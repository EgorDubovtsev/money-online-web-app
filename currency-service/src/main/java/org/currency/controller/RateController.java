package org.currency.controller;

import com.moneyonline.commons.annotation.Profiling;
import lombok.RequiredArgsConstructor;
import org.currency.controller.request.ConvertRequest;
import org.currency.mapper.CurrencyMapper;
import org.currency.service.impl.CurrencyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@RestController
@RequiredArgsConstructor(onConstructor = @_(@Autowired))
@RequestMapping("/rate")
public class RateController {
    private final CurrencyMapper currencyMapper;
    private final CurrencyServiceImpl currencyService;

    /**
     * @return актуальный курс валюты
     */
    @Profiling
    @GetMapping("/{currencyCode}")
    public ResponseEntity<String> currencyRate(@PathParam("currencyCode") String currencyCode) {
        Double currencyRate = currencyService.getCurrencyRate(currencyCode);
        return ResponseEntity.ok(currencyRate.toString());
    }

    /**
     * @return сумму, переведенную в целевую валюту
     */
    @Profiling
    @PostMapping("/convert")
    public ResponseEntity<String> convertCurrency(@RequestBody ConvertRequest convertRequest) {
        Double convertedAmount = currencyService.convertCurrency(convertRequest.getCurrencyCodeFrom(),
                convertRequest.getAmount(),
                convertRequest.getCurrencyCodeTo());

        return ResponseEntity.ok(convertedAmount.toString());
    }


}
