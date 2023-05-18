package com.example.cardtocard.dto;

import com.example.cardtocard.model.Amount;

public record TransferRequest(String cardFromNumber,
                              String cardToNumber,
                              String cardFromCVV,
                              String cardFromValidTill,
                              Amount amount) {
}
