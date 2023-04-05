package com.example.cardtocard.model;

import java.math.BigDecimal;

public class Amount {

    private BigDecimal value;
    private String currency;

    public Amount(String currency, BigDecimal value) {
        this.value = value;
        this.currency = currency;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Amount add(Amount commission) {
        return new Amount(currency, value.add(commission.value));
    }

    public Amount multiply(BigDecimal valueOf) {
        return new Amount(currency, value.multiply(valueOf));
    }
}