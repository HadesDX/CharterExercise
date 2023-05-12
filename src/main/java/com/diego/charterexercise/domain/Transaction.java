package com.diego.charterexercise.domain;

import java.util.UUID;

public record Transaction(UUID id, java.time.LocalDate date, long amount) {
}
