package com.example.cardtocard.controller;

import com.example.cardtocard.dto.ConfirmRequest;
import com.example.cardtocard.dto.TransferRequest;
import com.example.cardtocard.service.RequestHandler;
import com.example.cardtocard.dto.TransferResponse;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
public class TransferController {
    private final RequestHandler requestHandler;

    public TransferController(RequestHandler requestHandler) {
        this.requestHandler = requestHandler;
    }

    @PostMapping("/transfer")
    public ResponseEntity<TransferResponse> transfer(@RequestBody TransferRequest transferRequest) {
        return ResponseEntity.ok(requestHandler.handleTransferRequest(transferRequest));
    }

    @PostMapping("/confirmOperation")
    public ResponseEntity<TransferResponse> confirmOperation(@RequestBody ConfirmRequest confirmRequest) {
        return ResponseEntity.ok(requestHandler.handleConfirmRequest(confirmRequest));
    }
}