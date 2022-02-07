package ru.andrianov;

public class Managers<T extends TaskManager> {

    private final T taskManager;

    public Managers(T taskManager) {
        this.taskManager = taskManager;
    }

    TaskManager getDefault() {
        return taskManager;
    }

}
