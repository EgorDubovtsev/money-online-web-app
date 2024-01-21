package com.transfer.online.service;

import com.transfer.online.dto.ClientDto;
import com.transfer.online.entity.ClientEntity;

public interface AccountService {
    ClientDto getClientData(String clientId);

    ClientEntity create(ClientDto clientDto);

}
