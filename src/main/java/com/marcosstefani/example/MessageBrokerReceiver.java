package com.marcosstefani.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;

@Log4j2
public class MessageBrokerReceiver implements ChannelAwareMessageListener {
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        log.info("---");
        log.info(mapper.writeValueAsString(message));
        log.info(mapper.writeValueAsString(channel));
    }
}
