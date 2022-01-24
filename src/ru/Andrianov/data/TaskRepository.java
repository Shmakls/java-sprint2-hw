package ru.Andrianov.data;

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
        idCounter++;
        tasks.put(idCounter, task);
        return idCounter;
    }

    public void clearAllTasks() {
        tasks.clear();
    }

    public Task getTaskByID(Integer taskID) {
            return tasks.get(taskID);
    }

    public void removeTaskByID(Integer taskID) {
        tasks.remove(taskID);
    }

    public void updateTask(Task task, Integer taskID) {
        tasks.put(taskID, task);
    }

}
