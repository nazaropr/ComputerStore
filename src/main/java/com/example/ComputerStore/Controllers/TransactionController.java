package com.example.ComputerStore.Controllers;

import com.example.ComputerStore.Models.Report;
import com.example.ComputerStore.Models.Transaction;
import com.example.ComputerStore.Services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

    @GetMapping("/report/total-sales/day")
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

    @GetMapping("/report/total-sales/week")
    public ResponseEntity<String> getTotalSalesByWeek(@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String startDate,
                                                      @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String endDate) throws Exception {

        BigDecimal totalSales = transactionService.getTotalSalesByWeek(startDate, endDate);

        Report report = new Report();
        report.setTotalSales(totalSales);

        StringWriter sw = new StringWriter();
        JAXBContext context = JAXBContext.newInstance(Report.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(report, sw);

        return new ResponseEntity<>(sw.toString(), HttpStatus.OK);
    }

    @GetMapping("/report/total-sales/month")
    public ResponseEntity<String> getTotalSalesByMonth(@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String startDate,
                                                       @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String endDate) throws Exception {

        BigDecimal totalSales = transactionService.getTotalSalesByWeek(startDate, endDate);

        Report report = new Report();
        report.setTotalSales(totalSales);

        StringWriter sw = new StringWriter();
        JAXBContext context = JAXBContext.newInstance(Report.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(report, sw);

        return new ResponseEntity<>(sw.toString(), HttpStatus.OK);
    }
/***/

    @GetMapping("/report/profit-by-category/month")
    public ResponseEntity<String> getProfitByCategoryReportByMonth(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String endDate,
            @RequestParam String category) throws Exception {

        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);

        Map<String, BigDecimal> profitByCategory = transactionService.getProfitByCategoryByPeriod(start, end, category);

        if (profitByCategory.isEmpty()) {
            Report report = new Report();
            Map<String, BigDecimal> emptyProfitByCategory = new HashMap<>();
            emptyProfitByCategory.put(category, BigDecimal.ZERO);
            report.setProfitByCategory(emptyProfitByCategory);
            report.setPeriod(startDate + " to " + endDate);

            // Серіалізація звіту в XML
            StringWriter sw = new StringWriter();
            JAXBContext context = JAXBContext.newInstance(Report.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(report, sw);

            // Повернення відповіді з серіалізованим звітом
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_XML).body(sw.toString());
        }

        Report report = new Report();
        report.setProfitByCategory(profitByCategory);
        report.setPeriod(startDate + " to " + endDate);

        // Серіалізація звіту в XML
        StringWriter sw = new StringWriter();
        JAXBContext context = JAXBContext.newInstance(Report.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(report, sw);

        // Повернення відповіді з серіалізованим звітом
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_XML).body(sw.toString());
    }



}

