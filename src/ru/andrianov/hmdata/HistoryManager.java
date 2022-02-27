package ru.andrianov.hmdata;

import ru.andrianov.data.Task;
import java.util.Collection;


public interface HistoryManager {

    void add(Task task);

    Collection<Task> getHistory();

    void removeTaskFromHistoryById(Integer taskId);

    void clear();

}
