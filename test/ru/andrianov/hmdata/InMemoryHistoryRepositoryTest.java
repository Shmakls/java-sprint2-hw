package ru.andrianov.hmdata;

import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryRepositoryTest extends HistoryRepositoryTest<InMemoryHistoryRepository> {

    @BeforeEach
    void beforeEach() {
        historyRepository = new InMemoryHistoryRepository();
    }
}