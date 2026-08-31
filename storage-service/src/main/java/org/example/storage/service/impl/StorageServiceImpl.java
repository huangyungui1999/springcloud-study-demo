package org.example.storage.service.impl;


import com.example.common.BusinessException;
import com.example.common.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.example.storage.mapper.StorageMapper;
import org.example.storage.service.StorageService;

import java.sql.Timestamp;


@Service
public class StorageServiceImpl implements StorageService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private StorageMapper storageMapper;

	/**
	 * 扣减库存
	 * @param commodityCode
	 * @param count
	 * @throws BusinessException
	 */
	@Override
	@Transactional
	public void reduceStock(String commodityCode, Integer count)
			throws BusinessException {
//		logger.info("[reduceStock] current XID: {}", RootContext.getXID());
		logger.info("扣减库存");

		checkStock(commodityCode, count);

		Timestamp updateTime = new Timestamp(System.currentTimeMillis());
		int updateCount = storageMapper.reduceStock(commodityCode, count, updateTime);
		if (updateCount == 0) {
			throw new BusinessException("deduct stock failed");
		}
	}

	/**
	 * 剩余库存
	 * @param commodityCode
	 * @return
	 */
	@Override
	public Result<?> getRemainCount(String commodityCode) {
		Integer stock = storageMapper.getStock(commodityCode);
		if (stock == null) {
			return Result.failed("commodityCode wrong,please check commodity code");
		}
		return Result.success(stock);
	}

	private void checkStock(String commodityCode, Integer count)
			throws BusinessException {
		Integer stock = storageMapper.getStock(commodityCode);
		if (stock < count) {
			throw new BusinessException("no enough stock");
		}
	}

}
