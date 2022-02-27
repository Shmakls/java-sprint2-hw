package ru.andrianov.hmdata;



import ru.andrianov.data.Task;

import java.util.ArrayList;


public interface HistoryManager {

    void add(Task task);

    ArrayList<Task> getHistory();

    void removeTaskFromHistoryById(Integer taskId);

    void clear();

}
