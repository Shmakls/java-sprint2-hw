package ru.andrianov;

import ru.andrianov.data.FileBackedTasksRepository;
import ru.andrianov.data.InMemoryTaskRepository;
import ru.andrianov.data.TaskRepositoryStorageService;
import ru.andrianov.hmdata.FileBackedHistoryRepository;
import ru.andrianov.hmdata.HistoryRepositoryStorageService;
import ru.andrianov.hmdata.HistoryRepository;
import ru.andrianov.data.TaskRepository;
import ru.andrianov.hmdata.InMemoryHistoryRepository;

public class Managers {

    public Managers() {
    }

    // Конфигурация для подключения файловых репозиториев
    /* private static TaskRepository getTaskRepository() {
        FileBackedTasksRepository fileBackedTasksRepository = new FileBackedTasksRepository("src\\ru\\andrianov\\TaskRepository.csv");
        TaskRepositoryStorageService.restore(fileBackedTasksRepository);
        return fileBackedTasksRepository;
    }

    private static HistoryRepository getHistoryRepository() {
        FileBackedHistoryRepository fileBackedHistoryRepository = new FileBackedHistoryRepository("src\\ru\\andrianov\\HistoryRepository.csv");
        HistoryRepositoryStorageService.restore(fileBackedHistoryRepository);
        return fileBackedHistoryRepository;
    } */



    // Конфигурация для подключения InMemory репозиториев
    private static TaskRepository getTaskRepository() {
        return new InMemoryTaskRepository();
    }

    private static HistoryRepository getHistoryRepository() {
        return new InMemoryHistoryRepository();
    }

    public static TaskManager getTaskManager() {
        return new TaskManager(getTaskRepository(), getHistoryRepository());
    }

}
