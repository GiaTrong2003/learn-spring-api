package com.giatrong.learning.learnspringapi.config;

import com.giatrong.learning.learnspringapi.entity.User;
import com.giatrong.learning.learnspringapi.enums.ErrorCode;
import com.giatrong.learning.learnspringapi.exception.AppException;
import com.giatrong.learning.learnspringapi.repository.UserRepository;
import com.giatrong.learning.learnspringapi.service.JwtService;
import com.giatrong.learning.learnspringapi.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserService userService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {

            final String authHeader = request.getHeader("Authorization");
            final String jwt;
            final String username;

            // 1. check header Authorization has value and starts with "Bearer "
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                log.debug("No Authorization header found");
                filterChain.doFilter(request, response);
                return;
            }

            // 2. Get token from header
            // "7" is the length of "Bearer "
            jwt = authHeader.substring(7);

            // 3. extract username from token
            username = jwtService.extractUsername(jwt);

            // 4. check token is valid
            // SecurityContextHolder.getContext().getAuthentication() is: checking if there
            // is already an authentication in the context
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                User user = this.userRepository.findUserByUsername(username);

                // 5. if token is valid, set authentication in SecurityContext
                if (jwtService.isTokenValid(jwt, user)) {
                    // crete new UsernamePasswordAuthenticationToken
                    // UsernamePasswordAuthenticationToken is a class that implements Authentication
                    // interface
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            user,
                            null, // credentials
                            user.getAuthorities());
                    // Set more details for the authentication token
                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request));
                    // Set the authentication token in the SecurityContext
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
            // return the filter chain to continue processing the request
            filterChain.doFilter(request, response);

        } catch (ServletException e) {
            log.error("Error in doFilterInternal: {}", e.getMessage());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        } catch (IOException e) {
            logger.error("Error in doFilterInternal: {}");
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }
}