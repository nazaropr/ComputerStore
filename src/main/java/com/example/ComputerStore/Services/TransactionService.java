package com.example.ComputerStore.Services;

import com.example.ComputerStore.Models.Inventory;
import com.example.ComputerStore.Models.Transaction;
import com.example.ComputerStore.Repository.TransactionRepository;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private MongoTemplate mongoTemplate;


    public List<Transaction> allTransaction(){
        var result = transactionRepository.findAll();
        for (int i = 0; i < result.size(); i++) {
            var item = result.get(i);
            item.setIdStr(item.getId().toString());
        }
        return result;
    }
   /* */

    public List<Transaction> getTransactionsByPeriod(String period) {
        LocalDate startDate;
        LocalDate endDate;

        LocalDate today = LocalDate.now();

        switch (period) {
            case "day":
                startDate = today;
                endDate = today;
                break;
            case "week":
                startDate = today.minusWeeks(1);
                endDate = today;
                break;
            case "month":
                startDate = today.minusMonths(1);
                endDate = today;
                break;
            default:
                startDate = today.minusMonths(1);
                endDate = today;
                break;
        }

        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);

        Criteria criteria = new Criteria()
                .andOperator(
                        Criteria.where("date").gte(startDateTime),
                        Criteria.where("date").lte(endDateTime)
                );

        Query query = new Query(criteria);
        return mongoTemplate.find(query, Transaction.class);
    }




    public Transaction createTransaction(String category, LocalDate date, Integer sales, Integer sum, Integer profit){
        Transaction transaction = new Transaction(category, date, sales, sum, profit);
        var result = transactionRepository.insert(transaction);
        result.setIdStr(result.getId().toString());
        return result;
    }
}