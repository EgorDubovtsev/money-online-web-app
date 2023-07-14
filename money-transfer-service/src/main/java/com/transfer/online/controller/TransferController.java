package com.transfer.online.controller;

import com.transfer.online.entity.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/ts")
public class TransferController {
    @PostMapping
    @RequestMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestBody Transaction transaction) {
        log.info("POST: /ts/transfer from: {}, to: {}, amount: {} {}", transaction.getAccountSource(), transaction.getAccountDestination(), transaction.getAmount(), transaction.getCurrency());
        return null;
    }
}
