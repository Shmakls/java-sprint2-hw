package ru.andrianov;

import ru.andrianov.hmdata.HistoryRepository;
import ru.andrianov.data.*;
import ru.andrianov.operations.EpicStatus;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class TaskManager {

    private TaskRepository taskRepository;
    private EpicStatus epicStatus;
    private HistoryRepository historyRepository;

    public TaskManager(TaskRepository taskRepository, HistoryRepository historyManager) {
        this.taskRepository = taskRepository;
        epicStatus = new EpicStatus();
        this.historyRepository = historyManager;
    }

    public Integer createNewTask(Task task) {
        if (task != null) {
            Integer taskId = taskRepository.createNewTask(task);
            if (task instanceof Subtask) {
                int epicTaskId = ((Subtask) task).getEpicTaskId();
                Epic epicTask = (Epic) taskRepository.getTaskById(epicTaskId);
                List<Integer> subtasksIds = epicTask.getSubtasksIds();
                if (!subtasksIds.contains(taskId)) {
                    subtasksIds.add(taskId);
                }
                epicStatus.checkAndChangeEpicStatus(taskRepository, epicTaskId);
                task.setId(taskId);
            }
            System.out.println("Новая задача создана. ID - " + taskId
                    + ", название: " + task.getTitle()
                    + ", описание: " + task.getDescription() + ".");
            return taskId;
        } else {
            return null;
        }
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
        System.out.println("Все задачи удалены!");
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

            historyRepository.removeTaskFromHistoryById(taskId);

            if (task instanceof Subtask) {
                epicStatus.checkAndChangeEpicStatus(taskRepository, epicTaskId);
            }
        } else {
            throw new IllegalArgumentException("Такого ID в списке нет");
        }

    }

    public void updateTask(Task task, int taskId) {

            int epicTaskId = 0;
            List<Integer> subtasksIds = null;

            if (task instanceof Epic) {
                subtasksIds = ((Epic) task).getSubtasksIds();
            }

            if (task instanceof Subtask) {
                epicTaskId = ((Subtask) task).getEpicTaskId();
            }

            task.setId(taskId);

            taskRepository.updateTask(task, taskId);
            System.out.println("Задача с ID " + taskId + " обновлена.");

            if (task instanceof Epic) {
                Epic epicTask = (Epic) taskRepository.getTaskById(taskId);
                epicTask.setSubtasksIds(subtasksIds);
            }

            if (task instanceof Subtask) {
                epicStatus.checkAndChangeEpicStatus(taskRepository, epicTaskId);
            }
    }

    public void getSubtaskListByEpic(int epicId) {

        Epic epic = (Epic) taskRepository.getTaskById(epicId);
        String epicTitle = epic.getTitle();
        List<Integer> subtasksIds = epic.getSubtasksIds();

        System.out.println("Для epic задачи " + epicTitle + " имеются следующие подзадачи: ");
        for (Integer subtaskId : subtasksIds) {
            Subtask subtask = (Subtask) taskRepository.getTaskById(subtaskId);
            System.out.println("ID: " + subtaskId
                    + ", название: " + subtask.getTitle()
                    + ", описание: " + subtask.getDescription()
                    + ", статус: " + subtask.getStatus());
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

