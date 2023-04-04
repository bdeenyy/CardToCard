package com.example.cardtocard.service;

import com.example.cardtocard.model.Amount;
import com.example.cardtocard.model.Card;
import org.springframework.stereotype.Component;

@Component
public class TransferLogger {

    public void logTransfer(Card cardFrom, Card cardTo, Amount amount) {
        System.out.println("Transfer from " + cardFrom.getNumber() + " to " + cardTo.getNumber() + " of amount " + amount.getValue() + " " + amount.getCurrency());
    }
}