package com.example.ComputerStore.Repository;

import com.example.ComputerStore.Models.Transaction;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends MongoRepository<Transaction, ObjectId> {
    List<Transaction> findByDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    List<Transaction> findByDateBetweenAndCategory(LocalDateTime startDate, LocalDateTime endDate, String category);
    List<Transaction> findByDateAndCategory(LocalDate date, String category);
}