package org.currency.service;

import lombok.RequiredArgsConstructor;
import org.currency.dao.CurrencyDao;
import org.currency.entity.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @_(@Autowired))
public class CurrencyService {
    private final CurrencyDao currencyDao;
    @Value("${currencies.main}")
    private String mainCurrencyCode;

    public List<Currency> getAvailableCurrencies() {
        return currencyDao.getEnabledCurrencies();
    }

    public Double getCurrencyRate(String currencyCode) {
        return currencyDao.getCurrencyRate(currencyCode);
    }

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
