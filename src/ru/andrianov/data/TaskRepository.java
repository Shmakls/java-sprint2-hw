package ru.andrianov.data;

import java.util.HashMap;

public interface TaskRepository {

    HashMap<Integer, Task> getTasks();

    Integer createNewTask(Task task);

    void clearAllTasks();

    Task getTaskById(Integer taskId);

    void removeTaskById(Integer taskId);

    void updateTask(Task task, Integer taskId);

}
