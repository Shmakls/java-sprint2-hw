package ru.Andrianov.operations;

import ru.Andrianov.data.*;

import java.util.ArrayList;
import java.util.HashMap;

public class EpicStatus {

    public void checkAndChangeEpicStatus(TaskRepository taskRepository, int epicID) {



        Epic epic = (Epic) taskRepository.getTaskByID(epicID);
        ArrayList<Integer> subtasksListID = epic.getSubtasksListID();

        int countNew = 0;
        int countDone = 0;

        for (Integer integer : subtasksListID) {

            Subtask subtask = (Subtask) taskRepository.getTaskByID(integer);

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
