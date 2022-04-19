package ru.andrianov;

import ru.andrianov.data.*;
import ru.andrianov.hmdata.*;

public class Managers {

    public Managers() {
    }

    // Конфигурация для подключения Http-репозиториев
    private static TaskRepository getTaskRepository() {
        return new HttpTaskRepository("http://localhost:8080/");
    }

    private static HistoryRepository getHistoryRepository() {
        return new HttpHistoryRepository("http://localhost:8080/");
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
    /* private static TaskRepository getTaskRepository() {
        return new InMemoryTaskRepository();
    }

    private static HistoryRepository getHistoryRepository() {
        return new InMemoryHistoryRepository();
    } */

    public static TaskManager getTaskManager() {
        return new TaskManager(getTaskRepository(), getHistoryRepository());
    }

}
