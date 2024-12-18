package FinTrack.backend.services;

import FinTrack.backend.dto.BudgetRequest;
import FinTrack.backend.models.Budget;
import FinTrack.backend.models.User;
import FinTrack.backend.repositories.BudgetRepository;
import FinTrack.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;


import java.util.List;

@Service
public class BudgetService {
    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private UserRepository userRepository;

    // Create a budget
    public Budget addBudget(Budget budget) {
        return budgetRepository.save(budget);
    }

    // Get all budgets
    public List<Budget> getAllBudgets() {
        return budgetRepository.findAll();
    }

    public List<Budget> addOrUpdateBudgets(Long userId, List<Budget> budgets) {
        for (Budget budget : budgets) {
            Budget existingBudget = budgetRepository.findByUserIdAndCategory(userId, budget.getCategory());
            if (existingBudget != null) {
                // Update existing budget
                existingBudget.setAmount(budget.getAmount());
                budgetRepository.save(existingBudget);
            } else {
                // Add new budget
                budget.setUserId(userId);
                budgetRepository.save(budget);
            }
        }
        // Return all budgets for the user
        return budgetRepository.findAllByUserId(userId);
    }

    // Get budget by ID
    public Budget getBudgetById(Long id) {
        return budgetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Budget not found"));
    }

    // Get budgets for a specific user
    public List<Budget> getBudgetsByUserId(Long userId) {
        return budgetRepository.findAll().stream()
                .filter(budget -> budget.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    // Update budget
    public Budget updateBudget(Long id, Budget updatedBudget) {
        Budget budget = getBudgetById(id); // Reuse logic
        budget.setCategory(updatedBudget.getCategory());
        budget.setAmount(updatedBudget.getAmount());
        return budgetRepository.save(budget);
    }

    public List<Budget> addBudgets(List<Budget> budgets) {
        return budgetRepository.saveAll(budgets);
    }

    // Delete budget
    public void deleteBudget(Long id) {
        budgetRepository.deleteById(id);
    }
}

