package com.moneyonline.commons.entity;

import java.util.Random;

public class Util {
    public static final int ACCOUNT_NUMBER_SYMBOL_COUNT = 12;

    public static String generateAccountNumber() {
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < ACCOUNT_NUMBER_SYMBOL_COUNT; i++) {
            stringBuilder.append(random.nextInt(10));
        }
        return stringBuilder.toString();
    }
}
