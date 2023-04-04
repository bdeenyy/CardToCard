package com.example.cardtocard.model;

import java.math.BigDecimal;

public class Amount {

    private BigDecimal value;
    private String currency;

    public Amount(BigDecimal value, String currency) {
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
        return new Amount(value.add(commission.value), currency);
    }

    public Amount multiply(BigDecimal valueOf) {
        return new Amount(value.multiply(valueOf), currency);
    }
}