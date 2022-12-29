package com.marcosstefani.example;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@ConfigurationProperties("app.messages")
@Component
public class BrokerProperties {

    private Map<String, MessageBrokerProperties> brokers = new HashMap<>();
    private int expirationTimeInMinutes;

    public Map<String, MessageBrokerProperties> getBrokers() {
        return brokers;
    }

    public BrokerProperties setBrokers(Map<String, MessageBrokerProperties> brokers) {
        this.brokers = brokers;
        return this;
    }

    public int getExpirationTimeInMinutes() {
        return expirationTimeInMinutes;
    }

    public void setExpirationTimeInMinutes(int expirationTimeInMinutes) {
        this.expirationTimeInMinutes = expirationTimeInMinutes;
    }
}
