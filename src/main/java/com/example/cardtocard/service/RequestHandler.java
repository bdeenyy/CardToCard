package com.example.cardtocard.service;

import com.example.cardtocard.dto.ConfirmRequest;
import com.example.cardtocard.dto.TransferRequest;

import com.example.cardtocard.dto.TransferResponse;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class RequestHandler {
    private final TransferService transferService;

    public RequestHandler(TransferService transferService) {
        this.transferService = transferService;
    }

    public TransferResponse handleTransferRequest(TransferRequest transferRequest) {
        if (transferRequest == null) {
            throw new IllegalArgumentException("Transfer request cannot be null");
        }
        return transferService.transfer(transferRequest);
    }

    public TransferResponse handleConfirmRequest(ConfirmRequest confirmRequest) {
        if (confirmRequest == null) {
            throw new IllegalArgumentException("Confirm request cannot be null");
        }
        return transferService.confirmOperation(confirmRequest);
    }
}
