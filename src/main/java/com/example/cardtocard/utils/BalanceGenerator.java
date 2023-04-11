package com.example.cardtocard.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class BalanceGenerator {
    private static final String CURRENCIES = "RUR";
    private static final Random random = new Random();

    public Map<String, BigDecimal> generateRandomBalances() {
        Map<String, BigDecimal> balanceMap = new HashMap<>();
            BigDecimal amount = BigDecimal.valueOf(random.nextDouble() * 10000).setScale(2, RoundingMode.HALF_UP);
            balanceMap.put(CURRENCIES, amount);
        return balanceMap;
    }
}