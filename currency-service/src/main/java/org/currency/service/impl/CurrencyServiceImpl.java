package org.currency.service.impl;

import lombok.RequiredArgsConstructor;
import org.currency.dao.CurrencyDao;
import org.currency.entity.Currency;
import org.currency.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @_(@Autowired))
public class CurrencyServiceImpl implements CurrencyService {
    private final CurrencyDao currencyDao;
    @Value("${currencies.main}")
    private String mainCurrencyCode;

    @Override
    public List<Currency> getAvailableCurrencies() {
        return currencyDao.getEnabledCurrencies();
    }

    @Override
    public Double getCurrencyRate(String currencyCode) {
        return currencyDao.getCurrencyRate(currencyCode);
    }

    @Override
    public Double convertCurrency(String currencyFrom, Double amount, String currencyTo) {
        Double convertedAmount = amount;
        if (!mainCurrencyCode.equals(currencyFrom)) {
            convertedAmount = convertCurrencyToMainCurrency(currencyFrom, amount);
        }
        Double currencyToRate = currencyDao.getCurrencyRate(currencyTo);

        return convertedAmount / currencyToRate;
    }

    private Double convertCurrencyToMainCurrency(String currencyFrom, Double amount) {
        Double currencyRate = currencyDao.getCurrencyRate(currencyFrom);

        return currencyRate * amount;
    }
}