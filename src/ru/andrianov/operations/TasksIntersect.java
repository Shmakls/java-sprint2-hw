package ru.andrianov.operations;

import ru.andrianov.data.Task;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TasksIntersect {

    private static boolean doesTasksIntersect(Task task1, Task task2) {

            ZonedDateTime B1 = task1.getStartTime();
            ZonedDateTime E1 = task1.getEndTime();
            ZonedDateTime B2 = task2.getStartTime();
            ZonedDateTime E2 = task2.getEndTime();

        // проверяем что начало 2-й задачи лежит в интервале первой или конец 2-й задачи лежит в интервале первой
        if (B1.isBefore(B2) && E1.isAfter(B2) || B1.isBefore(E2) && E1.isAfter(E2)) {
            return true;
        // проверяем что начало 1-й задачи лежит в интервале второй или конец 1-й задачи лежит в интервале второй
        } else if (B2.isBefore(B1) && E2.isAfter(B1) || B2.isBefore(E2) && E2.isAfter(E2)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean canAddTask(Map<Integer, Task> tasks, Task task) {

        List<Task> tasksList = new ArrayList<>(tasks.values());
        return tasksList.stream()
               .noneMatch(existingTask -> doesTasksIntersect(existingTask, task));
    }
}
