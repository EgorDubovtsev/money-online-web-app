package com.web.app.service;

import com.web.app.entity.UserEntity;
import com.web.app.service.dto.TransferClientDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
@SpringBootTest
public class LocalUserRestServiceTest {
    @Autowired
    private LocalUserRestService localUserRestService;

    @Test
    public void LocalUsersRestService_registerUserInTransactionService_InjectData() {
        UserEntity userEntity = new UserEntity();
        TransferClientDto transferClientDto = localUserRestService.registerUserInTransactionService(userEntity);
        Assertions.assertNotNull(transferClientDto.getAccount());
        Assertions.assertFalse(transferClientDto.getAccount().isEmpty());
        Assertions.assertNotNull(transferClientDto.getBalance());

    }
}
