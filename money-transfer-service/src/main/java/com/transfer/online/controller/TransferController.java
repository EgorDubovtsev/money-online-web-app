package com.transfer.online.controller;

import com.transfer.online.entity.Transaction;
import com.transfer.online.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.transfer.online.cost.Const.INVALID_TRANSACTION_ERROR;

@Slf4j
@Controller
@RequestMapping("/ts")
@RequiredArgsConstructor(onConstructor = @_(@Autowired))
public class TransferController {
    private final TransactionService transactionService;

    @PostMapping
    @RequestMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestBody Transaction transaction) {
        log.debug("POST: /ts/transfer from: {}, to: {}, amount: {} {}", transaction.getAccountSource(), transaction.getAccountDestination(), transaction.getAmount(), transaction.getCurrency());

        if (transactionService.isTransactionValid(transaction)) {
            log.debug("Транзакция доступна к выполнению. from={} to={}", transaction.getAccountSource(), transaction.getAccountDestination());
            transactionService.executeTransaction(transaction);
            return ResponseEntity.ok(HttpStatus.OK.toString()); //todo: return transaction code

        } else {
            return new ResponseEntity<>(INVALID_TRANSACTION_ERROR ,HttpStatus.BAD_REQUEST);
        }
    }
}
