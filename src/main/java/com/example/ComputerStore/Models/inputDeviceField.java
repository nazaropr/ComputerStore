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
public class inputDeviceField {
    @Field
    private List<Fields_> keyboards;
    @Field
    private List<Fields_> pointingDevices;

}
