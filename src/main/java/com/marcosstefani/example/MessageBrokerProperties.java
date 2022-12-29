package com.marcosstefani.example;

public class MessageBrokerProperties {

    private String host;
    private int port;
    private String username;
    private String password;
    private int timeout = 300;

    public String getHost() {
        return host;
    }

    public MessageBrokerProperties setHost(String host) {
        this.host = host;
        return this;
    }

    public int getPort() {
        return port;
    }

    public MessageBrokerProperties setPort(int port) {
        this.port = port;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public MessageBrokerProperties setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public MessageBrokerProperties setPassword(String password) {
        this.password = password;
        return this;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
}
