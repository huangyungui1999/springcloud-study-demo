package com.example.order.mq;

import com.alibaba.fastjson2.JSON;
import com.example.common.constant.RabbitMQConstants;
import com.example.common.message.StockReduceMessage;
import com.example.order.mq.dto.BizCorrelationData;
import com.example.order.mq.dto.BizTypeConstants;
import org.apache.tomcat.util.json.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class StockReduceProducer {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private RabbitTemplate rabbitTemplate;

	/**
	 * @description direct模式
	 * @return null
	 * @author hyg
	 * @date 2026/9/2 11:06
	 */
	public void sendStockReduceMessage(String commodityCode, Integer count) {
		String msgId = UUID.randomUUID().toString();
		StockReduceMessage message = new StockReduceMessage(commodityCode, count);
		//设置回调对象
		BizCorrelationData bizCorrelationData = new BizCorrelationData(msgId, BizTypeConstants.ACCOUNT, JSON.toJSONString(message));
		rabbitTemplate.convertAndSend(RabbitMQConstants.STOCK_REDUCE_EXCHANGE,
				RabbitMQConstants.STOCK_REDUCE_ROUTING_KEY, message, bizCorrelationData);
		logger.info("[sendStockReduceMessage] commodityCode: {}, count: {}", commodityCode, count);
	}

}
