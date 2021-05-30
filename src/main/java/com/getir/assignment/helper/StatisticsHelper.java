package com.getir.assignment.helper;

import com.getir.assignment.controller.request.StatisticsCreateRequest;
import com.getir.assignment.domain.Book;
import com.getir.assignment.domain.Order;

import java.math.BigDecimal;
import java.util.Calendar;

public class StatisticsHelper {

    public static StatisticsCreateRequest createStatisticRequest(Order order) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(order.getCreateTime());
        int month = cal.get(Calendar.MONTH) + 1;

        int bookCount = 0;
        BigDecimal purchasedAmount = new BigDecimal("0");

        for (Book book : order.getBooks()) {
            bookCount += 1;
            purchasedAmount = purchasedAmount.add(book.getPrice());
        }

        return new StatisticsCreateRequest(month, 1, bookCount, purchasedAmount);

    }
}
