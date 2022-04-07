package ru.andrianov;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.andrianov.data.Epic;
import ru.andrianov.data.Status;
import ru.andrianov.data.Subtask;
import ru.andrianov.data.Task;

import static org.junit.jupiter.api.Assertions.*;

class TaskManagerTest {

    TaskManager taskManager;
    Task task1;
    Task task2;
    Task epic1;
    Task subtask1;
    Task subtask2;

    @BeforeEach
    void createNewTaskManager() {
        taskManager = Managers.getTaskManager();
        task1 = new Task("TestTask1", "DescriptionTestTask1", Status.NEW);
        task2 = new Task("TestTask2", "DescriptionTestTask2", Status.IN_PROGRESS);
        epic1 = new Epic("TestEpic1", "DescriptionTestEpic1", Status.NEW);
        subtask1 = new Subtask("TestSubtask1", "DescriptionTestSubtask1", Status.NEW, 3);
        subtask2 = new Subtask("TestSubtask2", "DescriptionTestSubtask2", Status.IN_PROGRESS, 3);
    }

    @Test
    void shouldBeReturnNullIfTuskNull() {
        Integer taskId = taskManager.createNewTask(null);
        assertNull(taskId);
    }

    @Test
    void shouldBeAddTaskInRepository() {
        Integer task1Id = taskManager.createNewTask(task1);
        Integer task2Id = taskManager.createNewTask(task2);
        Integer epicId = taskManager.createNewTask(epic1);
        Integer subtask1Id = taskManager.createNewTask(subtask1);

        Task taskFromRepository = taskManager.getTaskById(task1Id);
        Task epicFromRepository = taskManager.getTaskById(epicId);
        Task subtaskFromRepository = taskManager.getTaskById(subtask1Id);

        assertNotNull(task1Id, "Задача не сохранилась.");
        assertEquals(task1, taskFromRepository, "Задачи не совпадают.");
        assertEquals(epic1, epicFromRepository);
        assertEquals(subtask1, subtaskFromRepository);
    }

    @Test
    void shouldBeCreateConnectionBetweenEpicAndSubtask() {
        taskManager.createNewTask(task1);
        taskManager.createNewTask(task2);
        Integer epic1Id = taskManager.createNewTask(epic1);
        Integer subtask1Id = taskManager.createNewTask(subtask1);
        Integer subtask2Id = taskManager.createNewTask(subtask2);

        Epic testEpic1 = (Epic) taskManager.getTaskById(3);
        Subtask testSubtask1 = (Subtask) taskManager.getTaskById(4);

        Integer epicIdFromSubtask1 = testSubtask1.getEpicTaskId();

        Integer testSubtask1IdFromEpic = testEpic1.getSubtasksIds().get(0);

        assertEquals(3, epicIdFromSubtask1, "Связь между эпик задачей и подзадачей не установлена.");
        assertEquals(4, testSubtask1IdFromEpic, "В эпик не записалась связь с подзадачей.");
    }

}