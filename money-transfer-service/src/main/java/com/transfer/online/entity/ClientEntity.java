package com.transfer.online.entity;

import com.moneyonline.commons.entity.Currency;
import com.transfer.online.dto.ClientDto;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "clients")
public class ClientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private Long clientId;
    private String account;
    private BigDecimal balance;
    private Currency currency;
    @Version
    private long version;

    public ClientEntity(ClientDto clientDto) {
        this.clientId = clientDto.getClientId();
        this.account = clientDto.getAccount();
        this.balance = clientDto.getBalance();
        this.currency = clientDto.getCurrency();
    }
}
