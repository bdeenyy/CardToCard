package com.example.cardtocard.repository;

import com.example.cardtocard.model.Card;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


@Repository
public class CardRepository {
    private final ConcurrentMap<String, Card> cardMap = new ConcurrentHashMap<>();

    public Card getCardByNumber(String number) {
        Card card = cardMap.get(number);
        return card != null ? card : new Card(number);
    }

    public void saveCard(Card card) {
        cardMap.put(card.getNumber(), card);
    }
}