package com.marcosstefani.example;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
@ConditionalOnProperty(name = {"app.message-queue.enabled"}, havingValue = "true")
public class MessageQueueConfiguration {
    private static final String BROKER_NOTIFICATIONS = "notifications";
    private static final String BROKER_USERS = "users";
    private static final String BROKER_ADMIN = "admin";
    private static final String BROKER_REPORTS = "reports";

    private final MessageQueueProperties messageQueueProperties;
    private final BrokerProperties brokerProperties;
    private final ObjectMapper objectMapper;

    @Bean
    public AmqpAdmin brokerUsersAmqpAdmin(@Qualifier("brokerUsersConnectionFactory") ConnectionFactory brokerUsersConnectionFactory) {
        return getAmqpAdmin(brokerUsersConnectionFactory);
    }

    @Bean
    public AmqpAdmin brokerAdminAmqpAdmin(@Qualifier("brokerAdminConnectionFactory") ConnectionFactory brokerAdminConnectionFactory) {
        return getAmqpAdmin(brokerAdminConnectionFactory);
    }

    @Bean
    @Primary
    public AmqpAdmin brokerNotificationsAmqpAdmin(@Qualifier("brokerNotificationsConnectionFactory") ConnectionFactory brokerNotificationsConnectionFactory) {
        return getAmqpAdmin(brokerNotificationsConnectionFactory);
    }

    @Bean
    public AmqpAdmin brokerReportsAmqpAdmin(@Qualifier("brokerReportsConnectionFactory") ConnectionFactory brokerReportsConnectionFactory) {
        return getAmqpAdmin(brokerReportsConnectionFactory);
    }

    @Bean
    public MessageListenerAdapter listenerMessageQueueAdapter(ChannelAwareMessageListener messageBrokerReceiver) {
        return new MessageListenerAdapter(messageBrokerReceiver);
    }

    @Bean
    @Primary
    public CachingConnectionFactory brokerNotificationsConnectionFactory() {
        return createCachingConnectionFactory(BROKER_NOTIFICATIONS);
    }

    @Bean
    public CachingConnectionFactory brokerUsersConnectionFactory() {
        return createCachingConnectionFactory(BROKER_USERS);
    }

    @Bean
    public CachingConnectionFactory brokerAdminConnectionFactory() {
        return createCachingConnectionFactory(BROKER_ADMIN);
    }

    @Bean
    public CachingConnectionFactory brokerReportsConnectionFactory() {
        return createCachingConnectionFactory(BROKER_REPORTS);
    }

    @Bean
    public SimpleMessageListenerContainer notificationsContainer(@Qualifier("brokerNotificationsConnectionFactory") ConnectionFactory connectionFactory, MessageListenerAdapter messageListenerAdapter) {
        return createMessageListenerContainer(connectionFactory, messageListenerAdapter);
    }

    @Bean
    public SimpleMessageListenerContainer usersContainer(@Qualifier("brokerUsersConnectionFactory") ConnectionFactory connectionFactory, MessageListenerAdapter messageListenerAdapter) {
        return createMessageListenerContainer(connectionFactory, messageListenerAdapter);
    }

    @Bean
    public SimpleMessageListenerContainer adminContainer(@Qualifier("brokerAdminConnectionFactory") ConnectionFactory connectionFactory, MessageListenerAdapter messageListenerAdapter) {
        return createMessageListenerContainer(connectionFactory, messageListenerAdapter);
    }

    @Bean
    public SimpleMessageListenerContainer reportsContainer(@Qualifier("brokerReportsConnectionFactory") ConnectionFactory connectionFactory, MessageListenerAdapter messageListenerAdapter) {
        return createMessageListenerContainer(connectionFactory, messageListenerAdapter);
    }

