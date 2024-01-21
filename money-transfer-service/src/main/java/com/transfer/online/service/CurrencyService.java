package com.transfer.online.service;

import com.transfer.online.dto.CurrencyConvertDto;

import java.math.BigDecimal;

public interface CurrencyService {

    BigDecimal convert(String currencyCodeFrom, String currencyCodeTo, String amount);
}
