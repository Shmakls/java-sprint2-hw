package ru.andrianov;

import ru.andrianov.data.FileBackedTasksRepository;
import ru.andrianov.data.TaskManagerStorageService;
import ru.andrianov.hmdata.HistoryManager;
import ru.andrianov.hmdata.InMemoryHistoryManager;
import ru.andrianov.data.InMemoryTaskRepository;
import ru.andrianov.data.TaskRepository;

public class Managers {

    public Managers() {
    }

    public static TaskRepository getRepository() {
        FileBackedTasksRepository fbts = new FileBackedTasksRepository("");
        TaskManagerStorageService.restore(fbts);
        return fbts;
    }

    public static HistoryManager getHistoryManager() {
        return new InMemoryHistoryManager();
    }

    public static TaskManager getTaskManager() {
        return new TaskManager(getRepository(), getHistoryManager());
    }

}
