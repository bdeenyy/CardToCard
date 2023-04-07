package com.example.cardtocard.service;

import com.example.cardtocard.model.Amount;
import com.example.cardtocard.model.Card;
import com.example.cardtocard.model.TransferRequest;
import com.example.cardtocard.repository.CardRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TransferService {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private TransferLogger transferLogger;


    public boolean transfer(@NotNull TransferRequest transferRequest) {
        cardRepository.saveCard(new Card(transferRequest.getCardFromNumber(),
                transferRequest.getCardFromValidTill(),
                transferRequest.getCardFromCVV()));
        cardRepository.saveCard(new Card(transferRequest.getCardToNumber()));

        Card cardFrom = cardRepository.getCardByNumber(transferRequest.getCardFromNumber());
        Card cardTo = cardRepository.getCardByNumber(transferRequest.getCardToNumber());

        if (cardFrom == null || cardTo == null || !cardFrom.isValid(transferRequest.getCardFromValidTill(),
                transferRequest.getCardFromCVV())) {
            return false;
        }


        Amount amount = new Amount(transferRequest.getAmount().getCurrency(), transferRequest.getAmount().getValue());
        Amount commission = amount.multiply(BigDecimal.valueOf(0.01)); // 1% комиссии
        boolean success = cardFrom.withdraw(amount.add(commission));
        cardTo.deposit(amount);
        transferLogger.logTransfer(cardFrom, cardTo, amount, commission, success);
        return success;
    }
}