package ru.andrianov.data;

import com.google.gson.Gson;
import ru.andrianov.server.KVTaskClient;

import java.util.HashMap;
import java.util.Map;

public class HttpTaskRepository implements TaskRepository {

    private final String url;
    private String API_KEY;
    private final KVTaskClient kvTaskClient;
    private HashMap<Integer, Task> tasks;
    private Integer idCounter;
    Gson gson;

    public HttpTaskRepository(String url) {
        this.url = url;
        kvTaskClient = new KVTaskClient(this.url);
        tasks = new HashMap<>();
        idCounter = 0;
        gson = new Gson();
    }

    @Override
    public Map<Integer, Task> getTasks() {
        return tasks;
    }

    @Override
    public Integer createNewTask(Task task) {
        task.setId(++idCounter);
        task.setType(TaskRepositoryStorageService.findTypeTask(task));
        tasks.put(task.getId(), task);
        String json = gson.toJson(tasks);
        kvTaskClient.put("tasks-" + API_KEY, json);
        return idCounter;
    }

    @Override
    public void clearAllTasks() {
        tasks.clear();
        String json = gson.toJson(tasks);
        kvTaskClient.put("tasks-" + API_KEY, json);
    }

    @Override
    public Task getTaskById(Integer taskId) {
        return tasks.get(taskId);
    }

    @Override
    public void removeTaskById(Integer taskId) {
        tasks.remove(taskId);
        String json = gson.toJson(tasks);
        kvTaskClient.put("tasks-" + API_KEY, json);
    }

    @Override
    public void updateTask(Task task, Integer taskId) {
        tasks.put(taskId, task);
        String json = gson.toJson(tasks);
        kvTaskClient.put("tasks-" + API_KEY, json);
    }

    public String getAPI_KEY() {
        return API_KEY;
    }

    public void setAPI_KEY(String API_KEY) {
        this.API_KEY = API_KEY;
    }
}
