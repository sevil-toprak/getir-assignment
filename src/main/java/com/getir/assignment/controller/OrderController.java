package com.getir.assignment.controller;

import com.getir.assignment.controller.exception.InvalidDataException;
import com.getir.assignment.controller.request.OrderCreateRequest;
import com.getir.assignment.controller.request.OrderStatusUpdateRequest;
import com.getir.assignment.domain.Order;
import com.getir.assignment.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create")
    public ResponseEntity<Order> createOrder(@Valid @RequestBody OrderCreateRequest orderCreateRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidDataException(bindingResult);
        }

        Order order = orderService.createOrder(orderCreateRequest);
        logger.debug("Order is created with id: {} and customerId:{}", order.getId(), order.getCustomerId());

        return ResponseEntity.ok(order);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderWithId(@PathVariable String orderId) {
        Order order = orderService.getOrderWithId(orderId);
        logger.debug("Order is returned with id:{}", order.getId());

        return ResponseEntity.ok(order);
    }

    @GetMapping("/all/{customerId}")
    public ResponseEntity<Page<Order>> getAllOrdersWithCustomerId(@PathVariable String customerId, @PageableDefault(size = 20) Pageable pageable) {
        Page<Order> orders = orderService.getAllOrdersWithCustomerId(customerId, pageable);
        logger.debug("Orders are returned with customerId:{}", customerId);

        return ResponseEntity.ok(orders);
    }

    @PutMapping("/update/status")
    public ResponseEntity<Order> updateOrderStatus(@Valid @RequestBody OrderStatusUpdateRequest orderStatusUpdateRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidDataException(bindingResult);
        }

        Order order = orderService.updateOrderStatus(orderStatusUpdateRequest);
        logger.debug("Order status is updated with id: {} and status:{}", order.getId(), order.getStatus());

        return ResponseEntity.ok(order);
    }

    @GetMapping("/date/{startDate}/{endDate}")
    public ResponseEntity<Page<Order>> getAllOrdersWithDate(@PathVariable String startDate, @PathVariable String endDate, @PageableDefault(size = 20) Pageable pageable) throws ParseException {
        Page<Order> orders = orderService.getAllOrdersWithDate(startDate, endDate, pageable);
        logger.debug("Orders are returned with startDate: {} and endDate: {}", startDate, endDate);

        return ResponseEntity.ok(orders);
    }

}
