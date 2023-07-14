package com.moneyonline.commons.entity;

public enum Currency {
    RUB("RUB"), USD("USD"), EUR("EUR");
    private final String currencyName;

    Currency(String currency) {
        this.currencyName = currency;
    }

    public String getCurrencyName() {
        return currencyName;
    }
}
