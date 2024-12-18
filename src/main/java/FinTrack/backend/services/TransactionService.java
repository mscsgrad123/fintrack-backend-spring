package FinTrack.backend.services;

import FinTrack.backend.dto.TransactionRequest;
import FinTrack.backend.models.Transaction;
import FinTrack.backend.models.User;
import FinTrack.backend.repositories.TransactionRepository;
import FinTrack.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    // Create a transaction
    public Transaction addTransaction(Transaction transaction) {
        // Save and return the Transaction
        return transactionRepository.save(transaction);
    }

    // Get all transactions
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    // Get transaction by ID
    public Transaction getTransactionById(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
    }

    public List<Transaction> getTransactionsByUserId(Long userId) {
        return transactionRepository.findByUserId(userId,Sort.by(Sort.Direction.DESC, "date"));
    }

    // Update transaction
    public Transaction updateTransaction(Long id, Transaction updatedTransaction) {
        Transaction transaction = getTransactionById(id);
        transaction.setDate(updatedTransaction.getDate());// Reuse logic
        transaction.setType(updatedTransaction.getType());
        transaction.setAmount(updatedTransaction.getAmount());
        transaction.setCategory(updatedTransaction.getCategory());
        transaction.setMethod(updatedTransaction.getMethod());
        transaction.setNotes(updatedTransaction.getNotes());
        return transactionRepository.save(transaction);
    }

    // Delete transaction
    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }
}

