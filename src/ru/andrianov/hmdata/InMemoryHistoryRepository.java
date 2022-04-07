package ru.andrianov.hmdata;

import ru.andrianov.data.Task;

import java.util.*;

public class InMemoryHistoryRepository implements HistoryRepository {

    private HomeLinkedList viewedTasks;
    private Map<Integer, Node> nodeWithId;


    public InMemoryHistoryRepository() {
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

    }

    @Override
    public Collection<Task> getHistory() {
        return viewedTasks.getTasks();
    }

    @Override
    public void clear() {
        nodeWithId.clear();
        viewedTasks.clear();
    }

    @Override
    public void removeTaskFromHistoryById(Integer taskId) {
        if (nodeWithId.containsKey(taskId)) {
            Node node = nodeWithId.get(taskId);
            viewedTasks.removeNode(node);
            nodeWithId.remove(taskId);
        }
    }

}

