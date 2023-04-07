package com.example.cardtocard.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TransferRequest {

    private final String cardFromNumber;
    private final String cardFromValidTill;
    private final String cardFromCVV;
    private final String cardToNumber;
    private final Amount amount;

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

}