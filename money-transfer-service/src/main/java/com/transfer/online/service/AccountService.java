package com.transfer.online.service;

import com.moneyonline.commons.entity.Util;
import com.transfer.online.dto.ClientDto;
import com.transfer.online.entity.ClientEntity;
import com.transfer.online.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.transfer.online.cosnts.Const.INITIAL_BALANCE;

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
        client.setAccount(Util.generateAccountNumber());
        client.setBalance(INITIAL_BALANCE);
        return clientRepository.save(client);
    }

}
