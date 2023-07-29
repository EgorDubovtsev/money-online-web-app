package com.web.app.service;

import com.web.app.entity.UserEntity;
import com.web.app.service.dto.TransferClientDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("local")
@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @_(@Autowired))
public class LocalUserRestService implements UserRestService {
    private final UsersService usersService;

    @Override
    public void registerUserInTransactionService(UserEntity userEntity) {
        log.info("В локальном режиме запрос не отправляется в сервис переводов.");
    }

    @Override
    public TransferClientDto getClientData(Long clientId) {
        log.info("В локальном режиме запрос не отправляется в сервис переводов.");
        UserEntity user = usersService.getUserById(clientId);
        return user == null ? null : new TransferClientDto(user.getId(), user.getAccountNumber(), user.getBalance(), user.getCurrency());
    }
}