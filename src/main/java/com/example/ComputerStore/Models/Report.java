package com.example.ComputerStore.Models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@XmlRootElement(name = "report")
@XmlAccessorType(XmlAccessType.FIELD)
public class Report {

    @XmlElement
    private BigDecimal totalSales;

    @XmlElement
    private BigDecimal totalProfit;

    private Map<String, BigDecimal> profitByCategory;
    private String period;
    private String generatedBy;

    public void setProfitByCategory(Map<String, BigDecimal> profitByCategory) {
        this.profitByCategory = profitByCategory;
    }

    public Map<String, BigDecimal> getProfitByCategory() {
        return profitByCategory;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getPeriod() {
        return period;
    }

    public void setGeneratedBy(String generatedBy) {
        this.generatedBy = generatedBy;
    }

    public String getGeneratedBy() {
        return generatedBy;
    }

    public BigDecimal getTotalProfit() {
        BigDecimal totalProfit = BigDecimal.ZERO;
        for (BigDecimal profit : profitByCategory.values()) {
            totalProfit = totalProfit.add(profit);
        }
        return totalProfit;
    }


    public void setTotalSales(BigDecimal totalSales) {
        this.totalSales = totalSales;
    }

    public BigDecimal getTotalSales() {
        return totalSales;
    }

}