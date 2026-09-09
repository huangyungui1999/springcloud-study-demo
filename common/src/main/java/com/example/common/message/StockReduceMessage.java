package com.example.common.message;

import java.io.Serializable;

public class StockReduceMessage implements Serializable {

	private static final long serialVersionUID = 1L;

	private String commodityCode;

	private Integer count;

	public StockReduceMessage() {
	}

	public StockReduceMessage(String commodityCode, Integer count) {
		this.commodityCode = commodityCode;
		this.count = count;
	}

	public String getCommodityCode() {
		return commodityCode;
	}

	public void setCommodityCode(String commodityCode) {
		this.commodityCode = commodityCode;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

}
