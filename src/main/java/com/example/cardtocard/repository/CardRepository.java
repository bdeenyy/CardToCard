package com.example.cardtocard.repository;

import com.example.cardtocard.model.Card;

import java.util.Collection;

public interface CardRepository {

    void addCard(Card card);

    void removeCard(String number);

    Card getCard(String number);

    Collection<Card> getAllCards();
}
