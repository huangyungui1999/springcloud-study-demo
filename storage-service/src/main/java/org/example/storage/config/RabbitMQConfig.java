package org.example.storage.config;

import com.example.common.constant.RabbitMQConstants;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

	@Bean
	public DirectExchange stockReduceExchange() {
		return new DirectExchange(RabbitMQConstants.STOCK_REDUCE_EXCHANGE, true, false);
	}

	@Bean
	public Queue stockReduceQueue() {
		return new Queue(RabbitMQConstants.STOCK_REDUCE_QUEUE, true);
	}

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

}
