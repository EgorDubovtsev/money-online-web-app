package com.transfer.online.service;

import com.transfer.online.dto.ClientDto;
import com.transfer.online.entity.ClientEntity;
import com.transfer.online.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor(onConstructor = @_(@Autowired))
public class AccountService {
    private final ClientRepository clientRepository;

    public ClientDto getClientData(String clientId) {
        ClientEntity client = clientRepository.findByClientId(Long.parseLong(clientId));
        return client != null ? new ClientDto(client) : null;
    }

    public ClientEntity create(ClientDto clientDto) {
        ClientEntity client = new ClientEntity(clientDto);
        return clientRepository.save(client);
    }
}
