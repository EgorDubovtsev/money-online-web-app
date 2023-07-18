package com.transfer.online.service;

import com.transfer.online.entity.Transaction;

public class SimpleTransactionService implements TransactionService {
    @Override
    public boolean isTransactionValid(Transaction transaction) {
        return false;
    }

    @Override
    public boolean executeTransaction(Transaction transaction) {
        return false;
    }
}
