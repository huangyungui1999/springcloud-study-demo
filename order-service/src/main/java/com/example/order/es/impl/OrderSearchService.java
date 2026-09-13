package com.example.order.es.impl;

import com.example.order.entity.Order;
import com.example.order.es.ElasticRepository;
import com.example.order.es.OrderSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName OrderSearchService
 * @Description 订单业务
 * @Author hyg
 * @Date 2026/9/9 15:50
 */
@Service
public class OrderSearchService {
    @Autowired
    private ElasticRepository elasticRepository;

    // 新增/更新：id存在则更新，不存在新增
    public void save(Order order){
        OrderSearch p = new OrderSearch();
        p.setId(Long.valueOf(order.getId()));
        p.setTitle(order.getOrderContent());
        p.setProductId(order.getCommodityCode());
        p.setPrice(order.getMoney());
        elasticRepository.save(p);
    }

    // 根据id查询
    public OrderSearch findById(Long id){
        return elasticRepository.findById(id).orElse(null);
    }

    // 删除
    public void delete(Long id){
        elasticRepository.deleteById(id);
    }
}
