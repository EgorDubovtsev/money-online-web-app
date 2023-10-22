package org.currency.controller;

import com.google.gson.Gson;
import com.moneyonline.commons.annotation.Profiling;
import org.currency.controller.response.CurrenciesResponse;
import org.currency.controller.response.dto.CurrencyDto;
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
        /*
            Todo: подять базу и в ней хранить курсы?
                При обновлении курса по шедулеру - отсылать эвернт в подписчиков?
         */
        return ResponseEntity.ok(new CurrenciesResponse(Arrays.asList(
                new CurrencyDto("RUB", "1"),
                new CurrencyDto("USD", "80.3"),
                new CurrencyDto("EUR", "90.1"))));
    }
}
