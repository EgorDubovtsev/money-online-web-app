package com.transfer.online.repository;

import com.transfer.online.entity.Transaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TransactionRepository extends CrudRepository<Transaction,Long> {
    @Query(value = "SELECT * FROM client_transactions where account_source=?1 or account_destination=?1 order by created", nativeQuery = true)
    List<Transaction> findClientTransactions(String account);
}
