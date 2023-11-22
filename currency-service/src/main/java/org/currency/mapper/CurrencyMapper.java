package org.currency.mapper;

import org.currency.controller.response.dto.CurrencyDto;
import org.currency.entity.Currency;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CurrencyMapper {
    CurrencyDto currencyToCurrencyDto(Currency currency);

    Currency currencyDtoToCurrency(CurrencyDto currency);
}
