package com.example.order.feign;

import com.example.common.Result;
import com.example.order.feign.dto.AccountDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@FeignClient(name = "account-service")
public interface AccountServiceFeignClient {

	@PostMapping("/account/reduce-balance")
    Result<?> reduceBalance(@RequestBody AccountDTO accountReduceBalanceDTO);

}
