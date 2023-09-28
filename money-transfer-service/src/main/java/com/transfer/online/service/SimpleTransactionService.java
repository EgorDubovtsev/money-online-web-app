package com.transfer.online.service;

import com.transfer.online.cosnts.Status;
import com.transfer.online.dto.TransactionDto;
import com.transfer.online.entity.ClientEntity;
import com.transfer.online.entity.Transaction;
import com.transfer.online.repository.ClientRepository;
import com.transfer.online.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import java.sql.Timestamp;
import java.time.Instant;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor(onConstructor = @_(@Autowired))
public class SimpleTransactionService implements TransactionService {
    private final ClientRepository clientRepository;
    private final TransactionRepository transactionRepository;
    private final EntityManager entityManager;

    @Override
    public boolean isTransactionValid(TransactionDto transaction) {
        ClientEntity clientSrc = clientRepository.findByAccount(transaction.getAccountSource());
        ClientEntity clientDest = clientRepository.findByAccount(transaction.getAccountDestination());
        if (clientDest == null || clientSrc == null) {
            return false;
        }
        if (clientSrc.getBalance().compareTo(transaction.getAmount()) < 0) {
            return false;
        }
        if (clientSrc.getCurrency() != clientDest.getCurrency()
                || clientSrc.getCurrency() != transaction.getCurrency()) {
            return false;
        }

        return true;
    }

    @Override
    public Long executeTransaction(TransactionDto transaction) {
        log.debug("Обработка запроса на выполение транзацкии {}", transaction);

        ClientEntity clientSrc = clientRepository.findByAccount(transaction.getAccountSource());
        ClientEntity clientDest = clientRepository.findByAccount(transaction.getAccountDestination());
        Transaction transactionEntity = new Transaction(transaction);
        transactionEntity.setCreated(Timestamp.from(Instant.now()));

        entityManager.lock(clientSrc, LockModeType.PESSIMISTIC_WRITE);
        entityManager.lock(clientDest, LockModeType.PESSIMISTIC_WRITE);

        if (isTransactionValid(transaction)) {
            log.debug("Выполнение транзацкии {}", transaction);
            clientSrc.setBalance(clientSrc.getBalance().subtract(transaction.getAmount()));
            clientDest.setBalance(clientDest.getBalance().add(transaction.getAmount()));

            transactionEntity.setStatus(Status.FINISHED);

        } else {
            log.debug("Транзакция отклонена {}", transaction);
            transactionEntity.setStatus(Status.DECLINED);

        }
        entityManager.persist(transactionEntity);
        entityManager.lock(clientSrc, LockModeType.NONE);
        entityManager.lock(clientDest, LockModeType.NONE);

        return transactionEntity.getId();
    }
}
