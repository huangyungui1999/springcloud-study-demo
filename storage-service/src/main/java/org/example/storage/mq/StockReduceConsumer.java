package org.example.storage.mq;

import com.example.common.BusinessException;
import com.example.common.constant.RabbitMQConstants;
import com.example.common.message.StockReduceMessage;
import org.example.storage.service.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StockReduceConsumer {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private StorageService storageService;

	@RabbitListener(queues = RabbitMQConstants.STOCK_REDUCE_QUEUE)
	public void handleStockReduce(StockReduceMessage message) {
		logger.info("[handleStockReduce] commodityCode: {}, count: {}",
				message.getCommodityCode(), message.getCount());
		try {
			storageService.reduceStock(message.getCommodityCode(), message.getCount());
			logger.info("[handleStockReduce] success, commodityCode: {}, count: {}",
					message.getCommodityCode(), message.getCount());
		}
		catch (BusinessException e) {
			logger.error("[handleStockReduce] failed, commodityCode: {}, count: {}, reason: {}",
					message.getCommodityCode(), message.getCount(), e.getMessage());
		}
	}

}
