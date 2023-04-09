package com.example.cardtocard.service;

import com.example.cardtocard.model.Amount;
import com.example.cardtocard.model.Card;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

@Component
public class TransferLogger {

    private final Logger logger;
    private final File transferLogFile;

    public TransferLogger() throws Exception {
        logger = Logger.getLogger("transferLogger");
        transferLogFile = new File("transfer.log");
        FileHandler fileHandler = new FileHandler("transfer.log");
        fileHandler.setFormatter(new SimpleFormatter());
        logger.addHandler(fileHandler);
    }

    public void logTransfer(Card cardFrom, Card cardTo, Amount amount, Amount commission, boolean success, String operationId) {
        String message = "ID: " + operationId + " Transfer " + (success ? "successful" : "unsuccessful") + ", from " + cardFrom.getNumber() +
                " to " + cardTo.getNumber() + ", amount: " + amount.getValue() + " " + amount.getCurrency() +
                ", commission: " + commission.getValue() + " " + commission.getCurrency();
        logger.log(Level.INFO, message);
    }

    public String getTransferById(String operationId) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(transferLogFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("ID: " + operationId)) {
                    return line;
                }
            }
        }
        return null;
    }
}