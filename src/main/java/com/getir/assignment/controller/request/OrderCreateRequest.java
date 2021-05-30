package com.getir.assignment.controller.request;

import org.springframework.data.mongodb.core.mapping.DBRef;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

public class OrderCreateRequest {
    @NotBlank(message = "CustomerId is mandatory field!")
    private String customerId;

    @DBRef
    private List<OrderBookRequestObject> books = new ArrayList<>();

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public List<OrderBookRequestObject> getBooks() {
        return books;
    }

    public void setBooks(List<OrderBookRequestObject> books) {
        this.books = books;
    }
}
