package com.web.app.service;

import com.moneyonline.commons.annotation.Profiling;
import com.web.app.entity.UserEntity;
import com.web.app.service.dto.TransferClientDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.*;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@Slf4j
@Service
@Profile("prod")
public class OnlineUserRestService implements UserRestService {
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${services.transactionService.url}")
    private String transactionServiceUrl;

    @Value("${services.transactionService.createUserPath}")
    private String transactionServiceCreateUserPath;

    @Value("${services.transactionService.getClientDataPath}")
    private String transactionServiceGetClientDataPath;

    @Value("${services.transactionService.auth.username}")
    private String transactionServiceUsername;

    @Value("${services.transactionService.auth.password}")
    private String transactionServicePassword;


    @Override
    @Profiling
    @Retryable(maxAttemptsExpression = "${retry.maxAttempts}", backoff = @Backoff(delayExpression = "${retry.delay}"))
    public TransferClientDto registerUserInTransactionService(UserEntity userEntity) {
        String url = transactionServiceUrl + transactionServiceCreateUserPath;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth(transactionServiceUsername, transactionServicePassword);


        TransferClientDto transferClientDto = new TransferClientDto(userEntity.getId(), userEntity.getAccountNumber(), userEntity.getBalance(), userEntity.getCurrency());
        HttpEntity<TransferClientDto> entity = new HttpEntity<>(transferClientDto, headers);

        try {

            ResponseEntity<TransferClientDto> response = restTemplate.exchange(url, HttpMethod.POST, entity, TransferClientDto.class, new HashMap<>());
            return response.getBody();

        } catch (ResourceAccessException e) {
            log.error("Во время создания пользователя произошла ошибка.", e);
            throw new ResourceAccessException(e.getMessage());
        }
    }

    @Override
    @Transactional
    @Retryable(maxAttemptsExpression = "${retry.maxAttempts}", backoff = @Backoff(delayExpression = "${retry.delay}"))
    public TransferClientDto getClientData(Long clientId) {
        String url = transactionServiceUrl + transactionServiceGetClientDataPath;
        HashMap<String, String> params = new HashMap<>();
        params.put("clientId", String.valueOf(clientId));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth(transactionServiceUsername, transactionServicePassword);

        HttpEntity<TransferClientDto> entity = new HttpEntity<>(headers);

        return restTemplate.exchange(url, HttpMethod.GET, entity, TransferClientDto.class, params).getBody();
    }
}
