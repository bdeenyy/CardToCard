package com.example.cardtocard.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ConfirmRequest {
    private String operationId;
    private String code;

    @JsonCreator
    public ConfirmRequest(@JsonProperty("operationId")String operationId,
                          @JsonProperty("code")String code) {
        this.operationId = operationId;
        this.code = code;
    }

    public ConfirmRequest(Object operationId) {
        this.operationId = (String) operationId;
        this.code = null;
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
