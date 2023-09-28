package com.web.app.service;

import com.web.app.controller.dto.TransactionDto;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface TransactionService {
    /**
     * Проверка возможности транзакции к выполнению
     *
     * @param transactionDto
     */
    boolean isTransactionValid(TransactionDto transactionDto);


    /**
     * Метод выполнения траназакции
     *
     * @param transactionDto
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    boolean executeTransaction(TransactionDto transactionDto);
}
