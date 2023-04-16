package com.example.cardtocard.service;

import com.example.cardtocard.dto.TransferRequest;
import com.example.cardtocard.exception.BadRequestException;
import com.example.cardtocard.exception.UnauthorizedException;
import com.example.cardtocard.logger.TransferLogger;
import com.example.cardtocard.model.Amount;
import com.example.cardtocard.model.Card;
import com.example.cardtocard.repository.CardRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TransferServiceTest {

    @Mock
    private CardRepositoryImpl cardRepository;
    @Mock
    private TransferLogger transferLogger;

    private TransferService transferService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        transferService = new TransferService(cardRepository, transferLogger);
        Mockito.reset(cardRepository);
        Mockito.reset(transferLogger);
    }

    @Test
    void transfer_ValidRequest_ReturnsOperationId() throws BadRequestException, UnauthorizedException {
        String cardFromNumber = "4276123423456765";
        String cardFromValidTill = "12/21";
        String cardFromCVV = "123";
        String cardToNumber = "4276123412345678";
        BigDecimal amountValue = BigDecimal.valueOf(1000);
        String currency = "RUR";
        TransferRequest request = new TransferRequest(
                cardFromNumber,
                cardToNumber,
                cardFromCVV,
                cardFromValidTill,
                new Amount(currency, amountValue)
        );

        Card cardFrom = new Card(cardFromNumber, cardFromValidTill, cardFromCVV);
        Card cardTo= new Card(cardToNumber);
        when(cardRepository.getCardByNumber(cardFromNumber)).thenReturn(cardFrom);
        when(cardRepository.getCardByNumber(cardToNumber)).thenReturn(cardTo);

        String operationId = transferService.transfer(request);

        assertNotNull(operationId);
        verify(cardRepository, times(2)).saveCard(any());
        verify(cardRepository).getCardByNumber(cardFromNumber);
        verify(cardRepository).getCardByNumber(cardToNumber);

    }


    @Test
    void transfer_InvalidCardData_ThrowsUnauthorizedException() {
        TransferRequest request = new TransferRequest(
                "invalidCardNumber",
                "12/21",
                "invalidCVV",
                "4276123412345678",
                new Amount("RUB", BigDecimal.TEN)
        );
        when(cardRepository.getCardByNumber(anyString())).thenReturn(null);
        assertThrows(UnauthorizedException.class, () -> transferService.transfer(request));
    }

    @Test
    void transfer_NotEnoughMoneyOnCard_ThrowsBadRequestException() {
        TransferRequest request = new TransferRequest(
                "4276123423456765",
                "4276123412345678",
                "123",
                "12/21",
                new Amount("RUB", BigDecimal.TEN)
        );
        Card cardFrom = new Card(request.getCardFromNumber(), request.getCardFromValidTill(), request.getCardFromCVV());
        when(cardRepository.getCardByNumber(request.getCardFromNumber())).thenReturn(cardFrom);
        when(cardRepository.getCardByNumber(request.getCardToNumber())).thenReturn(new Card(request.getCardToNumber()));

        assertThrows(BadRequestException.class, () -> transferService.transfer(request));
        verify(cardRepository, times(2)).saveCard(any(Card.class));
        verify(cardRepository).getCardByNumber(request.getCardFromNumber());
        verify(cardRepository).getCardByNumber(request.getCardToNumber());
        verifyNoMoreInteractions(cardRepository);
        verifyNoInteractions(transferLogger);
    }

    @Test
    void confirmOperation_ValidRequest_ReturnsOperationId() throws BadRequestException {
        String operationId = "1234";
        when(transferLogger.findTransaction(operationId)).thenReturn("confirmed");

        String result = transferService.confirmOperation(operationId);

        assertEquals("confirmed", result);
        verify(transferLogger).findTransaction(operationId);
    }

    @Test
    void confirmOperation_InvalidRequest_ThrowsBadRequestException() throws BadRequestException {
        String operationId = "invalid";
        when(transferLogger.findTransaction(operationId)).thenThrow(new BadRequestException("not found"));

        assertThrows(BadRequestException.class, () -> transferService.confirmOperation(operationId));
        verify(transferLogger).findTransaction(operationId);
    }
}