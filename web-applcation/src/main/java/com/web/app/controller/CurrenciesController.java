package com.web.app.controller;

import com.google.gson.Gson;
import com.web.app.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor(onConstructor = @_(@Autowired))
public class CurrenciesController {
    private final Gson OBJECT_MAPPER = new Gson();
    private final CurrencyService currencyService;

    @GetMapping("/currencies")
    public ResponseEntity<String> getCurrencies() {
        List<String> currencies = currencyService.getCurrencies();

        return ResponseEntity.ok(OBJECT_MAPPER.toJson(currencies));
    }
}
