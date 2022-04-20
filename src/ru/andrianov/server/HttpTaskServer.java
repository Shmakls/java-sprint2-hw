package ru.andrianov.server;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import ru.andrianov.TaskManager;
import ru.andrianov.data.Task;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;

public class HttpTaskServer {

    private static final int PORT = 8080;
    private final TaskManager taskManager;
    private final HttpServer httpServer;
    private Gson gson = new Gson();

    public HttpTaskServer(TaskManager taskManager) throws IOException {
        this.taskManager = taskManager;
        httpServer = HttpServer.create(new InetSocketAddress(PORT), 0);
        httpServer.createContext("/tasks/task", new TaskHandler(gson, taskManager));
        httpServer.createContext("/tasks/subtasksListByEpic", (h) -> {
            try {
                switch (h.getRequestMethod()) {
                    case "GET":
                        String getId = h.getRequestURI().getPath().substring("/subtasksListByEpic/".length());
                        if (getId.isEmpty()) {
                            System.out.println("EpicId пустой. EpicId указывается в пути: /subtasksListByEpic/{key}");
                            h.sendResponseHeaders(400, 0);
                            return;
                        }
                        List<Integer> subtasksId = taskManager.getSubtaskListByEpic(Integer.parseInt(getId));
                        String jsonList = gson.toJson(subtasksId);
                        sendText(h, jsonList);
                        break;
                    default:
                        System.out.println("/subtasksListByEpic ждёт GET-запрос, а получил " + h.getRequestMethod());
                        h.sendResponseHeaders(405, 0);
                }
            } finally {
                h.close();
            }
        });
        httpServer.createContext("/tasks/history", (h) -> {
           try {
               switch (h.getRequestMethod()) {
                   case "GET":
                       sendText(h, gson.toJson(taskManager.getHistory()));
                       break;
                   default:
                       System.out.println("/history ждёт GET-запрос, а получил " + h.getRequestMethod());
                       h.sendResponseHeaders(405, 0);
               }
           } finally {
               h.close();
           }
        });
        httpServer.createContext("/tasks/prioritizedTask", (h) -> {
            try {
                switch (h.getRequestMethod()) {
                    case "GET":
                        sendText(h, gson.toJson(taskManager.getPrioritizedTasks()));
                        break;
                    default:
                        System.out.println("/prioritizedTask ждёт GET-запрос, а получил " + h.getRequestMethod());
                        h.sendResponseHeaders(405, 0);
                }
            } finally {
                h.close();
            }
        });
    }

    public void start() {
        System.out.println("Запускаем HttpTask Сервер на порту " + PORT);
        System.out.println("Открой в браузере http://localhost:" + PORT + "/");
        httpServer.start();
    }

    public void stop() {
        System.out.println("Останавливаем сервер на порту " + PORT);
        httpServer.stop(1);
    }

    private static String readText(HttpExchange h) throws IOException {
        return new String(h.getRequestBody().readAllBytes(), "UTF-8");
    }

    private static void sendText(HttpExchange h, String text) throws IOException {
        byte[] resp = text.getBytes("UTF-8");
        h.getResponseHeaders().add("Content-Type", "application/json");
        h.sendResponseHeaders(200, resp.length);
        h.getResponseBody().write(resp);
    }

    static class TaskHandler implements HttpHandler {

        private Gson gson;
        private TaskManager taskManager;

        public TaskHandler(Gson gson, TaskManager taskManager) {
            this.gson = gson;
            this.taskManager = taskManager;
        }

        @Override
        public void handle(HttpExchange h) throws IOException {
        try {
            switch (h.getRequestMethod()) {
                case "GET":
                    String getId = h.getRequestURI().getPath().substring("/tasks/task/".length());

                    if (getId.isEmpty()) {
                        sendText(h, gson.toJson(taskManager.getAllTasks()));
                    } else {
                        try {
                            sendText(h, gson.toJson(taskManager.getTaskById(Integer.parseInt(getId))));
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                            h.sendResponseHeaders(400, 0);
                            return;
                        }
                    }
                    break;

                case "POST":
                    String jsonNewTask = readText(h);
                    if (jsonNewTask.isEmpty()) {
                        System.out.println("jsonNewTask для сохранения пустой. jsonNewTask указывается в теле запроса");
                        h.sendResponseHeaders(400, 0);
                        return;
                    }
                    Task newTask = gson.fromJson(jsonNewTask, Task.class);
                    Integer newTaskId = taskManager.createNewTask(newTask);
                    System.out.println("Задача с ID " + newTaskId + " успешно создана!");
                    h.sendResponseHeaders(200, 0);
                    break;

                case "PUT":
                    String jsonUpdateTask = readText(h);
                    if (jsonUpdateTask.isEmpty()) {
                        System.out.println("jsonNewTask для обновления пустой. jsonNewTask указывается в теле запроса");
                        h.sendResponseHeaders(400, 0);
                        return;
                    }
                    Task updateTask = gson.fromJson(jsonUpdateTask, Task.class);
                    taskManager.updateTask(updateTask, updateTask.getId());
                    System.out.println("Задача успешно обновлена!");
                    h.sendResponseHeaders(200, 0);
                    break;

                case "DELETE":
                    String deleteId = h.getRequestURI().getPath().substring("/task/".length());

                    if (deleteId.isEmpty()) {
                        taskManager.clearAllTasks();
                        sendText(h, gson.toJson("Все задачи удалены!"));
                    } else {
                        taskManager.removeTaskById(Integer.parseInt(deleteId));
                        sendText(h, gson.toJson("Задача с ID " + Integer.parseInt(deleteId)) + " удалена!");
                    }
                    break;

                default:
                    System.out.println("/tasks/task ждёт GET, PUT, DELETE или POST-запрос, а получил " + h.getRequestMethod());
                    h.sendResponseHeaders(405, 0);

            }

        } finally {
            h.close();
        }

        }



    }



}
