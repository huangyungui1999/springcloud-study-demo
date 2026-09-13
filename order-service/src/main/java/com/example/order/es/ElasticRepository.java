package com.example.order.es;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @ClassName OrderRepository
 * @Description ElasticSearch相关接口
 * @Author hyg
 * @Date 2026/9/9 15:47
 */
public interface ElasticRepository extends ElasticsearchRepository<OrderSearch, Long> {
}
