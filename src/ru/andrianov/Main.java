package ru.andrianov;


import ru.andrianov.data.*;
import ru.andrianov.server.HttpTaskServer;
import ru.andrianov.server.KVServer;
import ru.andrianov.server.KVTaskClient;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Main {

    public static void main(String[] args) throws IOException {

        new KVServer().start();

        TaskManager taskManager = Managers.getTaskManager();

        new HttpTaskServer(taskManager).start();
    }
}
