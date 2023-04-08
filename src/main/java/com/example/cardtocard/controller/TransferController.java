package com.example.cardtocard.controller;

import com.example.cardtocard.model.ResponseBuilder;
import com.example.cardtocard.model.TransferRequest;
import com.example.cardtocard.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class TransferController {

    private final TransferService transferService;

    @Autowired
    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }


    @PostMapping("/transfer")
    public ResponseEntity<?> transfer(@RequestBody TransferRequest transferRequest) {
        String operationId = transferService.transfer(transferRequest);
        ResponseBuilder builder = new ResponseBuilder();
        return builder.buildResponse(operationId);
    }

}