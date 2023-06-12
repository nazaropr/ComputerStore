package com.example.ComputerStore.Controllers;

import com.example.ComputerStore.Models.Inventory;
import com.example.ComputerStore.Models.Transaction;
import com.example.ComputerStore.Services.InventoryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
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
    @PutMapping("/{id}")
    public ResponseEntity<Inventory> updateProduct(@PathVariable String id, @RequestBody Map<String, String> payload) {

        String category = payload.get("category");
        String subCategory = payload.get("subCategory");
        String manufacturer = payload.get("manufacturer");
        String modelName = payload.get("modelName");
        Integer purchasePrice = Integer.valueOf(payload.get("purchasePrice"));
        Integer sellingPrice = Integer.valueOf(payload.get("sellingPrice"));
        Integer quantityInStock = Integer.valueOf(payload.get("quantityInStock"));

        Inventory updatedProduct = inventoryService.updateProduct(new ObjectId(id), category, subCategory, manufacturer, modelName, purchasePrice, sellingPrice, quantityInStock);

        if (updatedProduct != null) {
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Inventory> createProduct(@RequestBody Map<String, String> payload) {
        String category = payload.get("category");
        String subCategory = payload.get("subCategory");
        String manufacturer = payload.get("manufacturer");
        String modelName = payload.get("modelName");
        Integer purchasePrice = Integer.valueOf(payload.get("purchasePrice"));
        Integer sellingPrice = Integer.valueOf(payload.get("sellingPrice"));
        Integer quantityInStock = Integer.valueOf(payload.get("quantityInStock"));

        return new ResponseEntity<Inventory>(inventoryService.createProduct(category, subCategory, manufacturer, modelName, purchasePrice, sellingPrice, quantityInStock), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable ObjectId id){
        inventoryService.deleteProduct(id);
        return new ResponseEntity<>("Product deleted", HttpStatus.NO_CONTENT);
    }


}
