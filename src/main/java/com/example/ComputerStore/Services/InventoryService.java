package com.example.ComputerStore.Services;

import com.example.ComputerStore.Repository.InventoryRepository;
import com.example.ComputerStore.Models.Inventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;
    public List<Inventory> allInventory(){
        return inventoryRepository.findAll();
    }
}
