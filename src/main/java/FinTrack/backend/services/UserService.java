package FinTrack.backend.services;

import FinTrack.backend.models.User;
import FinTrack.backend.repositories.BudgetRepository;
import FinTrack.backend.repositories.TransactionRepository;
import FinTrack.backend.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;


    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private BudgetRepository budgetRepository;

    public User createUserIfNotExists(String name, String email) {
        // Check if user exists
        Optional<User> existingUser = userRepository.findByEmail(email);

        if (existingUser.isPresent()) {
            return existingUser.get(); // Return the existing user
        }

        // Create a new user
        User newUser = new User();
        newUser.setName(name);
        newUser.setEmail(email);
        return userRepository.save(newUser);
    }

    // Create a new user
    public User registerUser(User user) {
        //user.setPassword(passwordEncoder.encode(user.getPassword())); // Encode password
        return userRepository.save(user);
    }

    // Get a user by ID
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    // Update user
    public User updateUser(Long id, User updatedUser) {
        User user = getUserById(id); // Reuse logic
        user.setName(updatedUser.getName());
        user.setEmail(updatedUser.getEmail());
      //  user.setPassword(passwordEncoder.encode(updatedUser.getPassword())); // Re-encode password
        return userRepository.save(user);
    }

    // Delete user
    @Transactional
    public void deleteUser(Long id) {
        User user = getUserById(id);

        // Delete all transactions associated with the user
        transactionRepository.deleteAllByUserId(id);

        // Delete all budgets associated with the user
        budgetRepository.deleteAllByUserId(id);

        // Finally, delete the user
        userRepository.delete(user);
    }
}

