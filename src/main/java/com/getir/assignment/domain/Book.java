package com.getir.assignment.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Objects;

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

    @NotNull
    private Long stock;

    public Book() {

    }

    public Book(@NotNull @Size(max = 50) String name, @NotNull @Size(max = 50) String author_name, @NotNull @Size(max = 50) String year, @NotNull BigDecimal price, @NotNull Long stock) {
        this.name = name;
        this.author_name = author_name;
        this.year = year;
        this.price = price;
        this.stock = stock;
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

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id) &&
                Objects.equals(name, book.name) &&
                Objects.equals(author_name, book.author_name) &&
                Objects.equals(year, book.year) &&
                Objects.equals(price, book.price) &&
                Objects.equals(stock, book.stock);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, author_name, year, price, stock);
    }
}
