package org.example.storage.service;


import com.example.common.BusinessException;
import com.example.common.Result;

public interface StorageService {

	void reduceStock(String commodityCode, Integer orderCount) throws BusinessException;

	Result<?> getRemainCount(String commodityCode);

}
