package FinTrack.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TransactionRequest {
    // Getters and Setters
    private Long user_id; // Accept user_id directly
    private String type;
    private double amount;
    private String category;
    private String method;
    private String notes;

}

