package com.example.ComputerStore.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.util.Date;

@Document(collection = "Transaction")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    private ObjectId id;
    @Field
    private String idStr;
    @Field
    private String category;
    @Field
    private LocalDate date;
    @Field
    private Integer sales;
    @Field
    private Integer sum;
    @Field
    private Integer profit;

    public Transaction (String category, LocalDate date, Integer sales, Integer sum, Integer profit){
        this.id = new ObjectId();
        this.idStr = this.id.toString();
        this.category = category;
        this.date = date;
        this.sales = sales;
        this.sum = sum;
        this.profit = profit;
    }
}
