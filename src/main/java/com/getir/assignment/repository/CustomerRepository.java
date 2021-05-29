package com.getir.assignment.repository;

import com.getir.assignment.domain.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;


public interface CustomerRepository extends MongoRepository<Customer, String> {
    Optional<Customer> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}