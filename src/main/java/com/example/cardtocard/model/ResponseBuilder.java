package com.example.cardtocard.model;

import com.example.cardtocard.exception.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseBuilder {
    public ResponseEntity<?> buildResponse(String operationId) {
        if (operationId != null) {
            Map<String, String> response = new HashMap<>();
            response.put("operationId", operationId);
            return ResponseEntity.ok().body(response);
        } else {
            ErrorDetails errorDetails = new ErrorDetails();
            errorDetails.setMessage("Transfer unsuccessful");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
        }
    }

    public ResponseEntity<?> buildResponse(HttpStatus status, String message, Integer id) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setMessage(message);
        errorDetails.setId(id);
        return ResponseEntity.status(status).body(errorDetails);
    }
}
