package com.example.ComputerStore.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
@Document(collection = "inventory")
@Data
@NoArgsConstructor

public class Fields_ {
    @Field
    private Integer idm;
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
}