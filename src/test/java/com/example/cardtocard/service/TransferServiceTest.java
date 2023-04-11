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
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class TransferServiceTest {

    private TransferService transferService;

    @Mock
    private CardRepositoryImpl cardRepositoryImpl;

    @Mock
    private TransferLogger transferLogger;

    private TransferRequest transferRequest;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        transferService = new TransferService(cardRepositoryImpl, transferLogger);
        transferRequest = new TransferRequest("1234567890123456", "0987654321098765",
                "123", "01/25", new Amount("RUR", BigDecimal.valueOf(100)));
    }

//    @Test
//    public void testTransfer_success() throws UnauthorizedException, BadRequestException {
//        Card cardFrom = new Card("1234567890123456", "01/25", "123");
//        Card cardTo = new Card("0987654321098765");
//        when(cardRepositoryImpl.getCardByNumber("1234567890123456")).thenReturn(cardFrom);
//        when(cardRepositoryImpl.getCardByNumber("0987654321098765")).thenReturn(cardTo);
//        String operationId = transferService.transfer(transferRequest);
//
//        verify(cardRepositoryImpl, times(2)).saveCard(any(Card.class));
//        verify(cardRepositoryImpl).getCardByNumber("1234567890123456");
//        verify(cardRepositoryImpl).getCardByNumber("0987654321098765");
//        verify(transferLogger).logTransfer(eq(cardFrom), eq(cardTo), eq(
//                new Amount("RUR", BigDecimal.valueOf(100))), eq(
//                new Amount("RUR", BigDecimal.valueOf(1))), eq(true), eq(operationId));
//
//        assertEquals(4, operationId.length());
//    }
//
//
//    @Test
//    public void testTransfer_unauthorized() {
//        when(cardRepositoryImpl.getCardByNumber("1234567890123456")).thenReturn(null);
//        BadRequestException thrown = assertThrows(BadRequestException.class, () -> transferService.transfer(transferRequest));
//        assertEquals("Данные карты не верны!", thrown.getMessage());
//
//        verify(cardRepositoryImpl).getCardByNumber("1234567890123456");
//        verifyNoMoreInteractions(cardRepositoryImpl, transferLogger);
//    }

    @Test
    public void testConfirmOperation() throws BadRequestException {
        String confirmRequest = "abcd";
        when(transferLogger.findTransaction(confirmRequest)).thenReturn("1234");

        String result = transferService.confirmOperation(confirmRequest);

        assertEquals("1234", result);
        verify(transferLogger).findTransaction(confirmRequest);
        verifyNoMoreInteractions(cardRepositoryImpl, transferLogger);
    }

//    @Test
//    public void testTransfer_insufficientFunds() throws UnauthorizedException {
//        Card cardFrom = new Card("1234567890123456", "01/22", "123");
//        Card cardTo = new Card("0987654321098765");
//        when(cardRepositoryImpl.getCardByNumber("1234567890123456")).thenReturn(cardFrom);
//        when(cardRepositoryImpl.getCardByNumber("0987654321098765")).thenReturn(cardTo);
//
//        BadRequestException thrown = assertThrows(BadRequestException.class, () -> transferService.transfer(transferRequest));
//        assertEquals("Не достаточно средств на счете", thrown.getMessage());
//
//        verify(cardRepositoryImpl, times(2)).saveCard(any(Card.class));
//        verify(cardRepositoryImpl).getCardByNumber("1234567890123456");
//        verify(cardRepositoryImpl).getCardByNumber("0987654321098765");
//        verifyNoMoreInteractions(transferLogger);
//    }

}
