package ru.andrianov.server;

import com.google.gson.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.andrianov.Managers;
import ru.andrianov.TaskManager;
import ru.andrianov.common.MyGsonBuilder;
import ru.andrianov.data.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;

class HttpTaskServerTest {

    HttpTaskServer httpTaskServer;
    KVServer kvServer;
    HttpClient client;
    HttpResponse.BodyHandler<String> handler;
    TaskManager taskManager;
    Task task1;
    Task task2;
    Task epic1;
    Task subtask1;
    Task subtask2;

    Gson gson;

    ZoneId zoneId = ZoneId.of("Europe/Moscow");
    Duration estimationTime = Duration.ofMinutes(15);
    LocalDateTime localDateTime = LocalDateTime.of(2022, 1, 15, 12, 0);
    ZonedDateTime startTimeTask1 = ZonedDateTime.of(localDateTime, zoneId);
    ZonedDateTime startTimeTask2 = ZonedDateTime.of(localDateTime.plusMinutes(30), zoneId);
    ZonedDateTime startTimeSubtask1 = ZonedDateTime.of(localDateTime.plusMinutes(60), zoneId);
    ZonedDateTime startTimeSubtask2 = ZonedDateTime.of(localDateTime.plusMinutes(90), zoneId);

    @BeforeEach
    void beforeEach() {
        try {
            kvServer = new KVServer();
            kvServer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        gson = MyGsonBuilder.build();

        taskManager = Managers.getTaskManager();
        taskManager.clearAllTasks();
        task1 = new Task("TestTask1",
                "DescriptionTestTask1",
                Status.NEW, startTimeTask1, estimationTime);
        task2 = new Task("TestTask2",
                "DescriptionTestTask2",
                Status.IN_PROGRESS, startTimeTask2, estimationTime);
        epic1 = new Epic("TestEpic1", "DescriptionTestEpic1", Status.NEW);
        subtask1 = new Subtask("TestSubtask1",
                "DescriptionTestSubtask1",
                Status.NEW, startTimeSubtask1, estimationTime, 3);
        subtask2 = new Subtask("TestSubtask2",
                "DescriptionTestSubtask2",
                Status.IN_PROGRESS, startTimeSubtask2, estimationTime, 3);

        try {
            httpTaskServer = new HttpTaskServer(taskManager);
            httpTaskServer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        client = HttpClient.newHttpClient();
        handler = HttpResponse.BodyHandlers.ofString();
    }

    @AfterEach
    void afterEach() {
        kvServer.stop();
        httpTaskServer.stop();
    }

    private void addFiveTestTasksToTaskManager() {
        taskManager.createNewTask(task1);
        taskManager.createNewTask(task2);
        taskManager.createNewTask(epic1);
        taskManager.createNewTask(subtask1);
        taskManager.createNewTask(subtask2);
    }

    @Test // test endpoint GET tasks/task
    void shouldBeReturnAllTasks() {
        String response = "";
        addFiveTestTasksToTaskManager();

        String testJson = gson.toJson(taskManager.getAllTasks());

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://localhost:8080/tasks/task"))
                .build();

        try {
            HttpResponse<String> httpResponse = client.send(httpRequest, handler);
            response = httpResponse.body();
            System.out.println("Список задач получен!");

        } catch (IOException e) {
            System.out.println("Ошибка IOException");
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.out.println("Ошибка InterruptedException");
            e.printStackTrace();
        }

        assertEquals(testJson, response, "Задачи не совпадают!");

    }

    @Test // test endpoint GET tasks/task/{id}
    void shouldBeReturnTaskById() {
        String response = "";
        addFiveTestTasksToTaskManager();

        String testJson = gson.toJson(taskManager.getTaskById(2));

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://localhost:8080/tasks/task/2"))
                .build();

        try {
            HttpResponse<String> httpResponse = client.send(httpRequest, handler);
            response = httpResponse.body();
            System.out.println("Задача получена!");

        } catch (IOException e) {
            System.out.println("Ошибка IOException");
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.out.println("Ошибка InterruptedException");
            e.printStackTrace();
        }

        assertEquals(testJson, response, "Задачи не совпадают!");
    }

    @Test // test endpoint GET tasks/history
    void shouldBeReturnHistory() {
        String response = "";
        addFiveTestTasksToTaskManager();

        Task testTask1 = taskManager.getTaskById(1);
        Task testTask4 = taskManager.getTaskById(4);

        String testJson = gson.toJson(taskManager.getHistory());

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://localhost:8080/tasks/history"))
                .build();

        try {
            HttpResponse<String> httpResponse = client.send(httpRequest, handler);
            response = httpResponse.body();
            System.out.println("История получена!");

        } catch (IOException e) {
            System.out.println("Ошибка IOException");
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.out.println("Ошибка InterruptedException");
            e.printStackTrace();
        }

        assertEquals(testJson, response, "Истории не совпадают!");
    }

    @Test // test endpoint GET tasks/prioritizedTask
    void shouldBeReturnPrioritizedTask() {
        String response = "";
        addFiveTestTasksToTaskManager();

        String testJson = gson.toJson(taskManager.getPrioritizedTasks());

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://localhost:8080/tasks/prioritizedTask"))
                .build();

        try {
            HttpResponse<String> httpResponse = client.send(httpRequest, handler);
            response = httpResponse.body();
            System.out.println("Приоритетные задачи получены");

        } catch (IOException e) {
            System.out.println("Ошибка IOException");
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.out.println("Ошибка InterruptedException");
            e.printStackTrace();
        }

        assertEquals(testJson, response, "Приоритетные списки не совпадают");
    }

    @Test // test endpoint GET tasks/subtasksListByEpic/{id}
    void shouldBeReturnSubtasksListIdByEpicId() {
        String response = "";
        addFiveTestTasksToTaskManager();

        String testJson = gson.toJson(taskManager.getSubtaskListByEpic(3));

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://localhost:8080/tasks/subtasksListByEpic/3"))
                .build();

        try {
            HttpResponse<String> httpResponse = client.send(httpRequest, handler);
            response = httpResponse.body();
            System.out.println("Список подзадач получен");

        } catch (IOException e) {
            System.out.println("Ошибка IOException");
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.out.println("Ошибка InterruptedException");
            e.printStackTrace();
        }

        assertEquals(testJson, response, "Списки подзадач не совпадают");
    }

    @Test // test endpoint POST /tasks/task Body{task}
    void shouldBeCreateNewTask() {

        String jsonTask1 = gson.toJson(task1);

        task1.setId(1);
        task1.setType(ru.andrianov.data.Type.TASK);

        String expectedJson = gson.toJson(task1);

        HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(jsonTask1);

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/tasks/task"))
                .POST(body)
                .version(HttpClient.Version.HTTP_1_1)
                .header("Content-Type", "application/json")
                .header("Accept", "text/json")
                .build();

        try {
            HttpResponse<String> response = client.send(httpRequest, handler);

        } catch (IOException e) {
            System.out.println("Ошибка IOException");
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.out.println("Ошибка InterruptedException");
            e.printStackTrace();
        }

        String actualJson = gson.toJson(taskManager.getTaskById(1));

        assertEquals(expectedJson, actualJson, "Задачи не совпадают!");
    }

    @Test // test endpoint PUT /tasks/task Body{task}
    void shouldBeUpdateTask() {
        addFiveTestTasksToTaskManager();

        Task updateSubtask1 = new Subtask("updateSubtask1",
                "UpdateDescriptionSubtask1",
                Status.DONE,
                startTimeSubtask1.plusMinutes(90),
                estimationTime, 3);
        updateSubtask1.setId(4);
        updateSubtask1.setType(Type.SUBTASK);

        String expectedJson = gson.toJson(updateSubtask1);

        HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(expectedJson);

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/tasks/task"))
                .PUT(body)
                .version(HttpClient.Version.HTTP_1_1)
                .header("Content-Type", "application/json")
                .header("Accept", "text/json")
                .build();

        try {
            HttpResponse<String> response = client.send(httpRequest, handler);

        } catch (IOException e) {
            System.out.println("Ошибка IOException");
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.out.println("Ошибка InterruptedException");
            e.printStackTrace();
        }

        String actualJson = gson.toJson(taskManager.getTaskById(4));

        assertEquals(expectedJson, actualJson, "Задачи не совпадают!");

    }

    @Test // test endpoint DELETE /tasks/task
    void shouldBeDeleteAllTasks() {
        addFiveTestTasksToTaskManager();

        Integer amountTasks = taskManager.getAmountOfStoredTasks();

        assertEquals(5, amountTasks, "Количество задач не совпадает!");

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .DELETE()
                .uri(URI.create("http://localhost:8080/tasks/task"))
                .build();

        try {
            HttpResponse<String> httpResponse = client.send(httpRequest, handler);

        } catch (IOException e) {
            System.out.println("Ошибка IOException");
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.out.println("Ошибка InterruptedException");
            e.printStackTrace();
        }

        Integer actualAmountTask = taskManager.getAmountOfStoredTasks();

        assertEquals(0, actualAmountTask, "Задачи не удалились!");

    }

    @Test // test endpoint DELETE /tasks/task/{id}
    void shouldBeDeleteTaskById() {
        addFiveTestTasksToTaskManager();

        Integer amountTasks = taskManager.getAmountOfStoredTasks();

        assertEquals(5, amountTasks, "Количество задач не совпадает!");

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .DELETE()
                .uri(URI.create("http://localhost:8080/tasks/task/2"))
                .build();

        try {
            HttpResponse<String> httpResponse = client.send(httpRequest, handler);

        } catch (IOException e) {
            System.out.println("Ошибка IOException");
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.out.println("Ошибка InterruptedException");
            e.printStackTrace();
        }

        Integer actualAmountTask = taskManager.getAmountOfStoredTasks();

        assertEquals(4, actualAmountTask, "Количеств задач после удаления не совпадает!");
    }

}