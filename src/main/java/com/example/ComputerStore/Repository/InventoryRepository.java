package com.example.ComputerStore.Repository;


import com.example.ComputerStore.Models.Fields_;
import com.example.ComputerStore.Models.Inventory;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepository extends MongoRepository<Inventory, ObjectId> {

}