    private SimpleMessageListenerContainer createMessageListenerContainer(ConnectionFactory connectionFactory, MessageListenerAdapter messageListenerAdapter){
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();

        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(
                messageQueueProperties.getQueue1(),
                messageQueueProperties.getQueue2(),
                messageQueueProperties.getQueue3(),
                messageQueueProperties.getQueue4(),
                messageQueueProperties.getQueue5(),
                messageQueueProperties.getQueue6(),
                messageQueueProperties.getQueue7()
        );
        container.setMessageListener(messageListenerAdapter);
        container.setMaxConcurrentConsumers(50);
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        container.setMissingQueuesFatal(false);
        container.setFailedDeclarationRetryInterval(60000);

        return container;
    }

    private CachingConnectionFactory createCachingConnectionFactory(String brokerName) {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(
                brokerProperties.getBrokers().get(brokerName).getHost(),
                brokerProperties.getBrokers().get(brokerName).getPort());
        cachingConnectionFactory.setUsername(brokerProperties.getBrokers().get(brokerName).getUsername());
        cachingConnectionFactory.setPassword(brokerProperties.getBrokers().get(brokerName).getPassword());
        cachingConnectionFactory.setConnectionTimeout(brokerProperties.getBrokers().get(brokerName).getTimeout());
        return cachingConnectionFactory;
    }

    private Queue queue1() {
        Map<String, Object> queueArguments = new HashMap<>();
        return new Queue(messageQueueProperties.getQueue1(), true, false, false, queueArguments);
    }

    private Queue queue2() {
        Map<String, Object> queueArguments = new HashMap<>();
        return new Queue(messageQueueProperties.getQueue2(), true, false, false, queueArguments);
    }

    private Queue queue3() {
        Map<String, Object> queueArguments = new HashMap<>();
        return new Queue(messageQueueProperties.getQueue3(), true, false, false, queueArguments);
    }

    private Queue queue4() {
        Map<String, Object> queueArguments = new HashMap<>();
        return new Queue(messageQueueProperties.getQueue4(), true, false, false, queueArguments);
    }

    private Queue queue5() {
        Map<String, Object> queueArguments = new HashMap<>();
        return new Queue(messageQueueProperties.getQueue5(), true, false, false, queueArguments);
    }

    private Queue queue6() {
        Map<String, Object> queueArguments = new HashMap<>();
        return new Queue(messageQueueProperties.getQueue6(), true, false, false, queueArguments);
    }

    private Queue queue7() {
        Map<String, Object> queueArguments = new HashMap<>();
        return new Queue(messageQueueProperties.getQueue7(), true, false, false, queueArguments);
    }

    @Bean
    public FanoutExchange exchange() {
        return new FanoutExchange(messageQueueProperties.getExchange1());
    }

    @Bean
    public Binding binding(Queue queue, FanoutExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange);
    }


    private AmqpAdmin getAmqpAdmin(ConnectionFactory connectionFactory){
        AmqpAdmin amqpAdmin = new RabbitAdmin(connectionFactory);
        amqpAdmin.declareQueue(queue1());
        amqpAdmin.declareQueue(queue2());
        amqpAdmin.declareQueue(queue4());
        amqpAdmin.declareQueue(queue3());
        amqpAdmin.declareQueue(queue5());
        amqpAdmin.declareQueue(queue6());
        amqpAdmin.declareQueue(queue7());

        amqpAdmin.declareExchange(exchange());
        amqpAdmin.declareBinding(binding(queue6(), exchange()));

        return amqpAdmin;
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter(ObjectMapper objectMapper) {
        objectMapper.setDefaultPropertyInclusion(
                JsonInclude.Value.construct(JsonInclude.Include.ALWAYS, JsonInclude.Include.NON_NULL));
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    private RabbitTemplate buildTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(createCachingConnectionFactory(BROKER_NOTIFICATIONS));
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter(objectMapper));
        return rabbitTemplate;
    }

    @Bean
    public RabbitTemplate eventsTemplate() {
        RabbitTemplate rabbitTemplate = buildTemplate();
        rabbitTemplate.setRoutingKey(messageQueueProperties.getQueue1());
        return buildTemplate();
    }

    @Bean
    @Primary
    public RabbitTemplate defaultRabbitTemplate() {
        return buildTemplate();
    }
}
