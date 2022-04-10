package ru.andrianov.data;

import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskRepositoryTest extends TaskRepositoryTest<InMemoryTaskRepository> {

    @BeforeEach
    void beforeEach() {
        taskRepository = new InMemoryTaskRepository();
    }

}