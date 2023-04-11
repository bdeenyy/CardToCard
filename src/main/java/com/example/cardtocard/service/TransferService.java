package com.example.cardtocard.service;

import com.example.cardtocard.exception.BadRequestException;
import com.example.cardtocard.exception.UnauthorizedException;
import com.example.cardtocard.logger.TransferLogger;
import com.example.cardtocard.model.Amount;
import com.example.cardtocard.model.Card;
import com.example.cardtocard.dto.TransferRequest;
import com.example.cardtocard.repository.CardRepositoryImpl;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class TransferService {

    CardRepositoryImpl cardRepositoryImpl;

    TransferLogger transferLogger;

    public TransferService(CardRepositoryImpl cardRepositoryImpl, TransferLogger transferLogger) {
        this.cardRepositoryImpl = cardRepositoryImpl;
        this.transferLogger = transferLogger;
    }

    public TransferService() {

    }

    public String transfer(TransferRequest transferRequest) throws UnauthorizedException, BadRequestException {
        String operationId = UUID.randomUUID().toString().substring(3, 7);
        cardRepositoryImpl.saveCard(new Card(transferRequest.getCardFromNumber(), transferRequest.getCardFromValidTill(), transferRequest.getCardFromCVV()));
        cardRepositoryImpl.saveCard(new Card(transferRequest.getCardToNumber()));
        Card cardFrom = cardRepositoryImpl.getCardByNumber(transferRequest.getCardFromNumber());
        Card cardTo = cardRepositoryImpl.getCardByNumber(transferRequest.getCardToNumber());

        if (cardFrom == null || cardTo == null || !cardFrom.isValid(transferRequest.getCardFromValidTill(),
                transferRequest.getCardFromCVV())) {
            throw new UnauthorizedException("Данные карты не верны!");
        }

        return transaction(transferRequest, operationId, cardFrom, cardTo);
    }

    private String transaction(TransferRequest transferRequest, String operationId, Card cardFrom, Card cardTo)throws BadRequestException {
        Amount amount = new Amount(transferRequest.getAmount().getCurrency(),
                transferRequest.getAmount().getValue()).multiply(BigDecimal.valueOf(0.01)); // format 0.00
        Amount commission = amount.multiply(BigDecimal.valueOf(0.01)); // 1% комиссии
        boolean success = cardFrom.withdraw(amount.add(commission));
        if (!success){
            throw new BadRequestException("Не достаточно средств на счете");
        }
        cardTo.deposit(amount);
        transferLogger.logTransfer(cardFrom, cardTo, amount, commission, true, operationId);
        return operationId;
    }


    public String confirmOperation(String confirmRequest) throws BadRequestException{
            return transferLogger.findTransaction(confirmRequest);
    }
}