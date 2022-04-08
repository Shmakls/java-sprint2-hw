package ru.andrianov;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.andrianov.data.Epic;
import ru.andrianov.data.Status;
import ru.andrianov.data.Subtask;
import ru.andrianov.data.Task;

import java.util.List;

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

    void addFiveTestTasksToTaskManager() {
        taskManager.createNewTask(task1);
        taskManager.createNewTask(task2);
        taskManager.createNewTask(epic1);
        taskManager.createNewTask(subtask1);
        taskManager.createNewTask(subtask2);
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

    @Test
    void shouldBeChangeEpicTaskStatusWhenSubtaskStatusChanged() {
        Integer task1Id = taskManager.createNewTask(task1);
        Integer task2Id = taskManager.createNewTask(task2);
        Integer epicId = taskManager.createNewTask(epic1);
        Integer subtask1Id = taskManager.createNewTask(subtask1);
        Integer subtask2Id = taskManager.createNewTask(subtask2);

        Epic epicFromRepository = (Epic) taskManager.getTaskById(epicId);
        Status epicTestStatusInProgress = epicFromRepository.getStatus();

        assertEquals(Status.IN_PROGRESS, epicTestStatusInProgress);

        Subtask subtask2FromRepository = (Subtask) taskManager.getTaskById(subtask2Id);
        subtask2FromRepository.setStatus(Status.NEW);
        taskManager.updateTask(subtask2FromRepository, subtask2Id);
        Status epicTestStatusNew = epicFromRepository.getStatus();

        assertEquals(Status.NEW, epicTestStatusNew);

        Subtask subtask1FromRepository = (Subtask) taskManager.getTaskById(subtask1Id);
        subtask1FromRepository.setStatus(Status.DONE);
        subtask2FromRepository.setStatus(Status.DONE);
        taskManager.updateTask(subtask1FromRepository, subtask1Id);
        taskManager.updateTask(subtask2FromRepository, subtask2Id);

        Status epicTestStatusDone = epicFromRepository.getStatus();

        assertEquals(Status.DONE, epicTestStatusDone);

    }

    @Test
    void shouldBePrintTaskListWithFiveTasks() {
        addFiveTestTasksToTaskManager();

        assertEquals(5, taskManager.getAmountOfStoredTasks());

        taskManager.printAllTasks();

        ;


    }

    @Test
    void shouldBeClearTaskRepositoryIfNoTasksSaved() {
        assertEquals(0, taskManager.getAmountOfStoredTasks());

        taskManager.clearAllTasks();

        assertEquals(0, taskManager.getAmountOfStoredTasks());
    }

    @Test
    void shouldBeClearTaskRepositoryWhenUsedClearAllTasksWithTasks() {
        addFiveTestTasksToTaskManager();

        assertEquals(5, taskManager.getAmountOfStoredTasks());

        taskManager.clearAllTasks();

        assertEquals(0, taskManager.getAmountOfStoredTasks());

    }

    @Test
    void shouldBeClearHistoryRepositoryWhenClearAllTasks() {
        addFiveTestTasksToTaskManager();

        assertEquals(5, taskManager.getAmountOfStoredTasks());

        Task callTask1 = taskManager.getTaskById(1);
        Task callEpic1 = taskManager.getTaskById(3);

        assertEquals(2, taskManager.getAmountOfStoredHistory());

        taskManager.clearAllTasks();

        assertEquals(0, taskManager.getAmountOfStoredHistory());

    }

    @Test
    void shouldBeGetTaskByIdIfTaskPresent() {
        addFiveTestTasksToTaskManager();

        assertEquals(5, taskManager.getAmountOfStoredTasks());

        Task testTask1 = taskManager.getTaskById(1);
        Task testSubtask2 = taskManager.getTaskById(5);

        assertEquals(task1.getId(), testTask1.getId());
        assertEquals(subtask2.getId(), testSubtask2.getId());

    }

    @Test
    void shouldBeThrowExceptionIfGetIdNotExist() {
        final IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> taskManager.getTaskById(10)
        );

        assertEquals("Такого ID в списке нет", exception.getMessage());


    }

    @Test
    void shouldBeRemoveTaskFromRepository() {
        addFiveTestTasksToTaskManager();

        assertEquals(5, taskManager.getAmountOfStoredTasks());

        taskManager.removeTaskById(1);

        final IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> taskManager.getTaskById(1)
        );

        assertEquals("Такого ID в списке нет", exception.getMessage());
    }

    @Test
    void shouldBeRemoveEpicAndHisSubtasksFromRepository() {
        addFiveTestTasksToTaskManager();

        assertEquals(5, taskManager.getAmountOfStoredTasks());

        taskManager.removeTaskById(3);

        final IllegalArgumentException epic1Exception = assertThrows(
                IllegalArgumentException.class,
                () -> taskManager.getTaskById(3)
                );

        assertEquals("Такого ID в списке нет", epic1Exception.getMessage());

        final IllegalArgumentException subtask1Exception = assertThrows(
                IllegalArgumentException.class,
                () -> taskManager.getTaskById(4)
        );

        assertEquals("Такого ID в списке нет", subtask1Exception.getMessage());

        final IllegalArgumentException subtask2Exception = assertThrows(
                IllegalArgumentException.class,
                () -> taskManager.getTaskById(5)
        );

        assertEquals("Такого ID в списке нет", subtask2Exception.getMessage());
    }

    @Test
    void shouldBeRemoveSubtaskAndHisConnectionWithEpic() {
        addFiveTestTasksToTaskManager();

        assertEquals(5, taskManager.getAmountOfStoredTasks());

        taskManager.removeTaskById(5);

        assertEquals(4, taskManager.getAmountOfStoredTasks());

        Epic epic1Test = (Epic) taskManager.getTaskById(3);
        List<Integer> subtasksId = epic1Test.getSubtasksIds();

        assertFalse(subtasksId.contains(5));

    }

    @Test
    void shouldBeThrowExceptionIfRemoveIdNotExist() {
        addFiveTestTasksToTaskManager();

        final IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> taskManager.removeTaskById(10)
        );

        assertEquals("Такого ID в списке нет", exception.getMessage());

    }

    @Test
    void shouldBeUpdateTaskIfGiveTask() {
        addFiveTestTasksToTaskManager();

        assertEquals(5, taskManager.getAmountOfStoredTasks());

        Task updateTask1 = new Task("UpdateTask1", "DescriptionUpdateTask1", Status.NEW);

        taskManager.updateTask(updateTask1, 1);

        Task testTask = taskManager.getTaskById(1);

        assertEquals("UpdateTask1", testTask.getTitle());

    }

    @Test
    void shouldBeUpdateTaskIfGiveEpic() {
        addFiveTestTasksToTaskManager();

        assertEquals(5, taskManager.getAmountOfStoredTasks());

        Epic updateEpic1 = new Epic("UpdateEpic1", "DescriptionUpdateEpic1", Status.NEW);

        taskManager.updateTask(updateEpic1, 3);

        Epic epicFromRepository = (Epic) taskManager.getTaskById(3);

        assertEquals("UpdateEpic1", epicFromRepository.getTitle());
    }

    @Test
    void shouldBeUpdateTaskIfGiveSubtask() {
        addFiveTestTasksToTaskManager();
        assertEquals(5, taskManager.getAmountOfStoredTasks());
        Epic epicBeforeUpdateSubtask = (Epic) taskManager.getTaskById(3);

        assertEquals(Status.IN_PROGRESS, epicBeforeUpdateSubtask.getStatus());

        Subtask updateSubtask2 = new Subtask("UpdateSubtask2", "DescriptionUpdateSubtask2", Status.NEW, 3);

        taskManager.updateTask(updateSubtask2, 5);

        Subtask subtaskFromRepository = (Subtask) taskManager.getTaskById(5);

        assertEquals("UpdateSubtask2", subtaskFromRepository.getTitle());

        Epic epicFromRepository = (Epic) taskManager.getTaskById(3);

        assertEquals(Status.NEW, epicFromRepository.getStatus());

    }

}