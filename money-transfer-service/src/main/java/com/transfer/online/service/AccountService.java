package com.transfer.online.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountService {
    public BigDecimal getBalance(String accountNumber) {
        return new BigDecimal(100);
    }
}
