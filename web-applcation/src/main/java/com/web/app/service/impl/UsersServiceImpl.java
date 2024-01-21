package com.web.app.service.impl;


import com.moneyonline.commons.entity.Currency;
import com.web.app.entity.UserEntity;
import com.web.app.repository.UsersRepository;
import com.web.app.service.UserRestService;
import com.web.app.service.UsersService;
import com.web.app.service.dto.TransferClientDto;
import com.web.app.service.entity.Errors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.web.app.consts.Const.INITIAL_BALANCE;

@Service
@Slf4j
@AllArgsConstructor(onConstructor = @_(@Autowired))
public class UsersServiceImpl implements UsersService {
    private UsersRepository usersRepository;
    private UserRestService userRestService;

    @Override
    @EventListener(ApplicationReadyEvent.class)
    public void createDefaultUser() {
        usersRepository.getAllUsers()
                .forEach(userEntity -> {
                            userEntity.setBalance(INITIAL_BALANCE);
                            userEntity.setCurrency(Currency.RUB);
                            TransferClientDto createdUserAccountDto = userRestService.registerUserInTransactionService(userEntity);
                            userEntity.setAccountNumber(createdUserAccountDto.getAccount());
                            usersRepository.saveUserChanges(userEntity);
                        }
                );
    }

    @Override

    public List<UserEntity> getUsersAvailableForTransfer(String username) {
        //todo: TBD
        return usersRepository.getAllUsers()
                .stream()
                .filter(user -> !Objects.equals(user.getUsername(), username))
                .collect(Collectors.toList());
    }

    @Override
    public UserEntity getUserInfoByUsername(String username) {
        UserEntity user = usersRepository.getUser(username);
        if (user == null) {
            return null;
        }

        TransferClientDto clientData = userRestService.getClientData(user.getId());
        if (clientData == null) {
            clientData = tryToGetClientDataFromLocal(user);
        }

        user.setBalance(clientData.getBalance());
        user.setCurrency(clientData.getCurrency());
        user.setAccountNumber(clientData.getAccount());

        return user;
    }

    private TransferClientDto tryToGetClientDataFromLocal(UserEntity user) {
        return new TransferClientDto(user.getId(), user.getAccountNumber(), user.getBalance(), user.getCurrency());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = RuntimeException.class)
    public void createUser(UserEntity user) {

        try {
            usersRepository.createUser(user);
            TransferClientDto createdUserAccountDto = userRestService.registerUserInTransactionService(user);
            user.setAccountNumber(createdUserAccountDto.getAccount());
            user.setBalance(createdUserAccountDto.getBalance());
            usersRepository.saveUserChanges(user);

        } catch (Exception e) {
            log.warn("Во время создания пользователя просизошла ошибка. User: {}", user);
            log.warn("{}", e);

            throw new RuntimeException("Во время создания пользователя просизошла ошибка");
        }

    }

    @Override
    public Errors validateUserCreate(UserEntity user) {
        Errors errors = new Errors();
        if (getUserInfoByUsername(user.getUsername()) != null) {
            return errors.addError("Пользователь с таким email уже зарегистрирован.");
        }
        return errors;
    }

    @Override
    public UserEntity getUserById(Long userId) {
        return usersRepository.findById(userId);
    }
}
