package ru.andrianov.data;

import java.util.HashMap;

public class TaskRepository {

    private HashMap<Integer, Task> tasks;
    Integer idCounter;

    public TaskRepository() {
        tasks = new HashMap<>();
        idCounter = 0;
    }

    public HashMap<Integer, Task> getTasks() {
        return tasks;
    }

    public void setTasks(HashMap<Integer, Task> tasks) {
        this.tasks = tasks;
    }

    public Integer createNewTask(Task task) {
        task.setId(++idCounter);
        tasks.put(task.getId(), task);
        return task.getId();
    }

    public void clearAllTasks() {
        tasks.clear();
    }

    public Task getTaskById(Integer taskId) {
            return tasks.get(taskId);
    }

    public void removeTaskById(Integer taskId) {
        tasks.remove(taskId);
    }

    public void updateTask(Task task, Integer taskId) {
        tasks.put(taskId, task);
    }

}
