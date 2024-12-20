package com.getir.assignment.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document(collection = "statistics")
public class Statistics {
    @Id
    private String id;

    private int month;

    private int totalOrderCount;

    private int totalBookCount;

    private BigDecimal totalPurchasedAmount;

    public Statistics() {

    }

    public Statistics(int month, int totalOrderCount, int totalBookCount, BigDecimal totalPurchasedAmount) {
        this.month = month;
        this.totalOrderCount = totalOrderCount;
        this.totalBookCount = totalBookCount;
        this.totalPurchasedAmount = totalPurchasedAmount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getTotalOrderCount() {
        return totalOrderCount;
    }

    public void setTotalOrderCount(int totalOrderCount) {
        this.totalOrderCount = totalOrderCount;
    }

    public int getTotalBookCount() {
        return totalBookCount;
    }

    public void setTotalBookCount(int totalBookCount) {
        this.totalBookCount = totalBookCount;
    }

    public BigDecimal getTotalPurchasedAmount() {
        return totalPurchasedAmount;
    }

    public void setTotalPurchasedAmount(BigDecimal totalPurchasedAmount) {
        this.totalPurchasedAmount = totalPurchasedAmount;
    }
}
