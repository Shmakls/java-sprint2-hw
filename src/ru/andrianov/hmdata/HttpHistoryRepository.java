package ru.andrianov.hmdata;

import ru.andrianov.data.Task;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class HttpHistoryRepository implements HistoryRepository {

    private final String url;
    private String key;
    private HomeLinkedList viewedTasks;
    private Map<Integer, Node> nodeWithId;

    public HttpHistoryRepository(String url, String key) {
        this.url = url;
        this.key = key;
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
        return null;
    }

    @Override
    public void removeTaskFromHistoryById(Integer taskId) {

    }

    @Override
    public void clear() {

    }
}
