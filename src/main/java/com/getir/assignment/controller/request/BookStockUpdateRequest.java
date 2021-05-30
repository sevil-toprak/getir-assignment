package com.getir.assignment.controller.request;

import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class BookStockUpdateRequest {
    @Id
    @NotBlank(message = "Id is mandatory field!")
    private String id;

    @NotNull(message = "Stock is mandotory field!")
    private Long stock;

    public BookStockUpdateRequest(@NotBlank(message = "Id is mandatory field!") String id, @NotNull(message = "Stock is mandotory field!") Long stock) {
        this.id = id;
        this.stock = stock;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }
}
