package com.getir.assignment.service;

import com.getir.assignment.controller.exception.NotFoundException;
import com.getir.assignment.controller.request.BookStockUpdateRequest;
import com.getir.assignment.controller.request.OrderCreateRequest;
import com.getir.assignment.controller.request.OrderStatusUpdateRequest;
import com.getir.assignment.domain.Book;
import com.getir.assignment.domain.Order;
import com.getir.assignment.controller.request.OrderBookRequestObject;
import com.getir.assignment.enums.Status;
import com.getir.assignment.helper.BookHelper;
import com.getir.assignment.helper.StatisticsHelper;
import com.getir.assignment.repository.BookRepository;
import com.getir.assignment.repository.CustomerRepository;
import com.getir.assignment.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Service
public class OrderService {
    private final static Logger logger = LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository orderRepository;

    private final CustomerRepository customerRepository;

    private final BookRepository bookRepository;

    private final StatisticsService statisticsService;

    private final BookService bookService;

    public OrderService(OrderRepository orderRepository, CustomerRepository customerRepository,
                        BookRepository bookRepository, StatisticsService statisticsService, BookService bookService) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.bookRepository = bookRepository;
        this.statisticsService = statisticsService;
        this.bookService = bookService;
    }

    public Order createOrder(OrderCreateRequest orderCreateRequest) {
        List<OrderBookRequestObject> orderBookList = orderCreateRequest.getBooks();
        List<Book> books = new ArrayList<>();

        for (OrderBookRequestObject orderBookRequestObject : orderBookList) {
            Book book = bookRepository.findById(orderBookRequestObject.getId()).orElseThrow(() -> new NotFoundException(orderBookRequestObject.getId()));
            book.setStock(Long.valueOf(orderBookRequestObject.getCount()));
            books.add(book);
        }

        Order order = new Order(orderCreateRequest.getCustomerId(),
                books, Status.PROCESSING, new Date(), new Date());

        List<BookStockUpdateRequest> updateRequests = BookHelper.createBookStockUpdateRequests(order);
        for (BookStockUpdateRequest req : updateRequests) {
            bookService.updateBookStockFromOrder(req);
        }

        statisticsService.addStatistics(StatisticsHelper.createStatisticRequest(order));
        return orderRepository.save(order);
    }

    public Order getOrderWithId(String orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new NotFoundException(orderId));
    }

    public Page<Order> getAllOrdersWithCustomerId(String customerId, Pageable pageable) {

        if (customerRepository.findById(customerId).isEmpty()) {
            logger.debug("Get all orders with customerId, customer not found with id: {}", customerId);
            throw new NotFoundException(customerId);
        }

        return orderRepository.findAllBycustomerId(customerId, pageable);
    }

    public Page<Order> getAllOrdersWithDate(String startDate, String endDate, Pageable pageable) throws ParseException {
//        Date from = new SimpleDateFormat("YY/MMM/d").parse(startDate.replace(".", "/"));
//        Date to = new SimpleDateFormat("dd/MM/yyyy").parse(endDate.replace(".", "/"));

        String pattern = "dd-M-yyyy hh:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date from = simpleDateFormat.parse(startDate.replace(".", "/"));
        Date to = simpleDateFormat.parse(endDate.replace(".", "/"));

        return orderRepository.getOrderByDate(from, to, pageable);
    }

    public Order updateOrderStatus(OrderStatusUpdateRequest orderStatusUpdateRequest) {
        Order order = orderRepository.findById(orderStatusUpdateRequest.getId()).orElseThrow(() -> new NotFoundException(orderStatusUpdateRequest.getId()));
        order.setStatus(Status.valueOf(orderStatusUpdateRequest.getStatus().toUpperCase()));

        return orderRepository.save(order);
    }


}
