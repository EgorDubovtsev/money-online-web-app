package com.transfer.online.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.moneyonline.commons.entity.Currency;
import com.transfer.online.entity.ClientEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
public class ClientDto {
    @JsonProperty
    private Long clientId;
    @JsonProperty
    private String account;
    @JsonProperty
    private BigDecimal balance;
    @JsonProperty
    private String currency;

    public ClientDto(ClientEntity client) {
        this.clientId = client.getClientId();
        this.account = client.getAccount();
        this.balance = client.getBalance();
        this.currency = client.getCurrency();
    }
}
