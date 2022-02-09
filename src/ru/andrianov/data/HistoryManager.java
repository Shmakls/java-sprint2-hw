package ru.andrianov.data;



import java.util.ArrayList;


public interface HistoryManager {

    void add(Task task);

    ArrayList<Task> getHistory();

    void removeTaskFromHistoryById(Integer taskId);

    void clearHistory();

}
