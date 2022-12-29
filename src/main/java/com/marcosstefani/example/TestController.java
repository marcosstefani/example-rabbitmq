package com.marcosstefani.example;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
@RequiredArgsConstructor
public class TestController {
    private final RabbitTemplate rabbitTemplate;

    @PutMapping("publish")
    public void publish(@RequestBody String body) {
        rabbitTemplate.convertAndSend("QUEUE1",body);
    }
}
