package com.example.order.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.MessageRecoverer;
import org.springframework.amqp.rabbit.retry.RepublishMessageRecoverer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

/**
 * @ClassName RabbitRetryConfig
 * @Description AUTO消费模式-本地重试
 * @Author hyg
 * @Date 2026/9/2 16:04
 */
@Configuration
public class RabbitRetryConfig {
    public static final String DLX_EXCHANGE = "dlx.exchange";
    public static final String DLQ_QUEUE = "order.dead.letter.queue";
    public static final String DLQ_ROUTING_KEY = "order.dlx.key";

    // 1. 声明死信交换机
    @Bean
    public DirectExchange dlxExchange() {
        return new DirectExchange(DLX_EXCHANGE);
    }

    // 2. 声明死信队列
    @Bean
    public Queue dlqQueue() {
        return QueueBuilder.durable(DLQ_QUEUE).build();
    }

    // 3. 绑定死信队列到死信交换机
    @Bean
    public Binding dlqBinding() {
        return BindingBuilder.bind(dlqQueue()).to(dlxExchange()).with(DLQ_ROUTING_KEY);
    }

    // 5. 【核心】配置消息恢复器：重试耗尽后，将异常信息写入 Header 并路由到死信队列
    @Bean
    public MessageRecoverer messageRecoverer(RabbitTemplate rabbitTemplate) {
        return new RepublishMessageRecoverer(rabbitTemplate, DLX_EXCHANGE, DLQ_ROUTING_KEY);
    }
}
