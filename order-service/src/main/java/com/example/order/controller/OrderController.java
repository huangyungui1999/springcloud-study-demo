package com.example.order.controller;


import com.example.common.BusinessException;
import com.example.common.Result;
import com.example.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 订单服务Controller
 */
@CrossOrigin
@RestController
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@PostMapping("/create")
	public Result<?> createOrder(@RequestParam("userId") String userId,
								 @RequestParam("commodityCode") String commodityCode,
								 @RequestParam("count") Integer count) {
		Result<?> res = null;
		try {
			res = orderService.createOrder(userId, commodityCode, count);
		}
		catch (BusinessException e) {
			return Result.failed(e.getMessage());
		}catch (Exception e) {
			System.out.println(e.getMessage());
			return Result.failed("下单失败");
		}
		return res;
	}


	@GetMapping("/getOrder")
	public Result<?> getOrder(@RequestParam("userId") String userId) {
		Result<?> res = null;
		try {
			res = orderService.getOrderByUserId(userId);
		}
		catch (BusinessException e) {
			return Result.failed(e.getMessage());
		}
		return res;
	}

}
