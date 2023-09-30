package com.web.app.controller;

import com.web.app.controller.dto.TransactionDto;
import com.web.app.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.HttpClientErrorException;

import java.security.Principal;
import java.util.Objects;

import static com.web.app.consts.Const.TRANSACTION_ERROR;

@Slf4j
@Controller
@RequiredArgsConstructor(onConstructor = @_(@Autowired))
public class TransferController {
    private final TransactionService transactionService;

    @PostMapping(path = "/transfer", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> transfer(@RequestBody TransactionDto transactionDto, Principal principal) {
        log.debug("POST /transfer Request: {}", transactionDto);

        if (!Objects.equals(transactionDto.getUserLoginFrom(), principal.getName())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }


        try {
            if (transactionService.isTransactionValid(transactionDto)) {
                transactionService.executeTransaction(transactionDto);

                return ResponseEntity.ok().build();

            }

        } catch (HttpClientErrorException e) {
            log.error("/transfer", e);
            return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body(e.getMessage());

        } catch (Exception e) {
            log.error("/transfer", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(TRANSACTION_ERROR);

        }

        return new ResponseEntity<>(TRANSACTION_ERROR, HttpStatus.BAD_REQUEST);
    }
}
