package com.web.app.controller;

import com.web.app.controller.dto.TransactionDto;
import com.web.app.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.web.app.consts.Const.TRANSACTION_ERROR;

@Slf4j
@Controller
@RequiredArgsConstructor(onConstructor = @_(@Autowired))
public class TransferController {
    private final TransactionService transactionService;

    @PostMapping(path = "/transfer")
    public ResponseEntity<String> transfer(@RequestBody TransactionDto transactionDto) {
        log.debug("POST /transfer Request: {}", transactionDto);
        if (transactionService.isTransactionValid(transactionDto)) {
            transactionService.executeTransaction(transactionDto);
            
            return ResponseEntity.ok(HttpStatus.OK.toString());
        } else {

            return ResponseEntity.badRequest().body(TRANSACTION_ERROR);
        }
    }
}
