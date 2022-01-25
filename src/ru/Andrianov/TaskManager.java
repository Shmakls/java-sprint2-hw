package ru.Andrianov;

import ru.Andrianov.data.Epic;
import ru.Andrianov.data.Subtask;
import ru.Andrianov.data.Task;
import ru.Andrianov.data.TaskRepository;
import ru.Andrianov.operations.EpicStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class TaskManager {

    TaskRepository taskRepository;
    Scanner scanner;
    EpicStatus epicStatus;

    public TaskManager() {
        taskRepository = new TaskRepository();
        scanner = new Scanner(System.in);
        epicStatus = new EpicStatus();
    }

    public void createNewTask(Task task) {
        Integer taskID = taskRepository.createNewTask(task);
        if (task != null) {
            if (task instanceof Subtask) {
                int epicTaskID = ((Subtask) task).getEpicTaskID();
                Epic epicTask = (Epic) taskRepository.getTaskByID(epicTaskID);
                ArrayList<Integer> subtasksListID = epicTask.getSubtasksListID();
                if (!subtasksListID.contains(taskID)) {
                    subtasksListID.add(taskID);
                }
                epicStatus.checkAndChangeEpicStatus(taskRepository, epicTaskID);
            }
            System.out.println("Новая задача создана. ID - " + taskID
                                + ", название: " + task.getTitle()
                                + ", описание: " + task.getDescription() + ".");
        }
    }

    public void printAllTasks() {
        HashMap<Integer, Task> tasks = taskRepository.getTasks();
        System.out.println("");
        System.out.print("Вывожу спсок всех задач:");
        for (Integer integer : tasks.keySet()) {
            Task task = tasks.get(integer);
            System.out.println("");
            System.out.print("ID: " + integer
                                + ", название: " + task.getTitle()
                                + ", описание: " + task.getDescription()
                                + ", статус: " + task.getStatus() + ".");
            if (task instanceof Epic) {
                ArrayList<Integer> subtasksListID = ((Epic) task).getSubtasksListID();
                System.out.print(" зависимые подзадачи:" + subtasksListID.toString());
            }
            if (task instanceof Subtask) {
                int epicTask = ((Subtask) task).getEpicTaskID();
                System.out.print(" относится к epic задаче с ID" + (epicTask));
            }
        }
        System.out.println("");
    }

    public void clearAllTasks() {
        taskRepository.clearAllTasks();
        System.out.println("Все задачи удалены!");
    }

    public Task getTaskByID(int taskID) {
        Task task = taskRepository.getTaskByID(taskID);
        System.out.println("Задача \"" + task.getTitle() + "\" получена.");
        return task;
    }

    public void removeTaskByID(int taskID) {
        Task task = taskRepository.getTaskByID(taskID);
        int epicTaskID = 0;

        if (task instanceof Epic) {
            ArrayList<Integer> subtasksListID = ((Epic) task).getSubtasksListID();
            for (Integer integer : subtasksListID) {
                taskRepository.removeTaskByID(integer);
            }
            System.out.println("Все связанные подзадачи удалены!");
        }

        if (task instanceof Subtask) {
            epicTaskID = ((Subtask) task).getEpicTaskID();
            Epic epicTask = (Epic) taskRepository.getTaskByID(epicTaskID);

            ArrayList<Integer> subtaskListID = epicTask.getSubtasksListID();
            subtaskListID.remove(taskID);

            System.out.println("Связь subtask с epic задачей удалена!");
        }

        String title = task.getTitle();
        taskRepository.removeTaskByID(taskID);
        System.out.println("Задача \"" + title + "\" удалена!");

        if (task instanceof Subtask) {
            epicStatus.checkAndChangeEpicStatus(taskRepository, epicTaskID);
        }

    }

    public void updateTask(Task task, int taskID) {

        int epicTaskID = 0;
        ArrayList<Integer> subtasksListID = null;

        if (task instanceof Epic) {
            subtasksListID = ((Epic) task).getSubtasksListID();
        }

        if (task instanceof Subtask) {
            epicTaskID = ((Subtask) task).getEpicTaskID();
        }

        taskRepository.updateTask(task, taskID);
        System.out.println("Задача с ID " + taskID + " обновлена.");

        if (task instanceof Epic) {
            Epic epicTask = (Epic) taskRepository.getTaskByID(taskID);
            epicTask.setSubtasksListID(subtasksListID);
        }

        if (task instanceof Subtask) {
            epicStatus.checkAndChangeEpicStatus(taskRepository, epicTaskID);
        }

    }

    public void getSubtaskListByEpic(int epicID) {

        Epic epic = (Epic) taskRepository.getTaskByID(epicID);
        String epicTitle = epic.getTitle();
        ArrayList<Integer> subtaskListID = epic.getSubtasksListID();

        System.out.println("Для epic задачи " + epicTitle + " имеются следующие подзадачи: ");
        for (Integer subtaskID : subtaskListID) {
            Subtask subtask = (Subtask) taskRepository.getTaskByID(subtaskID);
            System.out.println("ID: " + subtaskID
                    + ", название: " + subtask.getTitle()
                    + ", описание: " + subtask.getDescription()
                    + ", статус: " + subtask.getStatus());
        }
    }

}
