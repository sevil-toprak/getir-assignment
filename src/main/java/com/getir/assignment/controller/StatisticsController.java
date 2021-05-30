package com.getir.assignment.controller;

import com.getir.assignment.controller.exception.InvalidDataException;
import com.getir.assignment.controller.request.BookCreateRequest;
import com.getir.assignment.controller.request.StatisticsCreateRequest;
import com.getir.assignment.domain.Book;
import com.getir.assignment.domain.Statistics;
import com.getir.assignment.service.StatisticsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {
    private final static Logger logger = LoggerFactory.getLogger(StatisticsController.class);

    StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @PostMapping("/create")
    public ResponseEntity<Statistics> createOrUpdate(@Valid @RequestBody StatisticsCreateRequest statisticsCreateRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidDataException(bindingResult);
        }

        Statistics statistics = statisticsService.addStatistics(statisticsCreateRequest);
        logger.debug("Statistics is created or updated with id: {} and month: {}", statistics.getId(), statistics.getMonth());

        return ResponseEntity.ok(statistics);
    }

    @GetMapping("/{month}")
    public ResponseEntity<Statistics> getStatisticsWithMonth(@PathVariable int month) {
        Statistics statistics = statisticsService.getStatisticsWithMonth(month);
        logger.debug("Statistics is returned with month:{}", month);

        return ResponseEntity.ok(statistics);
    }

}
