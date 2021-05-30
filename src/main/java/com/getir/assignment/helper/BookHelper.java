package com.getir.assignment.helper;

import com.getir.assignment.controller.request.BookStockUpdateRequest;
import com.getir.assignment.controller.request.StatisticsCreateRequest;
import com.getir.assignment.domain.Book;
import com.getir.assignment.domain.Order;
import io.swagger.models.auth.In;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class BookHelper {

    public static List<BookStockUpdateRequest> createBookStockUpdateRequests(Order order) {
        List<BookStockUpdateRequest> requestList = new ArrayList<>();

        for (Book book : order.getBooks()) {
            requestList.add(new BookStockUpdateRequest(book.getId(), book.getStock()));
        }

        return requestList;
    }
}
