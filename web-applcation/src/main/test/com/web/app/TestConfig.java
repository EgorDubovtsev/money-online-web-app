package com.web.app.config;

import com.moneyonline.commons.entity.Currency;
import com.web.app.entity.UserEntity;
import com.web.app.repository.UsersRepository;
import com.web.app.service.CurrencyService;
import com.web.app.service.OnlineUserRestService;
import com.web.app.service.SimpleCurrencyService;
import com.web.app.service.UserRestService;
import com.web.app.service.dto.TransferClientDto;
import org.mockito.Mock;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Configuration
public class TestConfig {
    @Bean
    @Primary
//    @ConditionalOnProperty(name="service.mock", havingValue="true")
    public UserRestService onlineUserRestServiceMock(){
        UserRestService mock = mock(UserRestService.class);
        when(mock.getClientData(anyLong()))
                .thenReturn(new TransferClientDto(1L,"1234", new BigDecimal(1L), Currency.RUB));


        TransferClientDto transferClientDto = new TransferClientDto(1L, "1234", new BigDecimal(1),Currency.RUB);
        when(mock.registerUserInTransactionService(any(UserEntity.class))).thenReturn(transferClientDto);

        return mock;
    }

    @Bean
//    @ConditionalOnProperty(name="service.mock", havingValue="true")
    public CurrencyService simpleCurrencyServiceMock() {
        ArrayList<com.web.app.service.dto.Currency> currencies = new ArrayList<>();
        currencies.add(new com.web.app.service.dto.Currency("RUB", "1"));
        currencies.add(new com.web.app.service.dto.Currency("USD", "60.1"));
        CurrencyService currencyService = mock(CurrencyService.class);
        when(currencyService.getCurrencies()).thenReturn(currencies);
        return currencyService;
    }


}
