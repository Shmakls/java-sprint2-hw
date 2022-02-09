package ru.andrianov;

import ru.andrianov.data.HistoryManager;
import ru.andrianov.data.InMemoryHistoryManager;
import ru.andrianov.data.InMemoryTaskRepository;
import ru.andrianov.data.TaskRepository;

public class Managers {                                 //Не совсем понимаю как правильно реализовать этот класс,
                                                        //сделал как просят в ТЗ, подскажите если что, пожалуйста.
    private TaskRepository taskRepository;
    private HistoryManager historyManager;

    public Managers() {
        taskRepository = new InMemoryTaskRepository();
        historyManager = new InMemoryHistoryManager();
    }

    TaskRepository getDefault() {
        return taskRepository;
    }

    HistoryManager getDefaultHistory() {
        return historyManager;
    }

}
