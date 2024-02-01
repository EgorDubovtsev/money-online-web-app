package org.currency.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.currency.dao.CurrencyDao;
import org.currency.entity.Currency;
import org.currency.service.CurrencyScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class CurrencySchedulerImpl implements CurrencyScheduler {
    private final CurrencyDao currencyDao;

    @Override
    @Scheduled(cron = "${currencies.updateCron}")
    public void updateRates() {
        List<Currency> currencies = currencyDao.getEnabledCurrencies();

        for (Currency currency : currencies) {
            Double amount = getChangedAmount();

            if (increaseRate()) {
                currency.setRate(currency.getRate() + amount);
            } else {
                currency.setRate(currency.getRate() - amount);
            }
        }
        currencyDao.updateRate(currencies);
        log.debug("Курс был обновлен. {}", currencies);
    }

    private boolean increaseRate() {
        return Math.random() * 10 > 5;
    }

    private Double getChangedAmount() {
        return Math.random() * 10;
    }
}
