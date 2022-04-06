package ru.andrianov.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

abstract class TaskRepositoryTest<T extends TaskRepository> {
    T taskRepository;
    Task task1 = new Task("TestTask1", "DescriptionTestTask1", Status.NEW);
    Task task2 = new Task("TestTask1", "DescriptionTestTask2", Status.IN_PROGRESS);

    public TaskRepositoryTest(T taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Test
    void shouldBeCreatedNewTaskInRepository() {
        Integer taskId = taskRepository.createNewTask(task1);
        Task testTask = taskRepository.getTaskById(taskId);

        assertEquals(testTask, task1, "Задачи не совпадают.");

        Map<Integer, Task> tasks = taskRepository.getTasks();

        assertEquals(1, tasks.size(), "Задача не добавилась.");
    }

    @Test
    void shouldBeReturnTasksStorage() {
        taskRepository.createNewTask(task1);
        Map<Integer, Task> tasks = taskRepository.getTasks();

        assertNotNull(tasks, "Отсутствует хранение данных.");
    }

    @Test
    void shouldBeClearAllTasks() {
        taskRepository.createNewTask(task1);

        taskRepository.clearAllTasks();

        Map<Integer, Task> tasks = taskRepository.getTasks();

        assertEquals(0, tasks.size(), "История не очищена.");

    }

    @Test
    void ShouldBeGetTaskById() {
        Integer taskID = taskRepository.createNewTask(task1);
        Task testTask = taskRepository.getTaskById(taskID);

        assertEquals(testTask, task1, "Задачи не совпадают.");
    }

    @Test
    void shouldBeRemoveTaskById() {
        Integer taskId1 = taskRepository.createNewTask(task1);
        Integer taskId2 = taskRepository.createNewTask(task2);

        taskRepository.removeTaskById(taskId2);
        Map<Integer, Task> tasks = taskRepository.getTasks();

        assertFalse(tasks.containsKey(taskId2), "Задача не удалилась.");
    }

    @Test
    void shouldBeUpdateTask() {
        Integer taskId1 = taskRepository.createNewTask(task1);
        Integer taskId2 = taskRepository.createNewTask(task2);

        Task newTask2 = new Task("UpdatedTestTask2", "UpdatesDescriptionTestTask2", Status.NEW);

        taskRepository.updateTask(newTask2, taskId2);

        assertEquals("UpdatedTestTask2", taskRepository.getTaskById(taskId2).getTitle());
    }
}