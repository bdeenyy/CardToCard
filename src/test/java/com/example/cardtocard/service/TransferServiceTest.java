package com.example.cardtocard.service;

import com.example.cardtocard.dto.TransferRequest;
import com.example.cardtocard.dto.TransferResponse;
import com.example.cardtocard.dto.ConfirmRequest;
import com.example.cardtocard.logger.TransferLogger;
import com.example.cardtocard.model.Amount;
import com.example.cardtocard.model.Card;
import com.example.cardtocard.repository.CardRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransferServiceTest {
    @InjectMocks
    private TransferService transferService;

    @Mock
    private CardRepositoryImpl cardRepository;

    @Mock
    private TransferLogger transferLogger;

    @Test
    public void testTransfer() {
        // Настройка моков
        when(cardRepository.getCardByNumber("1234")).thenReturn(new Card("1234", "12/26", "123"));

        // Создание тестового запроса
        TransferRequest transferRequest = new TransferRequest(
                "1234",
                "1234",
                "123",
                "12/26",
                new Amount("RUR", BigDecimal.valueOf(10000))
        );


        // Вызов тестируемого метода
        TransferResponse response = transferService.transfer(transferRequest);

        // Проверка результата
        assertNotNull(response.operationId());
    }

    @Test
    public void testConfirmOperation() throws Exception {
        // Добавление operationId в список listOfOperations с помощью рефлексии
        Field listOfOperationsField = TransferService.class.getDeclaredField("listOfOperations");
        listOfOperationsField.setAccessible(true);
        List<String> listOfOperations = (List<String>) listOfOperationsField.get(transferService);
        listOfOperations.add("1234");

        // Создание тестового запроса
        ConfirmRequest confirmRequest = new ConfirmRequest("1234");

        // Вызов тестируемого метода
        TransferResponse response = transferService.confirmOperation(confirmRequest);

        // Проверка результата
        assertEquals("1234", response.operationId());
    }
}