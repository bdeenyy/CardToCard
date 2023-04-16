package com.example.cardtocard.model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Card {

    private final String number;
    private String holderName;
    private String validTill;
    private String CVV;
    private Map<String, BigDecimal> balanceMap = new HashMap<>();

    public Card(String number, String validTill, String CVV) {
        this.number = number;
        this.validTill = validTill;
        this.CVV = CVV;
        this.balanceMap = balanceMap;
        balanceMap.put("RUR", BigDecimal.valueOf(10000));
    }

    public Card(String number) {
        this.number = number;
        this.balanceMap = balanceMap;
        balanceMap.put("RUR", BigDecimal.valueOf(10000));
    }

    public String getNumber() {
        return number;
    }
    public boolean isValid(String validTill, String CVV) {
        return this.validTill.equals(validTill) && this.CVV.equals(CVV);
    }

    public boolean withdraw(Amount amount) {
        BigDecimal balance = balanceMap.get(amount.getCurrency());
        if (balance == null || balance.compareTo(amount.getValue()) < 0) {
            return false;
        }
        balanceMap.put(amount.getCurrency(), balance.subtract(amount.getValue()));
        return true;
    }

    public void deposit(Amount amount) {
        BigDecimal balance = balanceMap.get(amount.getCurrency());
        if (balance == null) {
            balanceMap.put(amount.getCurrency(), amount.getValue());
        } else {
            balanceMap.put(amount.getCurrency(), balance.add(amount.getValue()));
        }
    }

}