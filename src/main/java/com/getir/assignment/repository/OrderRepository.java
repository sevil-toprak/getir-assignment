package com.getir.assignment.repository;

import com.getir.assignment.domain.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order, String> {

    Page<Order> findAllBycustomerId(String customerId, Pageable pageable);
}