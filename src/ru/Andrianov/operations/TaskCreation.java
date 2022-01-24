package ru.Andrianov.operations;

import ru.Andrianov.data.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class TaskCreation {

    Scanner scanner = new Scanner(System.in);

    public void createNewTask(TaskCollections taskCollections, int taskID) {

        System.out.println("Введите название задачи:");
        String title = scanner.next();

        System.out.println("Введите описание задачи:");
        String description = scanner.nextLine();

        System.out.println("Введите статус задачи (NEW, IN_PROGRESS, DONE):");
        String command = scanner.next();
        Status status = getStatus(command);

        System.out.println("Введите тип задачи (task, epic, subtask)");
        String taskType = scanner.next();

        if (taskType.equalsIgnoreCase("subtask")) {
            System.out.println("Введите ID epic задачи, к которой относится данная подзадача:");
            Integer epicID = Integer.parseInt(scanner.next());
            connectEpicAndSubTask(taskCollections, taskID, epicID);
        }

        createTask(taskCollections, title, description, status, taskType, taskID);

    }

    public void createTask(TaskCollections taskCollections,
                              String title,
                              String description,
                              Status status,
                              String taskType,
                              int taskID) {

        switch (taskType.toLowerCase()) {
            case "task":
                HashMap<Integer, Task> tasks = taskCollections.getTasks();
                Task task = new Task(title, description, status);
                tasks.put(taskID, task);
                break;

            case "epic":
                HashMap<Integer, Epic> epics = taskCollections.getEpics();
                Epic epic = new Epic(title, description, Status.NEW);
                epics.put(taskID, epic);
                break;

            case "subtask":
                HashMap<Integer, Subtask> subtasks = taskCollections.getSubtasks();
                Subtask subtask = new Subtask(title, description, status);
                subtasks.put(taskID, subtask);
                break;
        }

    }

    public Status getStatus(String command) {
        Status status = null;
        switch (command.toUpperCase()) {
            case "NEW":
                status = Status.NEW;
                break;
            case "IN_PROGRESS":
                status = Status.IN_PROGRESS;
                break;
            case "DONE":
                status = Status.DONE;
                break;
        }
        return status;
    }

    public void connectEpicAndSubTask(TaskCollections taskCollections, int taskID, int epicID) {

        HashMap<Integer, Epic> epics = taskCollections.getEpics();
        HashMap<Integer, Subtask> subtasks = taskCollections.getSubtasks();

        Epic epic = epics.get(epicID);
        ArrayList<Integer> subtasksListID = epic.getSubtasksListID();
        subtasksListID.add(taskID);

        Subtask subtask = subtasks.get(taskID);
        subtask.setEpicTaskID(epicID);

    }


}
