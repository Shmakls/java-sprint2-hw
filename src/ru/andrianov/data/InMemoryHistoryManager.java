package ru.andrianov.data;

import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager {

    private ArrayList<Task> viewedTasks;

    public InMemoryHistoryManager() {
        viewedTasks = new ArrayList<>();
    }

    @Override
    public void add(Task task) {
        viewedTasks.add(task);
    }

    @Override
    public ArrayList<Task> getHistory() {
        return viewedTasks;
    }

    @Override
    public void removeTaskFromHistoryById(Integer taskId) {
        viewedTasks.removeIf(task -> taskId.equals(task.getId()));
    }

    @Override
    public void clearHistory() {
        viewedTasks.clear();
    }
}
