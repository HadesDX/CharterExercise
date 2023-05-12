package com.diego.charterexercise.strategy;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PointsBetweenRangeTest {

    PointsBetweenRange pointsAwarder;

    @BeforeAll
    public void before() {
        pointsAwarder = new PointsBetweenRange(50, 100, 1);
    }

    @ParameterizedTest
    @MethodSource("providedAmountsAndPoints")
    void correctPointsPerAmount(long input, long expected) {
        assertEquals(expected, pointsAwarder.awardPoints(input));
    }

    private static Stream<Arguments> providedAmountsAndPoints() {
        return Stream.of(
                Arguments.of(50, 1),
                Arguments.of(99, 50),
                Arguments.of(98, 49)
        );
    }
}
