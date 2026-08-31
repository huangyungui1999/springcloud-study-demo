package com.example.account.service;

import com.example.common.BusinessException;
import com.example.common.Result;

public interface AccountService {

	void reduceBalance(String userId, Integer price) throws BusinessException;

	Result<?> getRemainAccount(String userId);

}
