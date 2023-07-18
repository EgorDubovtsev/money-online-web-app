package com.web.app.service;

import com.web.app.controller.dto.TransactionDto;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
@Service
@Profile("prod")
public class OnlineMoneyTransferService implements TransactionService {
    @Override
    public boolean isTransactionValid(TransactionDto transactionDto) {
        return true;
    }

    @Override
    public boolean executeTransaction(TransactionDto  transactionDto) {
        return true;
    }
}
