package com.example.order.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Order {

	private Integer id;

	private String userId;

	/**
	 * 商品id
	 */
	private String commodityCode;

	/**
	 * 订单描述
	 * 简单用于ES搜索
	 */
	private String orderContent;

	private Integer count;

	private Integer money;

	private Timestamp createTime;

	private Timestamp updateTime;

	@Override
	public String toString() {
		return "Order{" + "id=" + id + ", userId='" + userId + '\'' + ", commodityCode='"
				+ commodityCode + '\'' + ", count=" + count + ", money=" + money
				+ ", createTime=" + createTime + ", updateTime=" + updateTime + '}';
	}

}
