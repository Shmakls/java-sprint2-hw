package ru.andrianov.operations;

import ru.andrianov.data.Epic;
import ru.andrianov.data.Task;
import ru.andrianov.data.TaskRepository;

import java.time.Duration;
import java.util.*;

public class CalculationOfTimeValuesForEpic {

    public static void checkAndChangeEpicTimeValues(TaskRepository taskRepository, int epicId) {

        Epic epic = (Epic) taskRepository.getTaskById(epicId);
        List<Integer> subtasksIds = epic.getSubtasksIds();

        List<Task> subtasksList = new ArrayList<>();

        for (Integer subtasksId : subtasksIds) {
            Task task = taskRepository.getTaskById(subtasksId);
            subtasksList.add(task);
        }

        subtasksList.sort(Comparator.comparing(Task::getStartTime));

        epic.setStartTime(subtasksList.get(0).getStartTime());
        epic.setEndTime(subtasksList.get(subtasksList.size()-1).getEndTime());

        Duration epicDuration = Duration.ofSeconds(0);

        for (Task task : subtasksList) {
            epicDuration = epicDuration.plus(task.getEstimationTime());
        }

        epic.setEstimationTime(epicDuration);

    }

}
