package com.web.app.service;

import com.web.app.entity.UserEntity;
import com.web.app.service.dto.TransferClientDto;

public interface UserRestService {
    /**
     * Регистрация пользователя в транзакционном сервисе. После регистрации пользователь может выполнять и получать переводы.
     *
     * @param userEntity Пользователь для регистрации.
     */
    TransferClientDto registerUserInTransactionService(UserEntity userEntity);


    /**
     * Получение информации по счету клиента.
     *
     * @param clientId идентификатор клиента
     */
    TransferClientDto getClientData(Long clientId);

}