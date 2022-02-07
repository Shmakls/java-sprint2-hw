package ru.andrianov.data;

public interface TaskRepository {

    Integer createNewTask(Task task);

    void clearAllTasks();

    Task getTaskById(Integer taskId);

    void removeTaskById(Integer taskId);

    void updateTask(Task task, Integer taskId);

}
