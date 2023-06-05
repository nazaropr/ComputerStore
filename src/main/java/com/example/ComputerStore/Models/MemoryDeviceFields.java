package com.example.ComputerStore.Models;

import com.example.ComputerStore.Models.Fields_;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document(collection = "inventory")
@Data
@NoArgsConstructor
public class MemoryDeviceFields {
    @Field
    private List<Fields_> RAM;
    @Field
    private List<Fields_> hardDrives;
    @Field
    private List<Fields_> solidStateDrives;
    @Field
    private List<Fields_> DVD;
}
