package ru.Andrianov.operations;

import ru.Andrianov.data.Epic;
import ru.Andrianov.data.Status;
import ru.Andrianov.data.Subtask;
import ru.Andrianov.data.TaskRepository;

import java.util.ArrayList;
import java.util.HashMap;

public class EpicStatus {

    public void checkEpicStatus(TaskRepository taskCollections, int epicID) {

        HashMap<Integer, Epic> epics = taskCollections.getEpics();
        HashMap<Integer, Subtask> subtasks = taskCollections.getSubtasks();
        Epic epic = epics.get(epicID);
        ArrayList<Integer> subtasksListID = epic.getSubtasksListID();

        int countNew = 0;
        int countDone = 0;

        for (Integer integer : subtasksListID) {

            Subtask subtask = subtasks.get(integer);

            if (subtask.getStatus() == Status.IN_PROGRESS) {
                epic.setStatus(Status.IN_PROGRESS);
            }

            if (subtask.getStatus() == Status.NEW) {
                countNew++;
            }

            if (subtask.getStatus() == Status.DONE) {
                countDone++;
            }
        }

        if (countNew == subtasksListID.size()) {
            epic.setStatus(Status.NEW);
        }

        if (countDone == subtasksListID.size()) {
            epic.setStatus(Status.DONE);
        }

    }

}
