package org.currency.controller;

import com.moneyonline.commons.annotation.Profiling;
import lombok.RequiredArgsConstructor;
import org.currency.controller.response.CurrenciesResponse;
import org.currency.controller.response.dto.CurrencyDto;
import org.currency.mapper.CurrencyMapper;
import org.currency.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/currency")
@RequiredArgsConstructor(onConstructor = @_(@Autowired))
public class CurrencyController {
    private final CurrencyMapper currencyMapper;
    private final CurrencyService currencyService;

    /**
     * @return Список валют с которыми работает сервис
     */
    @Profiling
    @GetMapping("/available")
    public ResponseEntity<CurrenciesResponse> availableCurrencies() {

        List<CurrencyDto> availableCurrenciesDto = currencyService.getAvailableCurrencies()
                .stream()
                .map(currencyMapper::currencyToCurrencyDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new CurrenciesResponse(availableCurrenciesDto));
    }
}
