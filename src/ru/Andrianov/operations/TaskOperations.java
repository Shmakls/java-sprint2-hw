package ru.Andrianov.operations;

import ru.Andrianov.data.Epic;
import ru.Andrianov.data.Subtask;
import ru.Andrianov.data.Task;
import ru.Andrianov.data.TaskCollections;

import java.util.ArrayList;
import java.util.HashMap;

public class TaskOperations {


    public void getTaskList(TaskCollections taskCollections) {
        HashMap<Integer, Task> tasks = taskCollections.getTasks();
        HashMap<Integer, Epic> epics = taskCollections.getEpics();
        HashMap<Integer, Subtask> subtasks = taskCollections.getSubtasks();

        System.out.println("Вывожу список всех задач:");

        System.out.println("Обычные задачи:");
        for (Integer integer : tasks.keySet()) {
            Task task = tasks.get(integer);
            System.out.println("ID: " + integer
                                + ", название: " + task.getTitle()
                                + ", статус: " + task.getStatus());
        }

        System.out.println("Эпические задачи:");
        for (Integer integer : epics.keySet()) {
            Epic epic = epics.get(integer);
            System.out.println("ID: " + integer
                                + ", название: " + epic.getTitle()
                                + ", статус: " + epic.getStatus()
                                + ", зависимые подзадачи: " + epic.getSubtasksListID().toString());
        }

        System.out.println("Подзадачи:");
        for (Integer integer : subtasks.keySet()) {
            Subtask subtask = subtasks.get(integer);
            System.out.println("ID: " + integer
                                + ", название: " + subtask.getTitle()
                                + ", статус: " + subtask.getStatus()
                                + ", относится к epic задаче с ID " + subtask.getEpicTaskID());
        }
    }

    public void clearAllTasks(TaskCollections taskCollections) {
        HashMap<Integer, Task> tasks = taskCollections.getTasks();
        HashMap<Integer, Epic> epics = taskCollections.getEpics();
        HashMap<Integer, Subtask> subtasks = taskCollections.getSubtasks();

        tasks.clear();
        epics.clear();
        subtasks.clear();

        System.out.println("Все задачи удалены!");

    }

    public void getTaskByID(TaskCollections taskCollections, int taskID) {

        HashMap<Integer, Task> tasks = taskCollections.getTasks();
        HashMap<Integer, Epic> epics = taskCollections.getEpics();
        HashMap<Integer, Subtask> subtasks = taskCollections.getSubtasks();

        for (Integer integer : tasks.keySet()) {
            if (integer == taskID) {
                Task task = tasks.get(integer);
                System.out.println("ID: " + integer
                                    + ", название: " + task.getTitle()
                                    + ", статус: " + task.getStatus());
                return;
            }
        }

        for (Integer integer : epics.keySet()) {
            if (integer == taskID) {
                Epic epic = epics.get(integer);
                System.out.println("ID: " + integer
                        + ", название: " + epic.getTitle()
                        + ", статус: " + epic.getStatus()
                        + ", зависимые подзадачи: " + epic.getSubtasksListID().toString());
                return;
            }
        }

        for (Integer integer : subtasks.keySet()) {
            if (integer == taskID) {
                Subtask subtask = subtasks.get(integer);
                System.out.println("ID: " + integer
                        + ", название: " + subtask.getTitle()
                        + ", статус: " + subtask.getStatus()
                        + ", относится к epic задаче с ID " + subtask.getEpicTaskID());
                return;
            }
        }

        System.out.println("Задачи с таким ID нет!");

    }

    public void removeTaskByID(TaskCollections taskCollections, int taskID) {

        HashMap<Integer, Task> tasks = taskCollections.getTasks();
        HashMap<Integer, Epic> epics = taskCollections.getEpics();
        HashMap<Integer, Subtask> subtasks = taskCollections.getSubtasks();

        for (Integer integer : tasks.keySet()) {
            if (integer == taskID) {
                tasks.remove(integer);
                System.out.println("Задача удалена!");
                return;
            }
        }

        for (Integer integer : epics.keySet()) {
            if (integer == taskID) {
                Epic epic = epics.get(integer);
                ArrayList<Integer> subtasksListID = epic.getSubtasksListID();
                removeSubtasksIfRemoveEpic(taskCollections, subtasksListID);
                System.out.println("Epic задача удалена!");
                return;
            }
        }

        for (Integer integer : subtasks.keySet()) {
            if (integer == taskID) {
                Subtask subtask = subtasks.get(integer);
                int epicID = subtask.getEpicTaskID();
                removeConnectToEpicAfterDeleteSubtask(taskCollections, epicID, taskID);
                System.out.println("Подзадача удалена!");
                return;
            }
        }

    }

    public void removeSubtasksIfRemoveEpic(TaskCollections taskCollections, ArrayList<Integer> subtasksListID) {

        HashMap<Integer, Subtask> subtask = taskCollections.getSubtasks();

        for (int subtaskID : subtasksListID) {
            for (int epicTaskID : subtask.keySet()) {
                if (epicTaskID == subtaskID) {
                    subtask.remove(subtaskID);
                }
            }
        }
        System.out.println("Связанные подзадачи удалены!");
    }

    public void removeConnectToEpicAfterDeleteSubtask(TaskCollections taskCollections, int epicID, int taskID ) {

        HashMap<Integer, Epic> epics = taskCollections.getEpics();
        Epic epic = epics.get(epicID);
        ArrayList<Integer> subtasksListID = epic.getSubtasksListID();

        if (subtasksListID.contains(taskID)) {
            subtasksListID.remove(taskID);
            System.out.println("Связь epic задачи с данной подзадачей удалена!");
        }

    }

}
