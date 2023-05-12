package com.diego.charterexercise.controller;

import com.diego.charterexercise.domain.Transaction;
import com.diego.charterexercise.repository.TransactionRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AwardPointsControllerTest {
    @MockBean
    private TransactionRepository transactionRepository;
    @Autowired
    private MockMvc mockMvc;

    @ParameterizedTest
    @MethodSource("providedAmountsAndPoints")
    void correctPointsPerAmount(long input, long expected) throws Exception {
        List<Transaction> transactions = new ArrayList<>() {
            {
                add(new Transaction(UUID.fromString("c43e778d-50d5-49da-8d96-7964b0f929d7"), LocalDate.now(), input));
            }
        };
        Mockito.when(transactionRepository.getAllTransactions()).thenReturn(transactions);

        mockMvc.perform(get("/api/points")
                        .param("user", "c43e778d-50d5-49da-8d96-7964b0f929d7")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPoints", Matchers.is(expected), Long.class))
                .andExpect(jsonPath("$.pointsByMonths", Matchers.hasItems()));
    }

    private static Stream<Arguments> providedAmountsAndPoints() {
        return Stream.of(
                Arguments.of(-100, 0),
                Arguments.of(0, 0),
                Arguments.of(49, 0),
                Arguments.of(50, 1),
                Arguments.of(99, 50),
                Arguments.of(101, 52),
                Arguments.of(120, 90)
        );
    }

}
