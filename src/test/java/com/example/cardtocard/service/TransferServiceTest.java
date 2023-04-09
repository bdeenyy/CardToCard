package com.example.cardtocard.service;

import com.example.cardtocard.model.Amount;
import com.example.cardtocard.model.Card;
import com.example.cardtocard.model.ConfirmRequest;
import com.example.cardtocard.model.TransferRequest;
import com.example.cardtocard.repository.CardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class TransferServiceTest {

    private TransferService transferService;

    @Mock
    private CardRepository cardRepository;

    @Mock
    private TransferLogger transferLogger;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        transferService = new TransferService();
        transferService.cardRepository = cardRepository;
        transferService.transferLogger = transferLogger;
    }

    @Test
    void transfer_shouldReturnNull_whenCardFromIsNull() {
        TransferRequest transferRequest = new TransferRequest();
        transferRequest.setCardFromNumber("1111222233334444");
        transferRequest.setCardFromValidTill("01/23");
        transferRequest.setCardFromCVV("123");
        transferRequest.setCardToNumber("5555666677778888");
        transferRequest.setAmount(new Amount("RUB", BigDecimal.valueOf(1000)));

        when(cardRepository.getCardByNumber(any())).thenReturn(null);

        String result = transferService.transfer(transferRequest);

        assertNull(result);
    }

    @Test
    void transfer_shouldReturnNull_whenCardToIsNull() {
        TransferRequest transferRequest = new TransferRequest();
        transferRequest.setCardFromNumber("1111222233334444");
        transferRequest.setCardFromValidTill("01/23");
        transferRequest.setCardFromCVV("123");
        transferRequest.setCardToNumber("5555666677778888");
        transferRequest.setAmount(new Amount("RUR", BigDecimal.valueOf(1000)));

        when(cardRepository.getCardByNumber(any()))
                .thenReturn(new Card(transferRequest.getCardFromNumber(),
                        transferRequest.getCardFromValidTill(),
                        transferRequest.getCardFromCVV()))
                .thenReturn(null);

        String result = transferService.transfer(transferRequest);

        assertNull(result);
    }

    @Test
    void transfer_shouldReturnNull_whenCardFromIsNotValid() {
        TransferRequest transferRequest = new TransferRequest();
        transferRequest.setCardFromNumber("1111222233334444");
        transferRequest.setCardFromValidTill("01/23");
        transferRequest.setCardFromCVV("123");
        transferRequest.setCardToNumber("5555666677778888");
        transferRequest.setAmount(new Amount("RUR", BigDecimal.valueOf(1000)));

        when(cardRepository.getCardByNumber(any()))
                .thenReturn(new Card(transferRequest.getCardFromNumber(),
                        transferRequest.getCardFromValidTill(),
                        "111"))
                .thenReturn(new Card(transferRequest.getCardToNumber()));

        String result = transferService.transfer(transferRequest);

        assertNull(result);
    }

    @Test
    void confirmOperation_shouldReturnNull_whenOperationIdIsNull() throws Exception {
        ConfirmRequest confirmRequest = new ConfirmRequest();
        confirmRequest.setOperationId(null);

        String result = transferService.confirmOperation(confirmRequest);

        assertNull(result);
    }

    @Test
    void confirmOperation_shouldReturnOperationId_whenOperationIdIsFound() throws Exception {
        ConfirmRequest confirmRequest = new ConfirmRequest();
        confirmRequest.setOperationId("xxx");

        when(transferLogger.getTransferById(any())).thenReturn("id=xxx");

        String result = transferService.confirmOperation(confirmRequest);

        assertNotNull(result);
    }

    @Test
    void confirmOperation_shouldReturnNull_whenOperationIdIsNotFound() throws Exception {
        ConfirmRequest confirmRequest = new ConfirmRequest();
        confirmRequest.setOperationId("yyy");

        when(transferLogger.getTransferById(any())).thenReturn(null);

        String result = transferService.confirmOperation(confirmRequest);

        assertNull(result);
    }
}
