package com.giatrong.learning.learnspringapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class LearnSpringApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(LearnSpringApiApplication.class, args);
    }

}