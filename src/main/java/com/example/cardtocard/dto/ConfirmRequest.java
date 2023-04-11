package com.example.cardtocard.dto;

public class ConfirmRequest {
    private String operationId;
    private String code;

    public ConfirmRequest(String code, String operationId) {
        this.operationId = operationId;
        this.code = code;
    }

    public ConfirmRequest(String operationId) {
        this.operationId =  operationId;
    }

    public ConfirmRequest() {
    }

    public String getOperationId() {
        return operationId;
    }

    public String getCode() {
        return code;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }
}
