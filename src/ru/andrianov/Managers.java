package ru.andrianov;

import ru.andrianov.data.HistoryManager;
import ru.andrianov.data.InMemoryHistoryManager;
import ru.andrianov.data.InMemoryTaskRepository;
import ru.andrianov.data.TaskRepository;

public class Managers {

    public Managers() {
    }

    /*
    Я изначально понял что надо убрать зависимость, но не понимал как в Managers передать информацию о репозитории и
    истории. Сделал поля хранилищ статичными и передал их в конструктор. А вот с TaskManager так и не понял, где он должен
    быть, как инициализироватся. Сделал как-то так, если честно, не понимаю как это реализовать. Можно мне прям подсказку
    для дурака или на примере уже готового моего задания объяснить((.
     */

    public static TaskRepository getRepository() {
        return new InMemoryTaskRepository();
    }

    public static HistoryManager getHistoryManager() {
        return new InMemoryHistoryManager();
    }

    public static TaskManager getTaskManager() {
        return new TaskManager(getRepository(), getHistoryManager());
    }

}
