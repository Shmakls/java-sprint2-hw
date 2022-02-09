package ru.andrianov.operations;

import ru.andrianov.data.*;

import java.util.ArrayList;
import java.util.HashMap;

public class EpicStatus {

    public void checkAndChangeEpicStatus(TaskRepository taskRepository, int epicId) {

        Epic epic = (Epic) taskRepository.getTaskById(epicId);
        ArrayList<Integer> subtasksIds = epic.getSubtasksIds();

        HashMap<Status, Integer> checker = new HashMap<>();

        for (Integer subtaskId : subtasksIds) {

            Subtask subtask = (Subtask) taskRepository.getTaskById(subtaskId);
            Status status = subtask.getStatus();

            if (checker.containsKey(status)) {
                Integer count = checker.get(status);
                checker.put(status, ++count);
            } else {
                checker.put(status, 1);
            }
        }

        if (checker.size() > 1) {
            epic.setStatus(Status.IN_PROGRESS);
        } else if (checker.size() == 1) {
            epic.setStatus(checker.keySet().iterator().next());
        }
    }

}
