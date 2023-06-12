package com.example.ComputerStore.Services;

import com.example.ComputerStore.Models.Transaction;
import com.example.ComputerStore.Repository.InventoryRepository;
import com.example.ComputerStore.Models.Inventory;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;
    public List<Inventory> allInventory(){
        var result = inventoryRepository.findAll();
        for (int i = 0; i < result.size(); i++) {
           var item = result.get(i);
            item.setIdStr(item.getId().toString());
        }
        return result;
    }
    public Optional<Inventory> getById(ObjectId id){
        var result = inventoryRepository.findById(id);
        result.get().setIdStr(result.get().getId().toString());
        return result;
    }

    public Inventory createProduct(String category, String subCategory, String manufacturer, String modelName, Integer purchasePrice, Integer sellingPrice, Integer quantityInStock){
        Inventory product = new Inventory(category, subCategory, manufacturer, modelName, purchasePrice, sellingPrice, quantityInStock);
        var result = inventoryRepository.insert(product);
        result.setIdStr(result.getId().toString());
        return result;
    }

    public Inventory updateProduct(ObjectId id, String category, String subCategory, String manufacturer, String modelName, Integer purchasePrice, Integer sellingPrice, Integer quantityInStock) {
        Optional<Inventory> optionalProduct = inventoryRepository.findById(id);

        if (optionalProduct.isPresent()) {
            Inventory product = optionalProduct.get();
            product.setCategory(category);
            product.setSubCategory(subCategory);
            product.setManufacturer(manufacturer);
            product.setModelName(modelName);
            product.setPurchasePrice(purchasePrice);
            product.setSellingPrice(sellingPrice);
            product.setQuantityInStock(quantityInStock);
            var result = inventoryRepository.save(product);
            result.setIdStr(result.getId().toString());
            return result;
        }

        return null;
    }
    public void deleteProduct(ObjectId id){
        inventoryRepository.deleteById(id);
    }


}
