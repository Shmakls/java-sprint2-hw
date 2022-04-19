package ru.andrianov.hmdata;

import com.google.gson.Gson;
import ru.andrianov.data.Task;
import ru.andrianov.server.KVTaskClient;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class HttpHistoryRepository implements HistoryRepository {

    private final String url;
    private String API_KEY;
    private HomeLinkedList viewedTasks;
    private Map<Integer, Node> nodeWithId;
    private KVTaskClient kvTaskClient;
    Gson gson;

    public HttpHistoryRepository(String url) {
        this.url = url;
        viewedTasks = new HomeLinkedList();
        nodeWithId = new HashMap<>();
        kvTaskClient = new KVTaskClient(this.url);
        gson = new Gson();
    }

    public String getAPI_KEY() {
        return API_KEY;
    }

    public void setAPI_KEY(String API_KEY) {
        this.API_KEY = API_KEY;
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
        String json = gson.toJson(viewedTasks.getTasks());
        kvTaskClient.put("history", json);
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
        String json = gson.toJson(viewedTasks.getTasks());
        kvTaskClient.put("history", json);
    }

    @Override
    public void clear() {
        nodeWithId.clear();
        viewedTasks.clear();
        String json = gson.toJson(viewedTasks.getTasks());
        kvTaskClient.put("history", json);
    }
}
