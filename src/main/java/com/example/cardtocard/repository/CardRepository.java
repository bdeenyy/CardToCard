package com.example.cardtocard.repository;

import com.example.cardtocard.model.Amount;
import com.example.cardtocard.model.Card;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;


@Repository
public class CardRepository {

    private final Map<String, Card> cardMap = new HashMap<>();

    public CardRepository() {
        // add some test data
        Card card1 = new Card("1234567890123456", "Denis Bykov");
        card1.setValidTill("12/23");
        card1.setCVV("123");
        card1.deposit(new Amount(new BigDecimal("1000"), "USD"));
        card1.deposit(new Amount(new BigDecimal("5000"), "EUR"));
        cardMap.put(card1.getNumber(), card1);

        Card card2 = new Card("9876543210987654", "Kol Korneev");
        card2.setValidTill("09/24");
        card2.setCVV("456");
        card2.deposit(new Amount(new BigDecimal("8000"), "USD"));
        card2.deposit(new Amount(new BigDecimal("2000"), "EUR"));
        cardMap.put(card2.getNumber(), card2);
    }

    public Card getCardByNumber(String number) {
        return cardMap.get(number);
    }

}