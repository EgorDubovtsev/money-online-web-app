package com.web.app.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Profile("local")
public class LocalCurrencyService implements CurrencyService {
    @Override
    public List<String> getCurrencies() {
        return Arrays.asList("RUB", "USD", "EUR");
    }
}
