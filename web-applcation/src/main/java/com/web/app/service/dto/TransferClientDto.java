package com.web.app.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.moneyonline.commons.entity.Currency;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TransferClientDto {
    @JsonProperty
    private Long clientId;
    @JsonProperty
    private String account;
    @JsonProperty
    private BigDecimal balance;
    @JsonProperty
    private Currency currency;


}
