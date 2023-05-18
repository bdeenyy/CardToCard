package com.example.cardtocard.dto;

public class TransferResponse {
    private String operationId;

    public TransferResponse(String operationId) {
    }

    public String getOperationId() {
        return operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }

}
