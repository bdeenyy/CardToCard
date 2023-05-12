package com.example.cardtocard.controller;

import com.example.cardtocard.dto.ConfirmRequest;
import com.example.cardtocard.dto.TransferRequest;
import com.example.cardtocard.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping()
public class TransferController {
    private final TransferService transferService;

    @Autowired
    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping("/transfer")
    public ResponseEntity<Map<String, String>> transfer(@RequestBody TransferRequest transferRequest) {
        String operationId = transferService.transfer(transferRequest);
        Map<String, String> response = new HashMap<>();
        response.put("operationId", operationId);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/confirmOperation")
    public ResponseEntity<String> confirmOperation(@RequestBody ConfirmRequest confirmRequest) {
        String response = transferService.confirmOperation(confirmRequest.getRequest());
        return ResponseEntity.ok().body(response);
    }
}
