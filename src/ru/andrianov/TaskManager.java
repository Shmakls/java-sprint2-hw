package ru.andrianov;

import ru.andrianov.hmdata.HistoryRepository;
import ru.andrianov.data.*;
import ru.andrianov.operations.CalculationOfTimeValuesForEpic;
import ru.andrianov.operations.EpicStatus;
import ru.andrianov.operations.StartTimeComparator;

import java.time.ZonedDateTime;
import java.util.*;

public class TaskManager {

    private final TaskRepository taskRepository;
    private final EpicStatus epicStatus;
    private final HistoryRepository historyRepository;
    private final Set<Task> sortedByStartTimeTasks = new TreeSet<>(new StartTimeComparator());

    public TaskManager(TaskRepository taskRepository, HistoryRepository historyManager) {
        this.taskRepository = taskRepository;
        epicStatus = new EpicStatus();
        this.historyRepository = historyManager;
    }

    public Integer createNewTask(Task task) {

        if (task == null) {
            throw new IllegalArgumentException("Передана пустая задача");
        }

        if (!(canAddTask(taskRepository.getTasks(), task))) {
            throw new IllegalArgumentException("Новая задача пересекается по времени с существующими");
        }

        Integer taskId = taskRepository.createNewTask(task);
        sortedByStartTimeTasks.add(task);

        if (task instanceof Subtask) {
            int epicTaskId = ((Subtask) task).getEpicTaskId();
            Epic epicTask = (Epic) taskRepository.getTaskById(epicTaskId);
            List<Integer> subtasksIds = epicTask.getSubtasksIds();
            if (!subtasksIds.contains(taskId)) {
                subtasksIds.add(taskId);
            }
            epicStatus.checkAndChangeEpicStatus(taskRepository, epicTaskId);
            CalculationOfTimeValuesForEpic.checkAndChangeEpicTimeValues(taskRepository, epicTaskId);
            task.setId(taskId);
        }
        return taskId;
    }                                       // POST/tasks/task Body{task} - done

    public void printAllTasks() {
        Map<Integer, Task> tasks = taskRepository.getTasks();
        System.out.println("");
        if (tasks.size() > 0) {
            System.out.print("Вывожу список всех задач:");
            for (Integer taskId : tasks.keySet()) {
                Task task = tasks.get(taskId);
                System.out.println("");
                System.out.print(task);
            }

            System.out.println("");
        } else {
            System.out.println("Список задач пуст.");
        }
        System.out.println("");
    }

    public Map<Integer, Task> getAllTasks() {
        return taskRepository.getTasks();
    }       // GET/tasks/task - done

    public void clearAllTasks() {
        taskRepository.clearAllTasks();
        historyRepository.clear();
    }                                                   // DELETE/tasks/task - done

    public Task getTaskById(int taskId) {
        if (taskRepository.getTasks().containsKey(taskId)) {
            Task task = taskRepository.getTaskById(taskId);
            historyRepository.add(task);
            return task;
        } else {
            throw new IllegalArgumentException("Такого ID в списке нет");
        }
    }                                           // GET/tasks/task/{id} - done

    public void removeTaskById(int taskId) {
        if (taskRepository.getTasks().containsKey(taskId)) {
            Task task = taskRepository.getTaskById(taskId);
            int epicTaskId = 0;

            if (task instanceof Epic) {
                List<Integer> subtasksIds = ((Epic) task).getSubtasksIds();
                for (Integer subtaskId : subtasksIds) {
                    taskRepository.removeTaskById(subtaskId);
                    historyRepository.removeTaskFromHistoryById(subtaskId);
                }
                System.out.println("Все связанные подзадачи удалены!");
            }

            if (task instanceof Subtask) {
                epicTaskId = ((Subtask) task).getEpicTaskId();
                Epic epicTask = (Epic) taskRepository.getTaskById(epicTaskId);

                List<Integer> subtasksIds = epicTask.getSubtasksIds();
                subtasksIds.remove((Integer) taskId);

                System.out.println("Связь subtask с epic задачей удалена!");
            }

            String title = task.getTitle();
            taskRepository.removeTaskById(taskId);
            System.out.println("Задача \"" + title + "\" удалена!");

            sortedByStartTimeTasks.remove(task);
            historyRepository.removeTaskFromHistoryById(taskId);

            if (task instanceof Subtask) {
                epicStatus.checkAndChangeEpicStatus(taskRepository, epicTaskId);
                CalculationOfTimeValuesForEpic.checkAndChangeEpicTimeValues(taskRepository, epicTaskId);
            }
        } else {
            throw new IllegalArgumentException("Такого ID в списке нет");
        }

    }                                        // DELETE/tasks/task/{id} - done

