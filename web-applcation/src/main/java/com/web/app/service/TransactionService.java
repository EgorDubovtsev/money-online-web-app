package com.web.app.service;

import com.web.app.controller.dto.TransactionDto;

public interface TransactionService {
    boolean isTransactionValid(TransactionDto transactionDto);

    boolean executeTransaction(TransactionDto transactionDto);
}
