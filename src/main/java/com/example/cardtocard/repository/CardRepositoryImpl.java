package com.example.cardtocard.repository;

import com.example.cardtocard.model.Card;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


@Repository
public class CardRepositoryImpl implements CardRepository {
    private ConcurrentMap<String, Card> cards = new ConcurrentHashMap<>();

    public CardRepositoryImpl() {
        this.cards = new ConcurrentHashMap<>();
    }

    @Override
    public void addCard(Card card) {
        String number = card.getNumber();
        if (cards.containsKey(number)) {
            throw new IllegalArgumentException("Карта № " + number + " уже существует");
        }
        cards.put(number, card);
    }

    @Override
    public void removeCard(String number) {
        cards.remove(number);
    }

    @Override
    public Card getCard(String number) {
        return cards.get(number);
    }

    @Override
    public Collection<Card> getAllCards() {
        return cards.values();
    }

    public Card getCardByNumber(String number) {
        Card card = cards.get(number);
        return card != null ? card : new Card(number);
    }

    public void saveCard(Card card) {
        cards.put(card.getNumber(), card);
    }

}