package ru.andrianov.hmdata;

import ru.andrianov.data.Task;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class FileBackedHistoryRepository implements HistoryRepository {

    String filePath;
    HomeLinkedList viewedTasks;
    Map<Integer, Node> nodeWithId;

    public FileBackedHistoryRepository(String filePath) {
        this.filePath = filePath;
        viewedTasks = new HomeLinkedList();
        nodeWithId = new HashMap<>();
    }

    @Override
    public void add(Task task) {
        Integer taskId = task.getId();
        if (nodeWithId.containsKey(taskId)) {
            Node node = nodeWithId.get(taskId);
            viewedTasks.removeNode(node);
            viewedTasks.linkLast(task);
            nodeWithId.remove(taskId);
        } else {
            Node node = viewedTasks.linkLast(task);
            nodeWithId.put(taskId, node);
        }
        HistoryRepositoryStorageService.save(this);
    }

    @Override
    public Collection<Task> getHistory() {
        return viewedTasks.getTasks();
    }

    @Override
    public void removeTaskFromHistoryById(Integer taskId) {
        if (nodeWithId.containsKey(taskId)) {
            Node node = nodeWithId.get(taskId);
            viewedTasks.removeNode(node);
            nodeWithId.remove(taskId);
        }
        HistoryRepositoryStorageService.save(this);
    }

    @Override
    public void clear() {
        nodeWithId.clear();
        viewedTasks.clear();
        HistoryRepositoryStorageService.save(this);
    }
}
