package com.example.cardtocard.logger;

import com.example.cardtocard.exception.BadRequestException;
import com.example.cardtocard.model.Amount;
import com.example.cardtocard.model.Card;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

@Component
public class TransferLogger {

    private final Logger logger;
    private final File transferLogFile;
    private static final Map<String, String> TRANSACTION_MAP = new HashMap<>();

    public TransferLogger() throws Exception {
        logger = Logger.getLogger("transferLogger");
        transferLogFile = new File("transfer.log");
        FileHandler fileHandler = new FileHandler("transfer.log");
        fileHandler.setFormatter(new SimpleFormatter());
        logger.addHandler(fileHandler);
    }

    public void logTransfer(Card cardFrom, Card cardTo, Amount amount, Amount commission, boolean success, String operationId) {
        String message = "ID: " + operationId + ", Transfer " + (success ? "successful" : "unsuccessful") + ", from " + cardFrom.getNumber() +
                " to " + cardTo.getNumber() + ", amount: " + amount.getValue() + " " + amount.getCurrency() +
                ", commission: " + commission.getValue() + " " + commission.getCurrency();
        if (operationId != null) {
            TRANSACTION_MAP.put(operationId, message); // добавляем информацию о транзакции в мапу по ее Id
        }
        logger.log(Level.INFO, message);
    }

    // метод для поиска транзакции по ее Id
    public  String findTransaction(String transactionId) throws BadRequestException {
        String transactionInfo = TRANSACTION_MAP.get(transactionId);
        if (transactionInfo == null) {
            throw new BadRequestException("Транзакция с ID " + transactionId + " не найдена");
        }
        return transactionId;
    }

}