package FinTrack.backend.repositories;

import FinTrack.backend.models.Transaction;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUserId(Long userId, Sort sort);
    void deleteAllByUserId(Long userId);
}

