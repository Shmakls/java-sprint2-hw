package ru.andrianov;

import ru.andrianov.data.FileBackedTasksRepository;
import ru.andrianov.data.InMemoryTaskRepository;
import ru.andrianov.data.TaskRepositoryStorageService;
import ru.andrianov.hmdata.FileBackedHistoryManager;
import ru.andrianov.hmdata.HistoryManager;
import ru.andrianov.hmdata.HistoryManagerStorageService;
import ru.andrianov.data.TaskRepository;
import ru.andrianov.hmdata.InMemoryHistoryManager;

public class Managers {

    public Managers() {
    }

    // Конфигурация для подключения файловых репозиториев
    /* private static TaskRepository getRepository() {
        FileBackedTasksRepository fileBackedTasksRepository = new FileBackedTasksRepository("src\\ru\\andrianov\\TaskRepository.csv");
        TaskRepositoryStorageService.restore(fileBackedTasksRepository);
        return fileBackedTasksRepository;
    }

    private static HistoryManager getHistoryManager() {
        FileBackedHistoryManager fileBackedHistoryManager = new FileBackedHistoryManager("src\\ru\\andrianov\\HistoryRepository.csv");
        HistoryManagerStorageService.restore(fileBackedHistoryManager);
        return fileBackedHistoryManager;
    } */

    // Конфигурация для подключения InMemory репозиториев
    private static TaskRepository getRepository() {
        return new InMemoryTaskRepository();
    }

    private static HistoryManager getHistoryManager() {
        return new InMemoryHistoryManager();
    }

    public static TaskManager getTaskManager() {
        return new TaskManager(getRepository(), getHistoryManager());
    }
}
