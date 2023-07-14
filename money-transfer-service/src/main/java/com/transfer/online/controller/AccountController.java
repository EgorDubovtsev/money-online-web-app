package com.transfer.online.controller;

import com.transfer.online.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@Slf4j
@RequiredArgsConstructor(onConstructor = @_(@Autowired))
public class AccountController {
    private final AccountService accountService;

    @GetMapping(path = "/{accountNumber}/balance")
    public ResponseEntity<String> getBalance(@PathVariable String accountNumber) {
        log.debug("GET /balance account: {}", accountNumber);//Todo: add masking

        return ResponseEntity.ok(accountService.getBalance(accountNumber).toPlainString());
    }
}
