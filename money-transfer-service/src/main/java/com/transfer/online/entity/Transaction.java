package com.transfer.online.entity;

import com.transfer.online.cosnts.Status;
import com.transfer.online.dto.TransactionDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "client_transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @Column(name = "account_source")
    private String accountSource;
    @Column(name = "account_destination")
    private String accountDestination;
    private String currency;
    private BigDecimal amount;
    private Status status;
    private Timestamp created;
    @Version
    private long version;

    public Transaction(TransactionDto transactionDto) {
        this.accountSource = transactionDto.getAccountSource();
        this.accountDestination = transactionDto.getAccountDestination();
        this.currency = transactionDto.getCurrency();
        this.amount = transactionDto.getAmount();
    }
}
