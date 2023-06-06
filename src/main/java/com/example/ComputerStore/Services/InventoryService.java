package com.example.ComputerStore.Services;

import com.example.ComputerStore.Repository.InventoryRepository;
import com.example.ComputerStore.Models.Inventory;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;
    public List<Inventory> allInventory(){
        return inventoryRepository.findAll();
    }

    public Optional<Inventory> getById(ObjectId id){
        return inventoryRepository.findById(id);
    }
}