    public void updateTask(Task task, int taskId) {

        if (task == null) {
            throw new IllegalArgumentException("Передана пустая задача.");
        }

        if (taskRepository.getTasks().containsKey(taskId)) {
            int epicTaskId = 0;
            List<Integer> subtasksIds = null;

            if (task instanceof Epic) {
                subtasksIds = ((Epic) task).getSubtasksIds();
            }

            if (task instanceof Subtask) {
                epicTaskId = ((Subtask) task).getEpicTaskId();
            }

            task.setId(taskId);
            Task taskForUpdate = taskRepository.getTaskById(taskId);
            sortedByStartTimeTasks.remove(taskForUpdate);

            taskRepository.updateTask(task, taskId);
            sortedByStartTimeTasks.add(task);

            System.out.println("Задача с ID " + taskId + " обновлена.");

            if (task instanceof Epic) {
                Epic epicTask = (Epic) taskRepository.getTaskById(taskId);
                epicTask.setSubtasksIds(subtasksIds);
            }

            if (task instanceof Subtask) {
                epicStatus.checkAndChangeEpicStatus(taskRepository, epicTaskId);
                CalculationOfTimeValuesForEpic.checkAndChangeEpicTimeValues(taskRepository, epicTaskId);
            }
        } else {
            throw new IllegalArgumentException("Такого ID в списке нет");
        }
    }                                 // PUT/tasks/task Body{task} - done

    public List<Integer> getSubtaskListByEpic(int epicId) {

        if (taskRepository.getTasks().containsKey(epicId)) {
            Epic epic = (Epic) taskRepository.getTaskById(epicId);
            List<Integer> subtasksIds = epic.getSubtasksIds();

            System.out.println("Для epic задачи " + epic.getTitle() + " имеются следующие подзадачи: ");
            for (Integer subtaskId : subtasksIds) {
                Subtask subtask = (Subtask) taskRepository.getTaskById(subtaskId);
                System.out.println("ID: " + subtaskId
                        + ", название: " + subtask.getTitle()
                        + ", описание: " + subtask.getDescription()
                        + ", статус: " + subtask.getStatus());
            }
            return subtasksIds;
        } else {
            throw new IllegalArgumentException("Такого ID в списке нет");
        }
    }                         // GET/tasks/subtasksListByEpic/{id} - done

    public void printHistory() {
        Collection<Task> viewedTasks = historyRepository.getHistory();
        System.out.println("Вывожу историю просмотренных задач:");
        if (viewedTasks != null) {
            for (Task viewedTask : viewedTasks) {
                System.out.println(viewedTask);
            }
            System.out.println("");
        } else {
            System.out.println("История просмотров пуста");
        }
    }

    public Collection<Task> getHistory() {
        return historyRepository.getHistory();
    }     // GET/tasks/history - done

    public Set<Task> getPrioritizedTasks() {
        return sortedByStartTimeTasks;
    }                                        // GET/tasks/prioritizedTask - done

    public Integer getAmountOfStoredTasks() {
        return taskRepository.getTasks().size();
    }

    public Integer getAmountOfStoredHistory() {
        if (historyRepository.getHistory() != null) {
            return historyRepository.getHistory().size();
        } else {
            return 0;
        }
    }

    private boolean doesTasksIntersect(Task task1, Task task2) {

        ZonedDateTime begin1 = task1.getStartTime();
        ZonedDateTime end1 = task1.getEndTime();
        ZonedDateTime begin2 = task2.getStartTime();
        ZonedDateTime end2 = task2.getEndTime();

        // проверяем что начало 2-й задачи лежит в интервале первой или конец 2-й задачи лежит в интервале первой
        if (begin1.isBefore(begin2) && end1.isAfter(begin2) || begin1.isBefore(end2) && end1.isAfter(end2)) {
            return true;
            // проверяем что начало 1-й задачи лежит в интервале второй или конец 1-й задачи лежит в интервале второй
        } else if (begin2.isBefore(begin1) && end2.isAfter(begin1) || begin2.isBefore(end2) && end2.isAfter(end2)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean canAddTask(Map<Integer, Task> tasks, Task task) {

        List<Task> tasksList = new ArrayList<>(tasks.values());
        return tasksList.stream()
                .noneMatch(existingTask -> doesTasksIntersect(existingTask, task));
    }

}

