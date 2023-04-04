package com.example.cardtocard.service;

import com.example.cardtocard.model.Card;
import com.example.cardtocard.model.Amount;
import com.example.cardtocard.model.TransferRequest;
import com.example.cardtocard.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransferService {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private TransferLogger transferLogger;

    public boolean transfer(TransferRequest transferRequest) {
        Card cardFrom = cardRepository.getCardByNumber(transferRequest.getCardFromNumber());
        Card cardTo = cardRepository.getCardByNumber(transferRequest.getCardToNumber());
        if(cardFrom == null || cardTo == null) {
            return false;
        }
        if(!cardFrom.isValid(transferRequest.getCardFromValidTill(), transferRequest.getCardFromCVV())) {
            return false;
        }
        Amount amount = new Amount(transferRequest.getAmountValue(), transferRequest.getAmountCurrency());
        if(!cardFrom.withdraw(amount)) {
            return false;
        }
        cardTo.deposit(amount);
        transferLogger.logTransfer(cardFrom, cardTo, amount);
        return true;
    }

}