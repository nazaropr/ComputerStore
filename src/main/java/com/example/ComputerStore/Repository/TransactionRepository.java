package com.example.ComputerStore.Repository;

import com.example.ComputerStore.Models.Inventory;
import com.example.ComputerStore.Models.Transaction;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends MongoRepository<Transaction, ObjectId> {

}