package com.example.cardtocard.controller;

import com.example.cardtocard.exception.*;
import com.example.cardtocard.dto.ConfirmRequest;
import com.example.cardtocard.dto.TransferRequest;
import com.example.cardtocard.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping()
public class TransferController {

    private final TransferService transferService;

    @Autowired
    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping("/transfer")
    public ResponseEntity<?> transfer(@RequestBody TransferRequest transferRequest) throws UnauthorizedException, BadRequestException {
        String operationId = transferService.transfer(transferRequest);
        Map<String, String> response = new HashMap<>();
        response.put("operationId", operationId);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/confirmOperation") public ResponseEntity<?> confirmOperation(@RequestBody ConfirmRequest confirmRequest) throws BadRequestException {
        String response = transferService.confirmOperation(confirmRequest.getOperationId());
        return ResponseEntity.ok(new ConfirmRequest(response));
    }
        
}