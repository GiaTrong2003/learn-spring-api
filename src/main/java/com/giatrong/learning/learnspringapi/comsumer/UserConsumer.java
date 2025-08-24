package com.giatrong.learning.learnspringapi.comsumer;

import com.giatrong.learning.learnspringapi.dto.dtos.User.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserConsumer {

    @KafkaListener(topics = "user-topic-2", groupId = "user-group-1")
    public void listen(UserDto event) {
        log.info("📩 Received UserEvent: {}", event);
    }
}
