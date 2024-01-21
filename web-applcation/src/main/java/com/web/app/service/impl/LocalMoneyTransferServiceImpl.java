package com.web.app.service.impl;

import com.web.app.controller.dto.TransactionDto;
import com.web.app.entity.UserEntity;
import com.web.app.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Profile("!prod")
@RequiredArgsConstructor(onConstructor = @_(@Autowired))
public class LocalMoneyTransferServiceImpl implements TransactionService {
    private final UsersServiceImpl usersService;

    @Override
    public boolean isTransactionValid(TransactionDto transactionDto) {
        UserEntity userFrom = usersService.getUserInfoByUsername(transactionDto.getUserLoginFrom());
        UserEntity userTo = usersService.getUserInfoByUsername(transactionDto.getUserLoginTo());

        boolean isTransactionValid = userFrom.getCurrency().equals(userTo.getCurrency());
        BigDecimal amount = new BigDecimal(transactionDto.getAmount());
        isTransactionValid = isTransactionValid && amount.compareTo(BigDecimal.ZERO) > 0;
        userFrom.lock();

        isTransactionValid = isTransactionValid && userFrom.getBalance().compareTo(amount) >= 0;

        userFrom.unlock();
        return isTransactionValid;
    }

    @Override
    public boolean executeTransaction(TransactionDto transactionDto) {
        UserEntity userFrom = usersService.getUserInfoByUsername(transactionDto.getUserLoginFrom());
        UserEntity userTo = usersService.getUserInfoByUsername(transactionDto.getUserLoginTo());

        lockUsers(userFrom, userTo);

        transferMoney(userFrom, userTo, new BigDecimal(transactionDto.getAmount()));

        unlockUsers(userFrom, userTo);
        return true;
    }

    private void lockUsers(UserEntity user1, UserEntity user2) {
        UserEntity userWithHigherId = user1.getId() > user2.getId() ? user1 : user2;
        UserEntity userWithLowerId = user1.getId() < user2.getId() ? user1 : user2;

        userWithHigherId.lock();
        userWithLowerId.lock();
    }

    private void unlockUsers(UserEntity user1, UserEntity user2) {
        UserEntity userWithHigherId = user1.getId() > user2.getId() ? user1 : user2;
        UserEntity userWithLowerId = user1.getId() < user2.getId() ? user1 : user2;

        userWithLowerId.unlock();
        userWithHigherId.unlock();
    }

    private void transferMoney(UserEntity userFrom, UserEntity userTo, BigDecimal amount) {
        userFrom.setBalance(userFrom.getBalance().subtract(amount));
        userTo.setBalance(userTo.getBalance().add(amount));
    }
}
