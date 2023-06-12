package com.example.ComputerStore.Models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document(collection = "inventory")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {
    @Id
    private ObjectId id;
    @Field
    private String idStr;
    @Field
    private String category;
    @Field
    private String subCategory;
    @Field
    private String manufacturer;
    @Field
    private String modelName;
    @Field
    private Integer purchasePrice;
    @Field
    private Integer sellingPrice;
    @Field
    private Integer quantityInStock;

    public Inventory(String category, String manufacturer, String modelName, Integer purchasePrice, Integer sellingPrice, Integer quantityInStock){
        this.id = new ObjectId();
        this.idStr = this.id.toString();
        this.category = category;
        this.manufacturer = manufacturer;
        this.modelName = modelName;
        this.purchasePrice = purchasePrice;
        this.sellingPrice = sellingPrice;
        this.quantityInStock = quantityInStock;
    }
    public Inventory(String category, String subCategory, String manufacturer, String modelName, Integer purchasePrice, Integer sellingPrice, Integer quantityInStock){
        this.id = new ObjectId();
        this.idStr = this.id.toString();
        this.category = category;
        this.subCategory = subCategory;
        this.manufacturer = manufacturer;
        this.modelName = modelName;
        this.purchasePrice = purchasePrice;
        this.sellingPrice = sellingPrice;
        this.quantityInStock = quantityInStock;
    }
}