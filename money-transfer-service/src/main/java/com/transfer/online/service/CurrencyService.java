package com.transfer.online.service;

import com.transfer.online.dto.CurrencyConvertDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;

@Service
public class CurrencyService {
    private final RestTemplate restTemplate = new RestTemplate();
    @Value("${services.currencyService.host}")
    private String currencyServiceHost;

    @Value("${services.currencyService.convertPath}")
    private String convertPath;

    private String sendRequestToConvert(CurrencyConvertDto request) {
        String url = currencyServiceHost + convertPath;
        HttpEntity<CurrencyConvertDto> entity = new HttpEntity<>(request, new HttpHeaders());

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class, new HashMap<>());

        return response.getBody();
    }

    @Transactional
    @Retryable(maxAttemptsExpression = "${retry.maxAttempts}", backoff = @Backoff(delayExpression = "${retry.delay}"))
    public BigDecimal convert(String currencyCodeFrom, String currencyCodeTo, String amount){
        CurrencyConvertDto request = new CurrencyConvertDto(amount, currencyCodeFrom, currencyCodeTo);

        String convertedAmount = sendRequestToConvert(request);
        return new BigDecimal(convertedAmount);
    }
}
