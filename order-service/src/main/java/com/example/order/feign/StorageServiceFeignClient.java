package com.example.order.feign;


import com.example.common.Result;
import com.example.order.feign.dto.StorageDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "storage-service")
public interface StorageServiceFeignClient {

	@PostMapping("/storage/reduce-stock")
    Result<?> reduceStock(@RequestBody StorageDTO productReduceStockDTO);

}
