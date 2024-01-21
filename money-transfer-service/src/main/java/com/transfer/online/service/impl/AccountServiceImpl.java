package com.transfer.online.service.impl;

import com.moneyonline.commons.entity.Util;
import com.transfer.online.dto.ClientDto;
import com.transfer.online.entity.ClientEntity;
import com.transfer.online.repository.ClientRepository;
import com.transfer.online.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.transfer.online.cosnts.Const.INITIAL_BALANCE;

@Service
@RequiredArgsConstructor(onConstructor = @_(@Autowired))
public class AccountServiceImpl implements AccountService {
    private final ClientRepository clientRepository;

    @Override
    public ClientDto getClientData(String clientId) {
        ClientEntity client = clientRepository.findByClientId(Long.parseLong(clientId));
        return client != null ? new ClientDto(client) : null;
    }

    @Override
    public ClientEntity create(ClientDto clientDto) {
        ClientEntity client = new ClientEntity(clientDto);
        client.setAccount(Util.generateAccountNumber());
        client.setBalance(INITIAL_BALANCE);

        ClientEntity existedUserWithThisId = clientRepository.findByClientId(clientDto.getClientId());
        if (existedUserWithThisId != null) {
            return existedUserWithThisId;
        }
        return clientRepository.save(client);
    }

}
