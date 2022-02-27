package ru.andrianov.data;

import java.util.HashMap;
import java.util.Map;

public interface TaskRepository {

    Map<Integer, Task> getTasks();

    Integer createNewTask(Task task);

    void clearAllTasks();

    Task getTaskById(Integer taskId);

    void removeTaskById(Integer taskId);

    void updateTask(Task task, Integer taskId);

}
