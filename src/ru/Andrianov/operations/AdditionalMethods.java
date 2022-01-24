package ru.Andrianov.operations;

import ru.Andrianov.data.Epic;
import ru.Andrianov.data.Subtask;
import ru.Andrianov.data.TaskCollections;

import java.util.ArrayList;
import java.util.HashMap;

public class AdditionalMethods {

    public void getSubtaskListByEpic(TaskCollections taskCollections, int epicID) {

        HashMap<Integer, Epic> epics = taskCollections.getEpics();
        HashMap<Integer, Subtask> subtasks = taskCollections.getSubtasks();

        Epic epic = epics.get(epicID);
        String epicTitle = epic.getTitle();
        ArrayList<Integer> subtaskListID = epic.getSubtasksListID();

        System.out.println("Для epic задачи " + epicTitle + " имеются следующие подзадачи: ");
        for (Integer subtaskID : subtaskListID) {
            Subtask subtask = subtasks.get(subtaskID);
            System.out.println("ID: " + subtaskID
                                + ", название: " + subtask.getTitle()
                                + ", описание: " + subtask.getDescription()
                                + ", статус: " + subtask.getStatus());
        }
    }
}
