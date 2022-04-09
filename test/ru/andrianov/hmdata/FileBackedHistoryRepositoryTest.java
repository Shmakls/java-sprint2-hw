package ru.andrianov.hmdata;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.andrianov.data.Task;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedHistoryRepositoryTest extends HistoryRepositoryTest<FileBackedHistoryRepository> {

    public FileBackedHistoryRepositoryTest() {
        super(new FileBackedHistoryRepository("src\\ru\\andrianov\\HistoryRepository.csv"));
    }

    @BeforeEach
    void beforeEach() {
        historyRepository = new FileBackedHistoryRepository("src\\ru\\andrianov\\HistoryRepository.csv");
    }

    @Test
    void shouldBeWriteAndReadFromFile() {
        Integer task1Id = task1.getId();
        Integer task2Id = task2.getId();

        historyRepository.add(task1);
        historyRepository.add(task2);

        FileBackedHistoryRepository testHistoryRepository = new FileBackedHistoryRepository("src\\ru\\andrianov\\HistoryRepository.csv");

        HistoryRepositoryStorageService.restore(testHistoryRepository);

        ArrayList<Task> tasks = (ArrayList<Task>) testHistoryRepository.getHistory();

        Task testTask1 = tasks.get(0);
        Task testTask2 = tasks.get(1);

        assertEquals(task1, testTask1);
        assertEquals(task2, testTask2);

    }
}