package ru.andrianov;

import ru.andrianov.data.Task;

import java.util.List;

interface TaskManager {


    void createNewTask(Task task);

    void printAllTasks();

    void clearAllTasks();

    Task getTaskById(int taskId);

    void removeTaskById(int taskId);

    void updateTask(Task task, int taskId);

    void getSubtaskListByEpic(int epicId);


}
