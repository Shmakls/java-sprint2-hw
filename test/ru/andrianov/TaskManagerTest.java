package ru.andrianov;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.andrianov.data.Status;
import ru.andrianov.data.Task;

import static org.junit.jupiter.api.Assertions.*;

class TaskManagerTest {

    TaskManager taskManager;

    @BeforeEach
    void createNewTaskManager() {
        taskManager = Managers.getTaskManager();
    }

    @Test
    void shouldBeReturnNullIfTuskNull() {
        Integer taskId = taskManager.createNewTask(null);
        assertNull(taskId);
    }

    @Test
    void shouldBeAddTaskEpicSubtask() {
        Task task = new Task("TestTask1", "DescriptionTestTask1", Status.NEW);
        Integer taskId = taskManager.createNewTask(task);
        Task taskFromRepository = taskManager.getTaskById(taskId);

        assertNotNull(taskId, "Задача не сохранилась.");
        assertEquals(task, taskFromRepository, "Задачи не совпадают.");



    }

    @Test
    void printAllTasks() {
    }

    @Test
    void clearAllTasks() {
    }

    @Test
    void getTaskById() {
    }

    @Test
    void removeTaskById() {
    }

    @Test
    void updateTask() {
    }

    @Test
    void getSubtaskListByEpic() {
    }

    @Test
    void printHistory() {
    }
}