package com.example.order.config;

import com.example.common.constant.RabbitMQConstants;
import com.example.order.mq.OrderConfirmCallback;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

	/**
	 * @description 创建交换机
	 * @return null
	 * @author hyg
	 * @date 2026/9/2 11:28
	 */
	@Bean
	public DirectExchange stockReduceExchange() {
		return new DirectExchange(RabbitMQConstants.STOCK_REDUCE_EXCHANGE, true, false);
	}

	/**
	 * @description 创建队列
	 * @return null
	 * @author hyg
	 * @date 2026/9/2 11:01
	 */
	@Bean
	public Queue stockReduceQueue() {
		return QueueBuilder.durable(RabbitMQConstants.STOCK_REDUCE_QUEUE)
				.withArgument("x-dead-letter-exchange", RabbitRetryConfig.DLX_EXCHANGE)
				.withArgument("x-dead-letter-routing-key", "order.dlx.key")
				.build();
	}

	/**
	 * @description 将队列绑定到交换机上
	 * @return null
	 * @author hyg
	 * @date 2026/9/2 10:59
	 */
	@Bean
	public Binding stockReduceBinding() {
		return BindingBuilder.bind(stockReduceQueue())
				.to(stockReduceExchange())
				.with(RabbitMQConstants.STOCK_REDUCE_ROUTING_KEY);
	}

	@Bean
	public MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	@Bean
	public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
										 MessageConverter jsonMessageConverter,
										 OrderConfirmCallback orderConfirmCallback) {
		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(jsonMessageConverter);

		//设置生产者确认失败回调
		rabbitTemplate.setConfirmCallback(orderConfirmCallback);

		//return回调

		return rabbitTemplate;
	}

}
