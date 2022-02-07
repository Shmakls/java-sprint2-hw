package ru.andrianov.data;

import ru.andrianov.data.HistoryManager;
import ru.andrianov.data.Task;
import ru.andrianov.data.InMemoryTaskRepository;

import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private InMemoryTaskRepository taskRepository;

    public InMemoryHistoryManager(InMemoryTaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public void add(Task task) {

    }

    @Override
    public List<Task> getHistory() {
        System.out.println("Список последних 10 просмотренных задач получен!");
        return taskRepository.getViewedTask();
    }
}
