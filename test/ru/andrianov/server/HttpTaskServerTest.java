package ru.andrianov.server;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.andrianov.Managers;
import ru.andrianov.TaskManager;
import ru.andrianov.data.Epic;
import ru.andrianov.data.Status;
import ru.andrianov.data.Subtask;
import ru.andrianov.data.Task;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;

class HttpTaskServerTest {

    HttpTaskServer httpTaskServer;
    KVServer kvServer;
    TaskManager taskManager;
    Task task1;
    Task task2;
    Task epic1;
    Task subtask1;
    Task subtask2;

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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    void afterEach() {
        kvServer.stop();
        httpTaskServer.stop();
    }



}