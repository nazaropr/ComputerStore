package com.example.ComputerStore.Controllers;

import com.example.ComputerStore.Models.Report;
import com.example.ComputerStore.Models.Transaction;
import com.example.ComputerStore.Services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/inventory")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @GetMapping("/all-transaction")
    public ResponseEntity<List<Transaction>> getAllTransaction(){
        return new ResponseEntity<List<Transaction>> (transactionService.allTransaction(), HttpStatus.OK);
    }
    @PostMapping("/transaction")
    public ResponseEntity<Transaction> createTransaction(@RequestBody Map<String, String> payload){
        String category = payload.get("category");
        LocalDate date = LocalDate.parse(payload.get("date"));
        Integer sales = Integer.valueOf(payload.get("sales"));
        Integer sum = Integer.valueOf(payload.get("sum"));
        Integer profit = Integer.valueOf(payload.get("profit"));
        return new ResponseEntity<Transaction>(transactionService.createTransaction(category, date, sales, sum, profit), HttpStatus.OK);
    }

    @GetMapping("/report/total-sales")
    public ResponseEntity<String> getTotalSalesReport(@RequestParam String period) throws Exception {

        List<Transaction> transactions = transactionService.getTransactionsByPeriod(period);

        BigDecimal totalSales = transactions.stream()
                .map(transaction -> BigDecimal.valueOf(transaction.getSales()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Report report = new Report();
        report.setTotalSales(totalSales);

        StringWriter sw = new StringWriter();
        JAXBContext context = JAXBContext.newInstance(Report.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(report, sw);

        return new ResponseEntity<>(sw.toString(), HttpStatus.OK);
    }

    @GetMapping("/report/profit-by-category")
    public ResponseEntity<String> getProfitByCategoryReport(@RequestParam String period) throws Exception {
        // Отримайте дані з бази даних відповідно до вказаного періоду
        List<Transaction> transactions = transactionService.getTransactionsByPeriod(period);

        // Створіть мапу для зберігання прибутку за категоріями
        Map<String, BigDecimal> profitByCategory = new HashMap<>();

        // Обчисліть прибуток за категоріями
        for (Transaction transaction : transactions) {
            String category = transaction.getCategory();
            BigDecimal profit = profitByCategory.getOrDefault(category, BigDecimal.ZERO);
            profit = profit.add(BigDecimal.valueOf(transaction.getProfit()));
            profitByCategory.put(category, profit);
        }

        // Створіть об'єкт звіту
        Report report = new Report();
        report.setProfitByCategory(profitByCategory);

        // Створіть XML-представлення звіту
        StringWriter sw = new StringWriter();
        JAXBContext context = JAXBContext.newInstance(Report.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(report, sw);

        // Поверніть XML-представлення звіту як рядок
        return new ResponseEntity<>(sw.toString(), HttpStatus.OK);
    }

    @GetMapping("/report/total-sales-by-period")
    public ResponseEntity<String> getTotalSalesByPeriodReport(@RequestParam String period) throws Exception {
        // Отримайте дані з бази даних відповідно до вказаного періоду
        List<Transaction> transactions = transactionService.getTransactionsByPeriod(period);

        // Обчисліть суму проданих товарів
        int totalSales = transactions.stream()
                .mapToInt(Transaction::getSum)
                .sum();

        // Створіть об'єкт звіту
        Report report = new Report();
        report.setTotalSales(BigDecimal.valueOf(totalSales));

        // Створіть XML-представлення звіту
        StringWriter sw = new StringWriter();
        JAXBContext context = JAXBContext.newInstance(Report.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(report, sw);

        // Поверніть XML-представлення звіту як рядок
        return new ResponseEntity<>(sw.toString(), HttpStatus.OK);
    }



}

