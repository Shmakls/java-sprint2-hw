package ru.andrianov.data;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FileBackedTasksRepository implements TaskRepository {

    String filePath;
    Map<Integer, Task> tasks;
    Integer idCounter;

    public FileBackedTasksRepository(String filePath) {
        tasks = new HashMap<>();
        idCounter = 0;
        this.filePath = filePath;
        restore();
    }

    public String readFileContentsOrNull(String filePath) {
        try {
            return Files.readString(Path.of(filePath));
        } catch (IOException e) {
            System.out.println("Невозможно прочитать файл с месячным отчётом. Возможно, файл не находится в нужной директории.");
            return null;
        }
    }

    public void restore() {
        try {
            String[] lines = readFileContentsOrNull(filePath).split("\r\n");
            for (int i = 1; i < lines.length; i++) {
                Task task = fromString(lines[i]);
                tasks.put(task.getId(), task);
            }
        } catch (NullPointerException e) {
            System.out.println("Данные с файла не были считаны");
        }
    }

    public String toString(Task task) {

        Type type = findTypeTask(task);

        StringBuilder taskString = new StringBuilder((task.getId() + "," + type.currency + ","
                                                    + task.getTitle() + "," + task.getStatus().currency + ","
                                                    + task.getDescription() + ","));

        if (task instanceof Subtask) {
            taskString.append(((Subtask) task).getEpicTaskId());
        }

        if (task instanceof Epic) {
            List<Integer> subtasksId = ((Epic) task).getSubtasksIds();
            for (int i = 0; i < subtasksId.size(); i++) {
                taskString.append(subtasksId.get(i));
                if (i != subtasksId.size() - 1) {
                    taskString.append(",");
                }
            }
        }
        return taskString.toString();
    }

    public Task fromString(String value) {

        String[] fromString = value.split(",");
        int taskId = Integer.parseInt(fromString[0]);
        Type type = Type.getTypeByString(fromString[1]);
        String title = fromString[2];
        Status status = Status.getStatusByString(fromString[3]);
        String description = fromString[4];

        if (fromString[1].equals("SUBTASK")) {
            int epicId = Integer.parseInt(fromString[5]);
            Subtask task = new Subtask(title, description, status, epicId);
            task.setType(type);
            return task;
        } else if (fromString[1].equals("EPIC")) {
            List<Integer> subtasksId = new ArrayList<>();
            for (int i = 5; i < fromString.length; i ++) {
                subtasksId.add(Integer.parseInt(fromString[i]));
            }
            Epic task = new Epic(title, description, status);
            task.setSubtasksIds(subtasksId);
            return task;
        } else {
            Task task = new Task(title, description, status);
            task.setId(taskId);
            task.setType(type);
            return task;
        }



    }

    @Override
    public Map<Integer, Task> getTasks() {
        return tasks;
    }

    @Override
    public Integer createNewTask(Task task) {
        task.setId(++idCounter);
        task.setType(findTypeTask(task));
        tasks.put(task.getId(), task);
        TaskManagerStorageService.save(this);
        return idCounter;
    }

    @Override
    public void clearAllTasks() {
        tasks.clear();
        TaskManagerStorageService.save(this);
    }

    @Override
    public Task getTaskById(Integer taskId) {
        return tasks.get(taskId);
    }

    @Override
    public void removeTaskById(Integer taskId) {
        tasks.remove(taskId);
        TaskManagerStorageService.save(this);
    }

    @Override
    public void updateTask(Task task, Integer taskId) {
        tasks.put(taskId, task);
        TaskManagerStorageService.save(this);
    }

    @Override
    public Type findTypeTask(Task task) {
        if (task instanceof Subtask) {
            return Type.SUBTASK;
        } else if (task instanceof Epic) {
            return Type.EPIC;
        } else {
            return Type.TASK;
        }
    }
}
