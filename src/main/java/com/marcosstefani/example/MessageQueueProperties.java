package com.marcosstefani.example;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("app.message-queue")
@Component
@Getter
@Setter
public class MessageQueueProperties {
    private boolean enabled = true;
    private String queue1;
    private String queue2;
    private String queue3;
    private String queue4;
    private String queue5;
    private String queue6;
    private String queue7;
    private String exchange1;
}
