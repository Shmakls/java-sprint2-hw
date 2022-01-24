package ru.Andrianov.operations;

import ru.Andrianov.data.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class TaskCreationAndUpdate {

    /* Scanner scanner = new Scanner(System.in);

    public void createNewTask(TaskRepository taskCollections, int taskID) {

        System.out.print("Введите название задачи: ");
        String title = scanner.nextLine();
        System.out.println("");

        System.out.print("Введите описание задачи: ");
        String description = scanner.nextLine();

        System.out.print("Введите статус задачи (NEW, IN_PROGRESS, DONE): ");
        String command = scanner.nextLine();
        Status status = getStatus(command);

        System.out.print("Введите тип задачи (task, epic, subtask): ");
        String taskType = scanner.nextLine();

        createTask(taskCollections, title, description, status, taskType, taskID);

        if (taskType.equalsIgnoreCase("subtask")) {
            System.out.print("Введите ID epic задачи, к которой относится данная подзадача: ");
            Integer epicID = Integer.parseInt(scanner.next());
            connectEpicAndSubTask(taskCollections, taskID, epicID);
        }
    }

    public void updateTask(TaskRepository taskCollections, int taskID) {



    }



    public void createTask(TaskRepository taskCollections,
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

    public void connectEpicAndSubTask(TaskRepository taskCollections, int taskID, int epicID) {

        HashMap<Integer, Epic> epics = taskCollections.getEpics();
        HashMap<Integer, Subtask> subtasks = taskCollections.getSubtasks();

        Epic epic = epics.get(epicID);
        ArrayList<Integer> subtasksListID = epic.getSubtasksListID();
        subtasksListID.add(taskID);

        Subtask subtask = subtasks.get(taskID);
        subtask.setEpicTaskID(epicID);

    } */


}
