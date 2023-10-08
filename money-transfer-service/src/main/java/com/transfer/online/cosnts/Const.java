package com.transfer.online.cosnts;

import java.math.BigDecimal;

public class Const {
    public static final BigDecimal INITIAL_BALANCE = new BigDecimal(1000);
    public static final String INVALID_TRANSACTION_ERROR = "Невозможно произвести транзакцию, повторите попытку позднее.";
    public static final String CLIENT_NOT_FOUND = "Клиент с указанным номером счета не найден.";
}
