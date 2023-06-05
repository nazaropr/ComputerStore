package com.example.ComputerStore.Repository;


import com.example.ComputerStore.Models.Inventory;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends MongoRepository<Inventory, ObjectId> {

}
