package com.example.cardtocard.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TransferRequest {

    private String cardFromNumber;
    private String cardFromValidTill;
    private String cardFromCVV;
    private String cardToNumber;
    private Amount amount;

    @JsonCreator
    public TransferRequest(@JsonProperty("cardFromNumber") String cardFromNumber,
                           @JsonProperty("cardToNumber") String cardToNumber,
                           @JsonProperty("cardFromCVV") String cardFromCVV,
                           @JsonProperty("cardFromValidTill") String cardFromValidTill,
                           @JsonProperty("amount") Amount amount) {
        this.cardFromNumber = cardFromNumber;
        this.cardToNumber = cardToNumber;
        this.cardFromCVV = cardFromCVV;
        this.cardFromValidTill = cardFromValidTill;
        this.amount = amount;
    }

    public TransferRequest() {

    }


    public String getCardFromNumber() {
        return cardFromNumber;
    }


    public String getCardFromValidTill() {
        return cardFromValidTill;
    }


    public String getCardFromCVV() {
        return cardFromCVV;
    }


    public String getCardToNumber() {
        return cardToNumber;
    }


    public Amount getAmount() {
        return amount;
    }

    public void setCardFromNumber(String cardFromNumber) {
        this.cardFromNumber = cardFromNumber;
    }

    public void setCardFromValidTill(String cardFromValidTill) {
        this.cardFromValidTill = cardFromValidTill;
    }

    public void setCardFromCVV(String cardFromCVV) {
        this.cardFromCVV = cardFromCVV;
    }

    public void setCardToNumber(String cardToNumber) {
        this.cardToNumber = cardToNumber;
    }

    public void setAmount(Amount amount) {
        this.amount = amount;
    }
}