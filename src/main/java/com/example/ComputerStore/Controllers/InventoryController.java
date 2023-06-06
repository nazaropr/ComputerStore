package com.example.ComputerStore.Controllers;

import com.example.ComputerStore.Models.Inventory;
import com.example.ComputerStore.Services.InventoryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {
    @Autowired
    private InventoryService inventoryService;
    @GetMapping
    public ResponseEntity<List<Inventory>> getAllInventory(){
        return new ResponseEntity<List<Inventory>> (inventoryService.allInventory(), HttpStatus.OK);
    }
   @GetMapping("/{id}")
    public ResponseEntity<Optional<Inventory>> getProductDetails(@PathVariable ObjectId id) {
        return new ResponseEntity<Optional<Inventory>>(inventoryService.getById(id), HttpStatus.OK);
    }
    
}
