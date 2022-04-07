package ru.andrianov.hmdata;

import org.junit.jupiter.api.Test;
import ru.andrianov.data.Status;
import ru.andrianov.data.Task;
import ru.andrianov.data.Type;

import java.util.ArrayList;
import java.util.Collection;
import static org.junit.jupiter.api.Assertions.*;

abstract class HistoryRepositoryTest<T extends HistoryRepository> {

    T historyRepository;
    Task task1 = new Task("TestTask1", "DescriptionTestTask1", Status.NEW);
    Task task2 = new Task("TestTask2", "DescriptionTestTask2", Status.IN_PROGRESS);
    Task task3 = new Task("TestTask3", "DescriptionTestTask3", Status.IN_PROGRESS);

    public HistoryRepositoryTest(T historyRepository) {
        this.historyRepository = historyRepository;
        task1.setId(1);
        task1.setType(Type.TASK);
        task2.setId(2);
        task2.setType(Type.TASK);
        task3.setId(3);
        task3.setType(Type.TASK);
    }

    @Test
    void shouldBeAddViewedTaskToHistoryAndGetHistory() {
        Integer task1ID = task1.getId();
        historyRepository.add(task1);

        Collection<Task> testViewedTasks = historyRepository.getHistory();

        assertTrue(testViewedTasks.contains(task1));
    }

    @Test
    void shouldBeClearHistory() {
        historyRepository.add(task1);
        historyRepository.add(task2);

        Collection<Task> testViewedTask = historyRepository.getHistory();

        assertFalse(testViewedTask.isEmpty());

        historyRepository.clear();

        testViewedTask = historyRepository.getHistory();

        assertNull(testViewedTask);
    }

    @Test
    void shouldBeRemoveTaskFromHistoryByID() {
        Integer task2ID = task2.getId();
        historyRepository.add(task2);

        Collection<Task> testViewedTask = historyRepository.getHistory();

        assertFalse(testViewedTask.isEmpty());

        historyRepository.removeTaskFromHistoryById(task2ID);

        testViewedTask = historyRepository.getHistory();

        assertNull(testViewedTask);
    }

}