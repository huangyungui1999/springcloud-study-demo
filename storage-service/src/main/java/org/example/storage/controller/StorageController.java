package org.example.storage.controller;

import com.example.common.BusinessException;
import com.example.common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.example.storage.dto.StorageDTO;
import org.example.storage.service.StorageService;

/**
 * 库存服务Controller
 */
@CrossOrigin
@RestController
@RequestMapping("/storage")
public class StorageController {

	@Autowired
	private StorageService storageService;

	@PostMapping("/reduce-stock")
	@Transactional
	public Result<?> reduceStock(@RequestBody StorageDTO storageDTO) {
		try {
			storageService.reduceStock(storageDTO.getCommodityCode(),
					storageDTO.getCount());
		}
		catch (BusinessException e) {
			return Result.failed(e.getMessage());
		}
		return Result.success("");
	}

	@GetMapping("/")
	public Result<?> getRemainCount(@RequestParam("commodityCode") String commodityCode) {
		return storageService.getRemainCount(commodityCode);
	}

}
