package org.currency.service;

import org.currency.entity.Currency;

import java.util.List;

public interface CurrencyService {
    List<Currency> getAvailableCurrencies();

    Double getCurrencyRate(String currencyCode);

    Double convertCurrency(String currencyFrom, Double amount, String currencyTo);

}
