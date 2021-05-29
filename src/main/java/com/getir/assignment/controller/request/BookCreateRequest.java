package com.getir.assignment.controller.request;

import javax.validation.constraints.*;
import java.math.BigDecimal;

public class BookCreateRequest {
    @NotBlank(message = "Name is mandatory field!")
    @Size(max = 50, message = "Name size must be must be between 0 and 50!")
    private String name;

    @NotBlank(message = "Author name is mandatory field!")
    @Size(max = 50, message = "Author name size must be must be between 0 and 50!")
    private String author_name;

    @NotBlank(message = "Year is mandatory field!")
    @Size(max = 50, message = "Year size must be must be between 0 and 50!")
    private String year;

    @NotNull(message = "Price is mandotory field!")
    private BigDecimal price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
