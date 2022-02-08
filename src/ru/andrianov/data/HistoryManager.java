package ru.andrianov.data;

import ru.andrianov.data.Task;

import java.util.List;

public interface HistoryManager {

    void add(Task task);

    List<Task> getHistory();

    void removeTaskFromHistoryById(Integer taskId);

    void clearHistory();

}
