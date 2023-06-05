package com.example.ComputerStore.Controllers;

import com.example.ComputerStore.Models.Inventory;
import com.example.ComputerStore.Services.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {
    @Autowired
    private InventoryService inventoryService;
    @GetMapping
    public List<Inventory> getAllInventory(){
        return inventoryService.allInventory();
    }

}
