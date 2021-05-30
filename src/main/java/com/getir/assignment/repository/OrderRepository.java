package com.getir.assignment.repository;

import com.getir.assignment.domain.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Date;

public interface OrderRepository extends MongoRepository<Order, String> {

    Page<Order> findAllBycustomerId(String customerId, Pageable pageable);

    @Query("{'date' : { $gte: ?0, $lte: ?1 } }")
    Page<Order> getOrderByDate(Date startDate, Date endDate, Pageable pageable);
}