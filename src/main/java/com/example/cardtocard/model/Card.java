package com.example.cardtocard.model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Card {

    private final String number;
    private String holderName;
    private String validTill;
    private String CVV;
    private final Map<String, BigDecimal> balanceMap = new HashMap<>();

    public Card(String number, String validTill, String CVV) {
        this.number = number;
        this.validTill = validTill;
        this.CVV = CVV;
        balanceMap.put("RUR", BigDecimal.valueOf(10000));
    }

    public Card(String number) {
        this.number = number;
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
        balanceMap.merge(amount.getCurrency(), amount.getValue(), BigDecimal::add);
    }
}