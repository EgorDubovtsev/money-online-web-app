package com.web.app.service;

import com.web.app.controller.dto.TransactionDto;
import com.web.app.entity.UserEntity;
import com.web.app.service.dto.TransactionTsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;

@Service
@RequiredArgsConstructor(onConstructor = @_(@Autowired))
@Profile("prod")
public class OnlineMoneyTransferService implements TransactionService {
    private final UsersService usersService;
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${services.transactionService.host}")
    private String transactionServiceHost;

    @Value("${services.transactionService.transferPath}")
    private String transactionServiceTransferPath;

    @Value("${services.transactionService.auth.username}")
    private String transactionServiceUsername;

    @Value("${services.transactionService.auth.password}")
    private String transactionServicePassword;

    @Override
    public boolean isTransactionValid(TransactionDto transactionDto) {
        UserEntity userFrom = usersService.getUserInfoByUsername(transactionDto.getUserLoginFrom());
        UserEntity userTo = usersService.getUserInfoByUsername(transactionDto.getUserLoginTo());

        boolean isTransactionValid = userFrom.getCurrency().equals(userTo.getCurrency());
        BigDecimal amount = new BigDecimal(transactionDto.getAmount());
        isTransactionValid = isTransactionValid && amount.compareTo(BigDecimal.ZERO) > 0;
        userFrom.lock();

        isTransactionValid = isTransactionValid && userFrom.getBalance().compareTo(amount) >= 0;

        userFrom.unlock();
        return isTransactionValid;
    }

    @Override
    @Transactional
    @Retryable(maxAttemptsExpression = "${retry.maxAttempts}", backoff = @Backoff(delayExpression = "${retry.delay}"))
    public boolean executeTransaction(TransactionDto transactionDto) {
        UserEntity userFrom = usersService.getUserInfoByUsername(transactionDto.getUserLoginFrom());
        UserEntity userTo = usersService.getUserInfoByUsername(transactionDto.getUserLoginTo());

        String url = transactionServiceHost + transactionServiceTransferPath;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth(transactionServiceUsername, transactionServicePassword);

        TransactionTsDto request = createRequestToTs(transactionDto, userFrom, userTo);
        HttpEntity<TransactionTsDto> entity = new HttpEntity<>(request, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class, new HashMap<>());

        return response.getStatusCode().is2xxSuccessful();
    }

    private TransactionTsDto createRequestToTs(TransactionDto transactionDto, UserEntity from, UserEntity to) {
        TransactionTsDto transactionTsDto = new TransactionTsDto();
        UserEntity userFrom = usersService.getUserInfoByUsername(transactionDto.getUserLoginFrom());
        transactionTsDto.setCurrency(userFrom.getCurrency());
        transactionTsDto.setAmount(new BigDecimal(transactionDto.getAmount()));
        transactionTsDto.setAccountDestination(to.getAccountNumber());
        transactionTsDto.setAccountSource(from.getAccountNumber());

        return transactionTsDto;
    }
}
