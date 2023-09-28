package com.web.app.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.moneyonline.commons.entity.Currency;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Data
@Getter
@Setter
@NoArgsConstructor
public class TransactionTsDto {
    @JsonProperty
    private String accountSource;
    @JsonProperty
    private String accountDestination;
    @JsonProperty
    private Currency currency;
    @JsonProperty
    private BigDecimal amount;
}