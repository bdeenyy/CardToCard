package com.example.cardtocard.controller;

import com.example.cardtocard.model.TransferRequest;
import com.example.cardtocard.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transfer")
public class TransferController {

    private final TransferService transferService;

    @Autowired
    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping
    public ResponseEntity<String> transfer(@RequestBody TransferRequest transferRequest) {
        if (transferService.transfer(transferRequest)) {
            return ResponseEntity.ok("Transfer successful");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Transfer unsuccessful");
        }
    }
}