package com.transfer.online.controller;

import com.google.gson.Gson;
import com.transfer.online.cosnts.Const;
import com.transfer.online.dto.ClientDto;
import com.transfer.online.entity.ClientEntity;
import com.transfer.online.repository.ClientRepository;
import com.transfer.online.repository.TransactionRepository;
import com.transfer.online.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static com.transfer.online.cosnts.Const.CLIENT_NOT_FOUND;

@Controller
@Slf4j
@RequestMapping("/ts")
@RequiredArgsConstructor(onConstructor = @_(@Autowired))
public class AccountController {
    private final AccountService accountService;
    private final JdbcTemplate jdbcTemplate;
    private final ClientRepository clientRepository;
    private final TransactionRepository transactionRepository;
    private static final Gson MAPPER = new Gson();

    @GetMapping(path = "/{clientId}/data")
    public ResponseEntity<String> getClientData(@PathVariable String clientId) {
        log.debug("GET /{clientId}/data account: {}", clientId);
        ClientDto clientData = accountService.getClientData(clientId);
        if (clientData != null) {
            return ResponseEntity.ok(MAPPER.toJson(clientData));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(CLIENT_NOT_FOUND);
    }

    @PostMapping(path = "/create")
    public ResponseEntity<String> create(@RequestBody ClientDto clientDto) {
        log.debug("POST /create client: {}", clientDto);
        ClientEntity client = accountService.create(clientDto);
        return ResponseEntity.ok(MAPPER.toJson(client));
    }
}
