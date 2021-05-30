package com.getir.assignment;

import com.getir.assignment.controller.request.BookCreateRequest;
import com.getir.assignment.domain.Book;

import java.math.BigDecimal;

public class TestDataGenerator {
    public static Book createBook() {
        Book book = new Book();
        book.setId("60b25a9f73cb2e2cbec57f98");
        book.setName("test book");
        book.setAuthor_name("1");
        book.setYear("2019");
        book.setPrice(new BigDecimal("100"));
        book.setStock(2L);

        return book;
    }

    public static BookCreateRequest getBookCreateRequest() {
        BookCreateRequest book = new BookCreateRequest();
        book.setName("test book");
        book.setAuthor_name("1");
        book.setYear("2019");
        book.setPrice(new BigDecimal("100"));
        book.setStock(2L);

        return book;
    }
}
