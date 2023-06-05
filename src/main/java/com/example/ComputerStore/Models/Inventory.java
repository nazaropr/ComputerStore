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
    private List<Fields_> desktopComputers;
    @Field
    private List<Fields_> monitors;
    @Field
    private List<Fields_> laptops;
    @Field
    private List<Fields_> tablets;
    @Field
    private List<Fields_> printers;
    @Field
    private MemoryDeviceFields memoryDevices;
    @Field
    private List<Fields_> processors;
    @Field
    private List<Fields_> powerSupplies;
   @Field
    private inputDeviceField inputDevices;
}