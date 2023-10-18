package org.currency.controller;

import com.google.gson.Gson;
import com.moneyonline.commons.annotation.Profiling;
import org.currency.controller.response.CurrenciesResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;

@Controller
@RequestMapping("/currency")
public class CurrencyController {
    private final Gson OBJECT_MAPPER = new Gson();

    /**
     * @return Список валют с которыми работает сервис на данный момент
     */
    @Profiling
    @GetMapping("/available")
    public ResponseEntity<CurrenciesResponse> availableCurrencies() {

        return ResponseEntity.ok(new CurrenciesResponse(Arrays.asList("RUB", "USD", "EUR")));
    }
}
