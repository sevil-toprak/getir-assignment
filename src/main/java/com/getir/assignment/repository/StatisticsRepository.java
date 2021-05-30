package com.getir.assignment.repository;

import com.getir.assignment.domain.Statistics;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface StatisticsRepository extends MongoRepository<Statistics, String> {

    Optional<Statistics> findByMonth(int month);
}