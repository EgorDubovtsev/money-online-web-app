package com.transfer.online.service;

import com.transfer.online.cosnts.Status;
import com.transfer.online.dto.TransactionDto;
import com.transfer.online.entity.ClientEntity;
import com.transfer.online.entity.Transaction;
import com.transfer.online.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor(onConstructor = @_(@Autowired))
public class SimpleTransactionService implements TransactionService {
    private final ClientRepository clientRepository;
    private final EntityManager entityManager;
    private final CurrencyService currencyService;

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
        BigDecimal amount = transaction.getAmount();
        if (!clientSrc.getCurrency().equals(clientDest.getCurrency())) {
            amount = currencyService.convert(transaction.getCurrency(), clientDest.getCurrency(), transaction.getAmount().toPlainString());
        }

        if (clientSrc.getBalance().compareTo(amount) < 0) {
            return false;
        }

        return true;
    }

    @Override
    @Transactional
    public Long executeTransaction(TransactionDto transaction) {
        log.debug("Обработка запроса на выполение транзацкии {}", transaction);

        ClientEntity clientSrc = clientRepository.findByAccount(transaction.getAccountSource());
        ClientEntity clientDest = clientRepository.findByAccount(transaction.getAccountDestination());
        Transaction transactionEntity = new Transaction(transaction);
        transactionEntity.setCreated(Timestamp.from(Instant.now()));

        lockAccounts(clientSrc, clientDest);

        if (isTransactionValid(transaction)) {
            log.info("Транзакция доступна к выполению. {}", transaction);
            BigDecimal amount = transaction.getAmount();
            if (!clientSrc.getCurrency().equals(clientDest.getCurrency())) {
                amount = currencyService.convert(transaction.getCurrency(), clientDest.getCurrency(), transaction.getAmount().toPlainString());
            }
            clientSrc.setBalance(clientSrc.getBalance().subtract(amount));
            clientDest.setBalance(clientDest.getBalance().add(amount));

            transactionEntity.setStatus(Status.FINISHED);

        } else {
            log.debug("Транзакция отклонена {}", transaction);
            transactionEntity.setStatus(Status.DECLINED);

        }
        entityManager.persist(transactionEntity);
        unlockAccounts(clientSrc, clientDest);

        return transactionEntity.getId();
    }

    private void lockAccounts(ClientEntity client1, ClientEntity client2) {
        ClientEntity userWithHigherId = client1.getId() > client2.getId() ? client1 : client2;
        ClientEntity userWithLowerId = client1.getId() < client2.getId() ? client1 : client2;

        entityManager.lock(userWithHigherId, LockModeType.PESSIMISTIC_WRITE);
        entityManager.lock(userWithLowerId, LockModeType.PESSIMISTIC_WRITE);
    }

    private void unlockAccounts(ClientEntity client1, ClientEntity client2) {
        ClientEntity userWithHigherId = client1.getId() > client2.getId() ? client1 : client2;
        ClientEntity userWithLowerId = client1.getId() < client2.getId() ? client1 : client2;

        entityManager.lock(userWithLowerId, LockModeType.NONE);
        entityManager.lock(userWithHigherId, LockModeType.NONE);
    }
}
