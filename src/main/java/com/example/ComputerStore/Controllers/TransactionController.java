package com.example.ComputerStore.Controllers;

import com.example.ComputerStore.Models.Report;
import com.example.ComputerStore.Models.Transaction;
import com.example.ComputerStore.Services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
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
@GetMapping("/report/profit-by-category")
public ResponseEntity<String> getProfitByCategoryReport(
        @RequestParam String period,
        @RequestParam String startDate,
        @RequestParam(required = false) String endDate,
        @RequestParam(required = false) String category) throws Exception {

    LocalDate start = LocalDate.parse(startDate);
    LocalDateTime endDateTime;

    if (endDate != null) {
        endDateTime = LocalDateTime.parse(endDate + "T23:59:59");
    } else {
        endDateTime = LocalDateTime.of(start, LocalTime.MAX);
    }

    if (start.equals(endDateTime.toLocalDate())) {
        endDateTime = endDateTime.withHour(23).withMinute(59).withSecond(59);
    }

    Map<String, BigDecimal> profitByCategory = transactionService.getProfitByCategoryByPeriod(start, endDateTime.toLocalDate(), category);

    Report report = new Report();
    report.setProfitByCategory(profitByCategory);
    report.setPeriod(start + " to " + endDateTime.toLocalDate());

    StringWriter sw = new StringWriter();
    JAXBContext context = JAXBContext.newInstance(Report.class);
    Marshaller marshaller = context.createMarshaller();
    marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
    marshaller.marshal(report, sw);

    return new ResponseEntity<>(sw.toString(), HttpStatus.OK);
}



}

