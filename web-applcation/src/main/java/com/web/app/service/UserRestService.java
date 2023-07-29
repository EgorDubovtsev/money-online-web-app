package com.web.app.service;

import com.web.app.entity.UserEntity;
import com.web.app.service.dto.TransferClientDto;

public interface UserRestService {
    void registerUserInTransactionService(UserEntity userEntity);
    TransferClientDto getClientData(Long clientId);

}