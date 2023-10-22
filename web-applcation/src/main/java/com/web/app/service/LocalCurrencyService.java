package com.web.app.service;

import com.web.app.service.dto.Currency;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Profile("local")
public class LocalCurrencyService implements CurrencyService {
    @Override
    public List<Currency> getCurrencies() {
        return Arrays.asList(
                new Currency("RUB", "1.0"),
                new Currency("USD", "83.3"),
                new Currency("EUR", "90.1"));
    }
}
