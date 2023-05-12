package com.diego.charterexercise.strategy;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PointsOverAmountTest {

    PointsOverAmount pointsAwarder;

    @BeforeAll
    public void before() {
        pointsAwarder = new PointsOverAmount(100, 2);
    }

    @ParameterizedTest
    @MethodSource("providedAmountsAndPoints")
    void correctPointsPerAmount(long input, long expected) {
        assertEquals(expected, pointsAwarder.awardPoints(input));
    }

    private static Stream<Arguments> providedAmountsAndPoints() {
        return Stream.of(
                Arguments.of(100, 0),
                Arguments.of(101, 2),
                Arguments.of(120, 40)
        );
    }

}
