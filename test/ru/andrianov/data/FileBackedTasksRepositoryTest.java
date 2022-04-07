package ru.andrianov.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTasksRepositoryTest extends TaskRepositoryTest<FileBackedTasksRepository> {

    public FileBackedTasksRepositoryTest() {
        super(new FileBackedTasksRepository("test\\ru\\andrianov\\TaskRepository.csv"));
    }

    @BeforeEach
    void beforeEach() {
        taskRepository = new FileBackedTasksRepository("test\\ru\\andrianov\\TaskRepository.csv");
    }

    @Test
    void shouldBeWriteAndReadFromFile() {
        Integer task1ID = taskRepository.createNewTask(task1);
        Integer task2ID = taskRepository.createNewTask(task2);

        FileBackedTasksRepository tasksRepositoryTest = new FileBackedTasksRepository("test\\ru\\andrianov\\TaskRepository.csv");
        TaskRepositoryStorageService.restore(tasksRepositoryTest);

        Task testTask1 = tasksRepositoryTest.getTaskById(task1ID);
        Task testTask2 = tasksRepositoryTest.getTaskById(task2ID);

        assertEquals(task1, testTask1, "Задачи №1 не совпадают.");
        assertEquals(task2, testTask2, "Задачи №2 не совпадают.");

    }

}