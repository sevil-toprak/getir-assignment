package com.getir.assignment.service;

import com.getir.assignment.controller.exception.NotFoundException;
import com.getir.assignment.controller.request.StatisticsCreateRequest;
import com.getir.assignment.domain.Statistics;
import com.getir.assignment.enums.Roles;
import com.getir.assignment.repository.StatisticsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class StatisticsService {
    private final static Logger logger = LoggerFactory.getLogger(StatisticsService.class);

    private final StatisticsRepository statisticsRepository;

    public StatisticsService(StatisticsRepository statisticsRepository) {
        this.statisticsRepository = statisticsRepository;
    }

    public Statistics getStatisticsWithMonth(int month) {
        return statisticsRepository.findByMonth(month).orElseThrow(() -> new NotFoundException(String.valueOf(month)));
    }

    public Statistics addStatistics(StatisticsCreateRequest statisticsCreateRequest) {
        Statistics statistics = new Statistics();
        Statistics updatedStatistics = statisticsRepository.findByMonth(statisticsCreateRequest.getMonth()).orElse(null);

        if (updatedStatistics == null) {
            statistics.setMonth(statisticsCreateRequest.getMonth());
            statistics.setTotalBookCount(statisticsCreateRequest.getTotalBookCount());
            statistics.setTotalOrderCount(statisticsCreateRequest.getTotalOrderCount());
            statistics.setTotalPurchasedAmount(statisticsCreateRequest.getTotalPurchasedAmount());
        } else {
            int orderCount = updatedStatistics.getTotalOrderCount();
            int bookCount = updatedStatistics.getTotalBookCount();
            BigDecimal purchasedAmount = updatedStatistics.getTotalPurchasedAmount();

            statistics.setTotalOrderCount(orderCount + statisticsCreateRequest.getTotalOrderCount());
            statistics.setTotalBookCount(bookCount + statisticsCreateRequest.getTotalBookCount());
            statistics.setTotalPurchasedAmount(purchasedAmount.add(statisticsCreateRequest.getTotalPurchasedAmount()));
        }

        return statisticsRepository.save(statistics);
    }

}
