package com.example.account.service.impl;


import com.example.account.mapper.AccountMapper;
import com.example.account.service.AccountService;
import com.example.common.BusinessException;
import com.example.common.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.sql.Timestamp;


@Service
public class AccountServiceImpl implements AccountService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private AccountMapper accountMapper;

	/**
	 * 扣减账户余额
	 * @param userId
	 * @param price
	 * @throws BusinessException
	 */
	@Override
	@Transactional
	public void reduceBalance(String userId, Integer price) throws BusinessException {
//		logger.info("[reduceBalance] currenet XID: {}", RootContext.getXID());

		checkBalance(userId, price);

		Timestamp updateTime = new Timestamp(System.currentTimeMillis());
		int updateCount = accountMapper.reduceBalance(userId, price, updateTime);
		if (updateCount == 0) {
			throw new BusinessException("reduce balance failed");
		}
	}

	/**
	 * 获取账户余额
	 * @param userId
	 * @return
	 */
	@Override
	public Result<?> getRemainAccount(String userId) {
		Integer balance = accountMapper.getBalance(userId);
		if (balance == null) {
			return Result.failed("wrong userId,please check the userId");
		}
		return Result.success(balance);
	}

	private void checkBalance(String userId, Integer price) throws BusinessException {
		Integer balance = accountMapper.getBalance(userId);
		if (balance < price) {
			throw new BusinessException("no enough balance");
		}
	}

}
