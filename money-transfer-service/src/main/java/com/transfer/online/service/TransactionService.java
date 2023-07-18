package com.transfer.online.service;

import com.transfer.online.entity.Transaction;

public interface TransactionService {
    boolean isTransactionValid(Transaction transaction);

    boolean executeTransaction(Transaction transaction);
}
