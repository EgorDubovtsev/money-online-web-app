package com.transfer.online.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CurrencyConvertDto {
    @JsonProperty
    private Double amount;
    @JsonProperty
    private String currencyCodeFrom;
    @JsonProperty
    private String currencyCodeTo;
}
