package com.example.cardtocard.service;

import com.example.cardtocard.dto.ConfirmRequest;
import com.example.cardtocard.dto.TransferResponse;
import com.example.cardtocard.exception.BadRequestException;
import com.example.cardtocard.exception.UnauthorizedException;
import com.example.cardtocard.logger.TransferLogger;
import com.example.cardtocard.model.Amount;
import com.example.cardtocard.model.Card;
import com.example.cardtocard.dto.TransferRequest;
import com.example.cardtocard.repository.CardRepositoryImpl;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TransferService {
    private final List<String> listOfOperations = new ArrayList<>();
    private final CardRepositoryImpl cardRepositoryImpl;
    private final TransferLogger transferLogger;

    public TransferService(CardRepositoryImpl cardRepositoryImpl, TransferLogger transferLogger) {
        this.cardRepositoryImpl = cardRepositoryImpl;
        this.transferLogger = transferLogger;
    }

    public TransferResponse transfer(TransferRequest transferRequest) {
        String operationId = UUID.randomUUID().toString().substring(3, 7);
        cardRepositoryImpl.saveCard(new Card(transferRequest.getCardFromNumber(), transferRequest.getCardFromValidTill(), transferRequest.getCardFromCVV()));
        cardRepositoryImpl.saveCard(new Card(transferRequest.getCardToNumber()));
        Card cardFrom = cardRepositoryImpl.getCardByNumber(transferRequest.getCardFromNumber());
        Card cardTo = cardRepositoryImpl.getCardByNumber(transferRequest.getCardToNumber());
        if (cardFrom == null || cardTo == null) {
            try {
                throw new UnauthorizedException("Данные карты не верны!");
            } catch (UnauthorizedException e) {
                throw new RuntimeException(e);
            }
        }
        Amount amount = new Amount(transferRequest.getAmount().getCurrency(), transferRequest.getAmount().getValue()).multiply(BigDecimal.valueOf(0.01)); // format 0.00
        Amount commission = amount.multiply(BigDecimal.valueOf(0.01)); // 1% комиссии
        boolean success = cardFrom.withdraw(amount.add(commission));
        if (!success){
            try {
                throw new BadRequestException("Не достаточно средств на счете");
            } catch (BadRequestException e) {
                throw new RuntimeException(e);
            }
        }
        cardTo.deposit(amount);
        transferLogger.logTransfer(cardFrom, cardTo, amount, commission, true, operationId);
        listOfOperations.add(operationId);
        return new TransferResponse(operationId);
    }

    public TransferResponse confirmOperation(ConfirmRequest confirmRequest) {
        String operationId = confirmRequest.getOperationId();
        if (listOfOperations.contains(operationId)) {
            return new TransferResponse(operationId);
        } else {
            try {
                throw new BadRequestException("Операция не найдена");
            } catch (BadRequestException e) {
                throw new RuntimeException(e);
            }
        }
    }
}