package ru.andrianov.data;

import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTasksRepositoryTest extends TaskRepositoryTest<FileBackedTasksRepository> {

    public FileBackedTasksRepositoryTest() {
        super(new FileBackedTasksRepository("src\\ru\\andrianov\\TaskRepository.csv"));
    }

    @BeforeEach
    void beforeEach() {
        taskRepository = new FileBackedTasksRepository("src\\ru\\andrianov\\TaskRepository.csv");
    }

    

}