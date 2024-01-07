package com.transfer.online.dto;

import com.transfer.online.cosnts.Status;
import com.transfer.online.entity.Transaction;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class TransactionHistoryDto {
    private long id;
    private String accountSource;
    private String accountDestination;
    private String currency;
    private String amount;
    private Status status;
    private Timestamp created;

    public TransactionHistoryDto(Transaction transaction) {
        this.id = transaction.getId();
        this.accountSource = transaction.getAccountSource();
        this.accountDestination = transaction.getAccountDestination();
        this.currency = transaction.getCurrency();
        this.amount = transaction.getAmount().toPlainString();
        this.status = transaction.getStatus();
        this.created = transaction.getCreated();
    }
}
