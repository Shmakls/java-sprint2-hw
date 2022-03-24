package ru.andrianov.data;

import java.util.HashMap;
import java.util.Map;

public class FileBackedTasksRepository implements TaskRepository {

    String filePath;
    Map<Integer, Task> tasks;
    Integer idCounter;

    public FileBackedTasksRepository(String filePath) {
        tasks = new HashMap<>();
        idCounter = 0;
        this.filePath = filePath;
    }

    @Override
    public Map<Integer, Task> getTasks() {
        return tasks;
    }

    @Override
    public Integer createNewTask(Task task) {
        task.setId(++idCounter);
        task.setType(TaskRepositoryStorageService.findTypeTask(task));
        tasks.put(task.getId(), task);
        TaskRepositoryStorageService.save(this);
        return idCounter;
    }

    @Override
    public void clearAllTasks() {
        tasks.clear();
        TaskRepositoryStorageService.save(this);
    }

    @Override
    public Task getTaskById(Integer taskId) {
        return tasks.get(taskId);
    }

    @Override
    public void removeTaskById(Integer taskId) {
        tasks.remove(taskId);
        TaskRepositoryStorageService.save(this);
    }

    @Override
    public void updateTask(Task task, Integer taskId) {
        tasks.put(taskId, task);
        TaskRepositoryStorageService.save(this);
    }

}
