package com.web.app.service;

import com.web.app.service.dto.CurrenciesDto;
import com.web.app.service.dto.Currency;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@Profile("prod")
public class SimpleCurrencyService implements CurrencyService {
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${services.currencyService.host}")
    private String currencyServiceHost;

    @Value("${services.currencyService.getCurrenciesPath}")
    private String getCurrenciesPath;

    @Override
    @Transactional
    @Retryable(maxAttemptsExpression = "${retry.maxAttempts}", backoff = @Backoff(delayExpression = "${retry.delay}"))
    public List<Currency> getCurrencies() {
        String url = currencyServiceHost + getCurrenciesPath;
        CurrenciesDto response = restTemplate.exchange(url, HttpMethod.GET, null, CurrenciesDto.class, new HashMap<>()).getBody();

        return response == null ? new ArrayList<>() : response.getCurrencies();
    }
}
