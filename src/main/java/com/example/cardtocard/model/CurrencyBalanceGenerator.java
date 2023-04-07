package com.example.cardtocard.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CurrencyBalanceGenerator {
    private static final String[] CURRENCIES = {"USD", "EUR", "GBP", "JPY", "RUR"};
    private static final Random random = new Random();

    public Map<String, BigDecimal> generateRandomBalances() {
        Map<String, BigDecimal> balanceMap = new HashMap<>();
        for (String currency : CURRENCIES) {
            BigDecimal amount = BigDecimal.valueOf(random.nextDouble() * 100000).setScale(2, RoundingMode.HALF_UP);
            balanceMap.put(currency, amount);
        }
        return balanceMap;
    }
}