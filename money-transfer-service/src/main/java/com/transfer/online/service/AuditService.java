package com.transfer.online.service;

import com.transfer.online.dto.TransactionHistoryDto;

public interface AuditService {
    void sendTransactionToKafka(TransactionHistoryDto transactionDto);
}