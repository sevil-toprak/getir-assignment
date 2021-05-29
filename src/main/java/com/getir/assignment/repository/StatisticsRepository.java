package com.getir.assignment.repository;

import com.getir.assignment.domain.Statistics;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StatisticsRepository extends MongoRepository<Statistics, String> {

}