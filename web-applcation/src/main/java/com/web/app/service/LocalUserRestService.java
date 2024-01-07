package com.web.app.service;

import com.moneyonline.commons.entity.Util;
import com.web.app.entity.UserEntity;
import com.web.app.service.dto.TransferClientDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import static com.web.app.consts.Const.INITIAL_BALANCE;

@Profile({"local","test"})
@Service
@Slf4j
public class LocalUserRestService implements UserRestService {

    @Override
    public TransferClientDto registerUserInTransactionService(UserEntity userEntity) {
        TransferClientDto transferClientDto = new TransferClientDto();
        transferClientDto.setAccount(Util.generateAccountNumber());
        transferClientDto.setBalance(INITIAL_BALANCE);
        log.info("В локальном режиме запрос не отправляется в сервис переводов.");
        return transferClientDto;
    }

    @Override
    public TransferClientDto getClientData(Long clientId) {
        log.info("В локальном режиме запрос не отправляется в сервис переводов.");
        return null;
    }
}
