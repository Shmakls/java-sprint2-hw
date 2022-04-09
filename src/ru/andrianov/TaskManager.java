package ru.andrianov;

import ru.andrianov.hmdata.HistoryRepository;
import ru.andrianov.data.*;
import ru.andrianov.operations.CalculationOfTimeValuesForEpic;
import ru.andrianov.operations.EpicStatus;
import ru.andrianov.operations.StartTimeComparator;
import ru.andrianov.operations.TasksIntersect;

import java.util.*;

public class TaskManager {

    private final TaskRepository taskRepository;
    private final EpicStatus epicStatus;
    private final HistoryRepository historyRepository;
    private final TreeSet<Task> sortedByStartTimeTasks = new TreeSet<>(new StartTimeComparator());

    public TaskManager(TaskRepository taskRepository, HistoryRepository historyManager) {
        this.taskRepository = taskRepository;
        epicStatus = new EpicStatus();
        this.historyRepository = historyManager;
    }

    public Integer createNewTask(Task task) {

        if (task == null) {
            throw new IllegalArgumentException("Передана пустая задача");
        }

        if (!(TasksIntersect.canAddTask(taskRepository.getTasks(), task))) {
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
    }

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

    public void clearAllTasks() {
        taskRepository.clearAllTasks();
        historyRepository.clear();
    }

    public Task getTaskById(int taskId) {
        if (taskRepository.getTasks().containsKey(taskId)) {
            Task task = taskRepository.getTaskById(taskId);
            historyRepository.add(task);
            return task;
        } else {
            throw new IllegalArgumentException("Такого ID в списке нет");
        }
    }

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

    }

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
    }

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
    }

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

    public TreeSet<Task> getPrioritizedTasks() {
        return sortedByStartTimeTasks;
    }

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
}

