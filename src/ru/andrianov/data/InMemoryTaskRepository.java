package ru.andrianov.data;


import java.util.HashMap;


public class InMemoryTaskRepository implements TaskRepository {

    private HashMap<Integer, Task> tasks;                       //И здесь тоже самое
    Integer idCounter;

    public InMemoryTaskRepository() {
        tasks = new HashMap<>();
        idCounter = 0;
    }

    @Override
    public HashMap<Integer, Task> getTasks() {
        return tasks;
    }

    @Override
    public Integer createNewTask(Task task) {
        task.setId(++idCounter);
        tasks.put(task.getId(), task);
        return task.getId();
    }

    @Override
    public void clearAllTasks() {
        tasks.clear();
    }

    @Override
    public Task getTaskById(Integer taskId) {
        return tasks.get(taskId);
    }

    @Override
    public void removeTaskById(Integer taskId) {
        tasks.remove(taskId);
    }

    @Override
    public void updateTask(Task task, Integer taskId) {
        tasks.put(taskId, task);
    }

}
