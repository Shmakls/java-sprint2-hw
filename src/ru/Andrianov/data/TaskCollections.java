package ru.Andrianov.data;

import java.util.HashMap;

public class TaskCollections {

    private HashMap<Integer, Task> tasks;
    private HashMap<Integer, Subtask> subtasks;
    private HashMap<Integer, Epic> epics;

    public TaskCollections(HashMap<Integer, Task> tasks,
                           HashMap<Integer, Subtask> subtasks,
                           HashMap<Integer, Epic> epics) {
        this.tasks = tasks;
        this.subtasks = subtasks;
        this.epics = epics;
    }

    public HashMap<Integer, Task> getTasks() {
        return tasks;
    }

    public void setTasks(HashMap<Integer, Task> tasks) {
        this.tasks = tasks;
    }

    public HashMap<Integer, Subtask> getSubtasks() {
        return subtasks;
    }

    public void setSubtasks(HashMap<Integer, Subtask> subtasks) {
        this.subtasks = subtasks;
    }

    public HashMap<Integer, Epic> getEpics() {
        return epics;
    }

    public void setEpics(HashMap<Integer, Epic> epics) {
        this.epics = epics;
    }
}
