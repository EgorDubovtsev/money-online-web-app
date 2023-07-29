package com.transfer.online.controller;

import com.transfer.online.dto.TransactionDto;
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

import static com.transfer.online.cosnts.Const.INVALID_TRANSACTION_ERROR;

@Slf4j
@Controller
@RequestMapping("/ts")
@RequiredArgsConstructor(onConstructor = @_(@Autowired))
public class TransferController {
    private final TransactionService transactionService;

    @PostMapping
    @RequestMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestBody TransactionDto transactionDto) {
        log.debug("POST: /ts/transfer from: {}, to: {}, amount: {} {}", transactionDto.getAccountSource(), transactionDto.getAccountDestination(), transactionDto.getAmount(), transactionDto.getCurrency());

        if (transactionService.isTransactionValid(transactionDto)) {
            log.debug("Транзакция доступна к выполнению. from={} to={}", transactionDto.getAccountSource(), transactionDto.getAccountDestination());
            Long transactionCode = transactionService.executeTransaction(transactionDto);
            return ResponseEntity.ok(transactionCode.toString());

        } else {
            return new ResponseEntity<>(INVALID_TRANSACTION_ERROR ,HttpStatus.BAD_REQUEST);
        }
    }
}
