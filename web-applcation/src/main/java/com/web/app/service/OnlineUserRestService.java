package com.web.app.service;

import com.web.app.entity.UserEntity;
import com.web.app.service.dto.TransferClientDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashMap;

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
    public void registerUserInTransactionService(UserEntity userEntity) {
        URI uri = URI.create(transactionServiceUrl + transactionServiceCreateUserPath);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth(transactionServiceUsername, transactionServicePassword);


        TransferClientDto transferClientDto = new TransferClientDto(userEntity.getId(), userEntity.getAccountNumber(), userEntity.getBalance(), userEntity.getCurrency());
        HttpEntity<TransferClientDto> entity = new HttpEntity<>(transferClientDto, headers);

        restTemplate.postForEntity(uri, entity, TransferClientDto.class);
    }

    @Override
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
