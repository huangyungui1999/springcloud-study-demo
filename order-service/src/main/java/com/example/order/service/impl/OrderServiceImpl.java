package com.example.order.service.impl;


import com.example.common.BusinessException;
import com.example.common.Result;
import com.example.order.entity.Order;
import com.example.order.feign.AccountServiceFeignClient;
import com.example.order.feign.StorageServiceFeignClient;
import com.example.order.feign.dto.AccountDTO;
import com.example.order.feign.dto.StorageDTO;
import com.example.order.mapper.OrderMapper;
import com.example.order.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.util.List;

import static com.example.common.ResultEnum.COMMON_FAILED;


@Service
public class OrderServiceImpl implements OrderService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private AccountServiceFeignClient accountService;

    @Autowired
    private StorageServiceFeignClient storageService;

    @Autowired
    RestTemplate restTemplate;

    @Override
//    @GlobalTransactional(name="createOrder",rollbackFor=Exception.class)
    public Result<?> createOrder(String userId, String commodityCode, Integer count) {

//        logger.info("[createOrder] current XID: {}", RootContext.getXID());

        // deduct storage
        StorageDTO storageDTO = new StorageDTO();
        storageDTO.setCommodityCode(commodityCode);
        storageDTO.setCount(count);
        //RestTemplate远程调用
        //String storage_url = "http://localhost:8010/storage/reduce-stock";
        //整合了Nacos+LoadBalaner,可以使用微服务名tlmall-storage代替localhost:8020
        //String storage_url = "http://tlmall-storage/storage/reduce-stock";
        //Integer storageCode = restTemplate.postForObject(storage_url,storageDTO, Result.class).getCode();
        //openFeign远程调用
        Integer storageCode = storageService.reduceStock(storageDTO).getCode();
        if (storageCode.equals(COMMON_FAILED.getCode())) {
            throw new BusinessException("stock not enough");
        }

        // deduct balance
        int price = count * 2;
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setUserId(userId);
        accountDTO.setPrice(price);
        //RestTemplate远程调用
        //String account_url = "http://localhost:8020/account/reduce-balance";
        //整合了Nacos+LoadBalaner,可以使用微服务名tlmall-account代替localhost:8020
        //String account_url = "http://tlmall-account/account/reduce-balance";
        //Integer accountCode = restTemplate.postForObject(account_url, accountDTO, Result.class).getCode();
        //openFeign远程调用
        Integer accountCode = accountService.reduceBalance(accountDTO).getCode();
        if (accountCode.equals(COMMON_FAILED.getCode())) {
            throw new BusinessException("balance not enough");
        }

        // save order
        Order order = new Order();
        order.setUserId(userId);
        order.setCommodityCode(commodityCode);
        order.setCount(count);
        order.setMoney(price);
        order.setCreateTime(new Timestamp(System.currentTimeMillis()));
        order.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        orderMapper.saveOrder(order);
        logger.info("[createOrder] orderId: {}", order.getId());

        return Result.success(order);
    }

    @Override
    public Result<?> getOrderByUserId(String userId) {
        List<Order> list = orderMapper.getOrderByUserId(userId);

        return Result.success(list);
    }

}
