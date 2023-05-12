package com.diego.charterexercise.repository;

import com.diego.charterexercise.domain.Transaction;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Component
public class TransactionRepository {

    private static ArrayList<Transaction> transactions;

    public List<Transaction> getAllTransactions() {
        return transactions;
    }

    @PostConstruct
    private void init() {
        UUID user = UUID.fromString("c43e778d-50d5-49da-8d96-7964b0f929d7");
        LocalDate startDate = LocalDate.now().minusMonths(4);
        transactions = startDate.datesUntil(LocalDate.now())
                .map((date) -> new Transaction(user, date, ThreadLocalRandom.current().nextInt(1,500)))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
