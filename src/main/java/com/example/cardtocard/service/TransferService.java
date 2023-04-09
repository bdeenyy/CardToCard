package com.example.cardtocard.service;

import com.example.cardtocard.model.ConfirmRequest;
import com.example.cardtocard.model.Amount;
import com.example.cardtocard.model.Card;
import com.example.cardtocard.model.TransferRequest;
import com.example.cardtocard.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.UUID;

@Service
public class TransferService {

    @Autowired
    CardRepository cardRepository;

    @Autowired
    TransferLogger transferLogger;
    public String transfer(TransferRequest transferRequest) {
        String operationId = UUID.randomUUID().toString().substring(3, 7);
        cardRepository.saveCard(new Card(transferRequest.getCardFromNumber(), transferRequest.getCardFromValidTill(), transferRequest.getCardFromCVV()));
        cardRepository.saveCard(new Card(transferRequest.getCardToNumber()));
        Card cardFrom = cardRepository.getCardByNumber(transferRequest.getCardFromNumber());
        Card cardTo = cardRepository.getCardByNumber(transferRequest.getCardToNumber());

        if (cardFrom == null || cardTo == null || !cardFrom.isValid(transferRequest.getCardFromValidTill(),
                transferRequest.getCardFromCVV())) {
            return null;
        }

        Amount amount = new Amount(transferRequest.getAmount().getCurrency(),
                transferRequest.getAmount().getValue()).multiply(BigDecimal.valueOf(0.01)); // format 0.00
        Amount commission = amount.multiply(BigDecimal.valueOf(0.01)); // 1% комиссии
        boolean success = cardFrom.withdraw(amount.add(commission));
        cardTo.deposit(amount);
        transferLogger.logTransfer(cardFrom, cardTo, amount, commission, success, operationId);
        return success ? operationId : null;
    }

    public String confirmOperation(ConfirmRequest confirmRequest) throws IOException {
        if (confirmRequest.getOperationId() == null) {
            return null;
        }
        String logLine = transferLogger.getTransferById(confirmRequest.getOperationId());
        if (logLine != null) {
            return confirmRequest.getOperationId();
        } else {
            System.err.println("Transaction with ID " + confirmRequest.getOperationId() + " is not found in the log.");
            return null;
        }
    }
}