package com.getir.assignment.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@Document(collection = "book")
public class Book {
    @Id
    private String id;

    @NotNull
    @Size(max = 50)
    private String name;

    @NotNull
    @Size(max = 50)
    private String author_name;

    @NotNull
    @Size(max = 50)
    private String year;

    @NotNull
    private BigDecimal price;

    public Book() {

    }

    public Book(@NotNull @Size(max = 50) String name, @NotNull @Size(max = 50) String author_name, @NotNull @Size(max = 50) String year, @NotNull BigDecimal price) {
        this.name = name;
        this.author_name = author_name;
        this.year = year;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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
