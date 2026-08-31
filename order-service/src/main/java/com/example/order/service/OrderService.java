package com.example.order.service;


import com.example.common.BusinessException;
import com.example.common.Result;

public interface OrderService {

	Result<?> createOrder(String userId, String commodityCode, Integer count)
			throws BusinessException;

	Result<?> getOrderByUserId(String userId);
}
