package com.example.account.controller;

import com.example.account.dto.AccountDTO;
import com.example.account.service.AccountService;
import com.example.common.BusinessException;
import com.example.common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户账号Controller
 */
@RestController
@RequestMapping("/account")
//解决跨域问题
@CrossOrigin
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/reduce-balance")
    public Result<?> reduceBalance(@RequestBody AccountDTO accountDTO) {
        try {
            accountService.reduceBalance(accountDTO.getUserId(), accountDTO.getPrice());
        }
        catch (BusinessException e) {
            return Result.failed(e.getMessage());
        }
        return Result.success("");
    }

    @GetMapping("/")
    public Result<?> getRemainAccount(@RequestParam("userId") String userId) {
        return accountService.getRemainAccount(userId);
    }
}
