package com.example.cardtocard.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Amount {

    private String currency;

    private BigDecimal value;

    public Amount() {
    }

    public Amount(String currency, BigDecimal value) {
        this.value = value;
        this.currency = currency;
    }

    public BigDecimal getValue() {
        return value;
    }

    public String getCurrency() {
        return currency;
    }

    public Amount add(Amount commission) {
        return new Amount(currency, value.add(commission.value));
    }

    public Amount multiply(BigDecimal valueOf) {
        return new Amount(currency, value.multiply(valueOf).setScale(2, RoundingMode.HALF_UP));
    }
}