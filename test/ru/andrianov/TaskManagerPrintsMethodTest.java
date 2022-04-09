package ru.andrianov;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.andrianov.data.Epic;
import ru.andrianov.data.Status;
import ru.andrianov.data.Task;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class TaskManagerPrintsMethodTest {

    private ByteArrayOutputStream output = new ByteArrayOutputStream();
    private TaskManager taskManager;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(output));
        taskManager = Managers.getTaskManager();
    }

    @Test
    public void testPrintAllTasksMethodWithTask() {
        taskManager.createNewTask(
                new Task("TestTask1", "DescriptionTestTask1", Status.NEW));
        taskManager.printAllTasks();
        Assertions.assertEquals("\r\n"
                + "Вывожу список всех задач:"
                + "\r\n"
                + "ID: 1, название: TestTask1, описание: DescriptionTestTask1, статус: NEW.\r\n"
                + "\r\n", output.toString());
    }

    @Test
    public void testPrintAllTasksMethodWithoutTasks() {
        taskManager.printAllTasks();
        Assertions.assertEquals("\r\n"
                + "Список задач пуст.\r\n"
                + "\r\n", output.toString());
    }

    @Test
    public void testPrintHistoryWithTasks() {
        taskManager.createNewTask(
                new Task("TestTask1", "DescriptionTestTask1", Status.NEW));
        taskManager.createNewTask(
                new Epic("TestTask1", "DescriptionTestTask1", Status.NEW));
        taskManager.getTaskById(1);
        taskManager.getTaskById(2);

        taskManager.printHistory();

        Assertions.assertEquals("Вывожу историю просмотренных задач:\r\n"
                        + "ID: 1, название: TestTask1, описание: DescriptionTestTask1, статус: NEW.\r\n"
                        + "ID: 2, название: TestTask1, описание: DescriptionTestTask1, статус: NEW.Зависимые подзадачи с ID: [].\r\n"
                        + "\r\n", output.toString());
    }

    @Test
    public void testPrintHistoryWithoutTasks() {
        taskManager.printHistory();
        Assertions.assertEquals("Вывожу историю просмотренных задач:\r\n"
                            + "История просмотров пуста\r\n", output.toString());
    }

    @AfterEach
    public void cleanUpStreams() {
        System.setOut(null);
    }
}
