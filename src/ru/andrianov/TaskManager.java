package ru.andrianov;

import ru.andrianov.data.Epic;
import ru.andrianov.data.Subtask;
import ru.andrianov.data.Task;
import ru.andrianov.data.TaskRepository;
import ru.andrianov.operations.EpicStatus;

import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {

    private TaskRepository taskRepository;
    private EpicStatus epicStatus;

    public TaskManager() {
        taskRepository = new TaskRepository();
        epicStatus = new EpicStatus();
    }

    public void createNewTask(Task task) {
        Integer taskId = taskRepository.createNewTask(task);
        if (task != null) {
            if (task instanceof Subtask) {
                int epicTaskId = ((Subtask) task).getEpicTaskId();
                Epic epicTask = (Epic) taskRepository.getTaskById(epicTaskId);
                ArrayList<Integer> subtasksIds = epicTask.getSubtasksIds();
                if (!subtasksIds.contains(taskId)) {
                    subtasksIds.add(taskId);
                }
                epicStatus.checkAndChangeEpicStatus(taskRepository, epicTaskId);
            }
            System.out.println("Новая задача создана. ID - " + taskId
                                + ", название: " + task.getTitle()
                                + ", описание: " + task.getDescription() + ".");
        }
    }

    public void printAllTasks() {
        HashMap<Integer, Task> tasks = taskRepository.getTasks();
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
        taskRepository.clearAllTasks();
        System.out.println("Все задачи удалены!");
    }

    public Task getTaskById(int taskId) {
        Task task = taskRepository.getTaskById(taskId);
        System.out.println("Задача \"" + task.getTitle() + "\" получена.");
        return task;
    }

    public void removeTaskById(int taskId) {
        Task task = taskRepository.getTaskById(taskId);
        int epicTaskId = 0;

        if (task instanceof Epic) {
            ArrayList<Integer> subtasksIds = ((Epic) task).getSubtasksIds();
            for (Integer subtaskId : subtasksIds) {
                taskRepository.removeTaskById(subtaskId);
            }
            System.out.println("Все связанные подзадачи удалены!");
        }

        if (task instanceof Subtask) {
            epicTaskId = ((Subtask) task).getEpicTaskId();
            Epic epicTask = (Epic) taskRepository.getTaskById(epicTaskId);

            ArrayList<Integer> subtasksIds = epicTask.getSubtasksIds();
            subtasksIds.remove(taskId);

            System.out.println("Связь subtask с epic задачей удалена!");
        }

        String title = task.getTitle();
        taskRepository.removeTaskById(taskId);
        System.out.println("Задача \"" + title + "\" удалена!");

        if (task instanceof Subtask) {
            epicStatus.checkAndChangeEpicStatus(taskRepository, epicTaskId);
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

        taskRepository.updateTask(task, taskId);
        System.out.println("Задача с ID " + taskId + " обновлена.");

        if (task instanceof Epic) {
            Epic epicTask = (Epic) taskRepository.getTaskById(taskId);
            epicTask.setSubtasksIds(subtasksIds);
        }

        if (task instanceof Subtask) {
            epicStatus.checkAndChangeEpicStatus(taskRepository, epicTaskId);
        }

    }

    public void getSubtaskListByEpic(int epicId) {

        Epic epic = (Epic) taskRepository.getTaskById(epicId);
        String epicTitle = epic.getTitle();
        ArrayList<Integer> subtasksIds = epic.getSubtasksIds();

        System.out.println("Для epic задачи " + epicTitle + " имеются следующие подзадачи: ");
        for (Integer subtaskId : subtasksIds) {
            Subtask subtask = (Subtask) taskRepository.getTaskById(subtaskId);
            System.out.println("ID: " + subtaskId
                    + ", название: " + subtask.getTitle()
                    + ", описание: " + subtask.getDescription()
                    + ", статус: " + subtask.getStatus());
        }
    }

}