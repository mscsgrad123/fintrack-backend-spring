package FinTrack.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BudgetRequest {
    private Long user_id; // Accept user_id directly
    private String category;
    private double amount;

    // Getters and Setters
}
