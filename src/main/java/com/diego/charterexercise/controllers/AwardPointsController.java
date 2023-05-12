package com.diego.charterexercise.controllers;

import com.diego.charterexercise.domain.PointsResponse;
import com.diego.charterexercise.domain.Transaction;
import com.diego.charterexercise.repository.TransactionRepository;
import com.diego.charterexercise.strategy.PointsBetweenRange;
import com.diego.charterexercise.strategy.PointsOverAmount;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api")
public class AwardPointsController {
    private final TransactionRepository repository;

    public AwardPointsController(TransactionRepository repository) {
        this.repository = repository;
    }

    @GetMapping(value = "/points")
    public PointsResponse points(@RequestParam(value = "user") UUID user) {
        LocalDate now = LocalDate.now();
        Map<String, Long> pointsByMonths = repository.getAllTransactions().stream()
                .filter(transaction -> transaction.id().equals(user))
                .filter(transaction -> transaction.date().isAfter(now.minusMonths(2)))
                .parallel()
                .map(transaction -> new Object() {
                            final Transaction t = transaction;
                            final long p = new PointsOverAmount(100, 2).awardPoints(transaction.amount())
                                    + new PointsBetweenRange(50, 100, 1).awardPoints(transaction.amount());
                        }
                )
                .collect(Collectors.groupingBy(pair -> pair.t.date().getMonth().toString(),
                        Collectors.summingLong(pair -> pair.p)));

        long totalPoints = pointsByMonths.values().stream().reduce(0L, Long::sum);

        return new PointsResponse(pointsByMonths, totalPoints);
    }
}
