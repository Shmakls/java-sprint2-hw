package ru.andrianov;

import ru.andrianov.data.FileBackedTasksRepository;
import ru.andrianov.data.TaskRepositoryStorageService;
import ru.andrianov.hmdata.FileBackedHistoryManager;
import ru.andrianov.hmdata.HistoryManager;
import ru.andrianov.hmdata.HistoryManagerStorageService;
import ru.andrianov.hmdata.InMemoryHistoryManager;
import ru.andrianov.data.TaskRepository;

public class Managers {

    public Managers() {
    }

    public static TaskRepository getRepository() {
        FileBackedTasksRepository fileBackedTasksRepository = new FileBackedTasksRepository("");
        TaskRepositoryStorageService.restore(fileBackedTasksRepository);
        return fileBackedTasksRepository;
    }

    public static HistoryManager getHistoryManager() {
        FileBackedHistoryManager fileBackedHistoryManager = new FileBackedHistoryManager("");
        HistoryManagerStorageService.restore(fileBackedHistoryManager);
        return fileBackedHistoryManager;
    }

    public static TaskManager getTaskManager() {
        return new TaskManager(getRepository(), getHistoryManager());
    }

}
