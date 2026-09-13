package com.example.order.es;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @ClassName OrderSearch
 * @Description ElasticSearch订单索引
 * @Author hyg
 * @Date 2026/9/9 15:44
 */
@Data
@Document(indexName = "order") // 对应ES索引名
public class OrderSearch {
    @Id // 文档_id
    private Long id;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String title; // text，可分词，全文检索

    /**
     * 商品id
     */
    @Field(type = FieldType.Keyword)
    private String productId; // keyword，不分词，term精确查询、分组聚合

    @Field(type = FieldType.Integer)
    private Integer price;
}
