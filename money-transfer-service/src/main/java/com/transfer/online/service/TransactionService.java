package com.transfer.online.service;

import com.transfer.online.dto.TransactionDto;

public interface TransactionService {
    boolean isTransactionValid(TransactionDto transaction);

    Long executeTransaction(TransactionDto transaction);
}
