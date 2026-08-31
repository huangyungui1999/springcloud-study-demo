package com.example.order.mapper;

import com.example.order.entity.Order;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface OrderMapper {

	@Insert("INSERT INTO `order` (user_id, commodity_code,count,money,create_time,update_time) VALUES (#{userId}, #{commodityCode},#{count},#{money},#{createTime},#{updateTime})")
	@Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
	int saveOrder(Order order);

	@Select("select * from `order` where user_id = #{userId} order by id desc")
	List<Order> getOrderByUserId(@Param("userId") String userId);
}
