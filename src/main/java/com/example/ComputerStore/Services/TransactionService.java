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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


    public BigDecimal getTotalSalesByWeek(String startDateString, String endDateString) {
        LocalDate startDate = LocalDate.parse(startDateString);
        LocalDate endDate = LocalDate.parse(endDateString);

        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);

        List<Transaction> transactions = transactionRepository.findByDateBetween(startDateTime, endDateTime);

        BigDecimal totalSales = transactions.stream()
                .map(transaction -> BigDecimal.valueOf(transaction.getSales()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return totalSales;
    }

    public Transaction createTransaction(String category, LocalDate date, Integer sales, Integer sum, Integer profit){
        Transaction transaction = new Transaction(category, date, sales, sum, profit);
        var result = transactionRepository.insert(transaction);
        result.setIdStr(result.getId().toString());
        return result;
    }

    public Map<String, BigDecimal> getProfitByCategoryByPeriod(LocalDate startDate, LocalDate endDate, String category) {
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime;

        if (startDate.equals(endDate)) {
            endDateTime = startDate.atTime(LocalTime.MAX);
        } else {
            endDateTime = endDate.atTime(LocalTime.MAX);
        }

        List<Transaction> transactions;
        if (category != null) {
            transactions = transactionRepository.findByDateBetweenAndCategory(startDateTime, endDateTime, category);
        } else {
            transactions = transactionRepository.findByDateBetween(startDateTime, endDateTime);
        }

        Map<String, BigDecimal> profitByCategory = new HashMap<>();

        for (Transaction transaction : transactions) {
            String transactionCategory = transaction.getCategory();
            BigDecimal transactionProfit = BigDecimal.valueOf(transaction.getProfit());

            BigDecimal categoryProfit = profitByCategory.getOrDefault(transactionCategory, BigDecimal.ZERO);
            categoryProfit = categoryProfit.add(transactionProfit);

            profitByCategory.put(transactionCategory, categoryProfit);
        }

        return profitByCategory;
    }


}