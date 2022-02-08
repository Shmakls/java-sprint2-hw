package ru.andrianov;

import com.sun.source.tree.LiteralTree;
import ru.andrianov.data.*;
import ru.andrianov.operations.EpicStatus;

import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {

    private InMemoryTaskRepository inMemoryTaskRepository;
    private EpicStatus epicStatus;
    private InMemoryHistoryManager inMemoryHistoryManager;

    public TaskManager() {
        inMemoryTaskRepository = new InMemoryTaskRepository();
        epicStatus = new EpicStatus();
        inMemoryHistoryManager = new InMemoryHistoryManager();
    }

    public void createNewTask(Task task) {
        Integer taskId = inMemoryTaskRepository.createNewTask(task);
        if (task != null) {
            if (task instanceof Subtask) {
                int epicTaskId = ((Subtask) task).getEpicTaskId();
                Epic epicTask = (Epic) inMemoryTaskRepository.getTaskById(epicTaskId);
                ArrayList<Integer> subtasksIds = epicTask.getSubtasksIds();
                if (!subtasksIds.contains(taskId)) {
                    subtasksIds.add(taskId);
                }
                epicStatus.checkAndChangeEpicStatus(inMemoryTaskRepository, epicTaskId);
                task.setId(taskId);
            }
            System.out.println("Новая задача создана. ID - " + taskId
                    + ", название: " + task.getTitle()
                    + ", описание: " + task.getDescription() + ".");
        }
    }

    public void printAllTasks() {
        HashMap<Integer, Task> tasks = inMemoryTaskRepository.getTasks();
        System.out.println("");
        System.out.print("Вывожу список всех задач:");
        for (Integer taskId : tasks.keySet()) {
            Task task = tasks.get(taskId);
            System.out.println("");
            System.out.print("ID: " + taskId
                    + ", название: " + task.getTitle()
                    + ", описание: " + task.getDescription()
                    + ", статус: " + task.getStatus() + ".");
            if (task instanceof Epic) {
                ArrayList<Integer> subtasksIds = ((Epic) task).getSubtasksIds();
                System.out.print(" зависимые подзадачи с ID: " + subtasksIds.toString() + ".");
            }
            if (task instanceof Subtask) {
                int epicTask = ((Subtask) task).getEpicTaskId();
                System.out.print(" относится к epic задаче с ID " + (epicTask) + ".");
            }
        }
        System.out.println("");
        System.out.println("");
    }

    public void clearAllTasks() {
        inMemoryTaskRepository.clearAllTasks();
        inMemoryHistoryManager.clearHistory();
        System.out.println("Все задачи удалены!");
    }

    public Task getTaskById(int taskId) {
        Task task = inMemoryTaskRepository.getTaskById(taskId);
        System.out.println("Задача \"" + task.getTitle() + "\" получена.");
        inMemoryHistoryManager.add(task);
        return task;
    }

    public void removeTaskById(int taskId) {
        Task task = inMemoryTaskRepository.getTaskById(taskId);
        int epicTaskId = 0;

        if (task instanceof Epic) {
            ArrayList<Integer> subtasksIds = ((Epic) task).getSubtasksIds();
            for (Integer subtaskId : subtasksIds) {
                inMemoryTaskRepository.removeTaskById(subtaskId);
                inMemoryHistoryManager.removeTaskFromHistoryById(subtaskId);
            }
            System.out.println("Все связанные подзадачи удалены!");
        }

        if (task instanceof Subtask) {
            epicTaskId = ((Subtask) task).getEpicTaskId();
            Epic epicTask = (Epic) inMemoryTaskRepository.getTaskById(epicTaskId);

            ArrayList<Integer> subtasksIds = epicTask.getSubtasksIds();
            subtasksIds.remove(taskId);

            System.out.println("Связь subtask с epic задачей удалена!");
        }

        String title = task.getTitle();
        inMemoryTaskRepository.removeTaskById(taskId);
        System.out.println("Задача \"" + title + "\" удалена!");

        inMemoryHistoryManager.removeTaskFromHistoryById(taskId);

        if (task instanceof Subtask) {
            epicStatus.checkAndChangeEpicStatus(inMemoryTaskRepository, epicTaskId);
        }

    }

    public void updateTask(Task task, int taskId) {

        int epicTaskId = 0;
        ArrayList<Integer> subtasksIds = null;

        if (task instanceof Epic) {
            subtasksIds = ((Epic) task).getSubtasksIds();
        }

        if (task instanceof Subtask) {
            epicTaskId = ((Subtask) task).getEpicTaskId();
        }

        task.setId(taskId);

        inMemoryTaskRepository.updateTask(task, taskId);
        System.out.println("Задача с ID " + taskId + " обновлена.");

        if (task instanceof Epic) {
            Epic epicTask = (Epic) inMemoryTaskRepository.getTaskById(taskId);
            epicTask.setSubtasksIds(subtasksIds);
        }

        if (task instanceof Subtask) {
            epicStatus.checkAndChangeEpicStatus(inMemoryTaskRepository, epicTaskId);
        }

    }

    public void getSubtaskListByEpic(int epicId) {

        Epic epic = (Epic) inMemoryTaskRepository.getTaskById(epicId);
        String epicTitle = epic.getTitle();
        ArrayList<Integer> subtasksIds = epic.getSubtasksIds();

        System.out.println("Для epic задачи " + epicTitle + " имеются следующие подзадачи: ");
        for (Integer subtaskId : subtasksIds) {
            Subtask subtask = (Subtask) inMemoryTaskRepository.getTaskById(subtaskId);
            System.out.println("ID: " + subtaskId
                    + ", название: " + subtask.getTitle()
                    + ", описание: " + subtask.getDescription()
                    + ", статус: " + subtask.getStatus());
        }
    }

    public void printHistory() {
        ArrayList<Task> viewedTasks = inMemoryHistoryManager.getHistory();
        System.out.println("Вывожу историю просмотренных задач:");
        for (Task viewedTask : viewedTasks) {
            System.out.println(viewedTask);
        }
        System.out.println("");
    }


}

