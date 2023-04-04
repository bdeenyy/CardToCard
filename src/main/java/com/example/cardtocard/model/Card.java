package com.example.cardtocard.model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;


public class Card {

    private String number;
    private String holderName;
    private String validTill;
    private String CVV;
    private Map<String, BigDecimal> balanceMap = new HashMap<>();

    public Card(String number, String holderName) {
        this.number = number;
        this.holderName = holderName;
    }

    public boolean isValid(String validTill, String CVV) {
        return this.validTill.equals(validTill) && this.CVV.equals(CVV);
    }

    public boolean withdraw(Amount amount) {
        BigDecimal balance = balanceMap.get(amount.getCurrency());
        if(balance == null || balance.compareTo(amount.getValue()) < 0) {
            return false;
        }
        balanceMap.put(amount.getCurrency(), balance.subtract(amount.getValue()));
        return true;
    }

    public void deposit(Amount amount) {
        balanceMap.merge(amount.getCurrency(), amount.getValue(), BigDecimal::add);
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public String getValidTill() {
        return validTill;
    }

    public void setValidTill(String validTill) {
        this.validTill = validTill;
    }

    public String getCVV() {
        return CVV;
    }

    public void setCVV(String CVV) {
        this.CVV = CVV;
    }

    public Map<String, BigDecimal> getBalanceMap() {
        return balanceMap;
    }

    public void setBalanceMap(Map<String, BigDecimal> balanceMap) {
        this.balanceMap = balanceMap;
    }

}