package ru.andrianov.data;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class TaskRepositoryStorageService {

    public static void save(FileBackedTasksRepository tasksRepository) {

        String fileTitle = "id,type,name,status,description,startTime,estimationTime,epic\n";
        try (Writer fileWriter = new FileWriter(tasksRepository.filePath)) {
            fileWriter.write(fileTitle);
            for (Integer taskId : tasksRepository.tasks.keySet()) {
                String stringTask = toString(tasksRepository.tasks.get(taskId));
                fileWriter.write(stringTask);
                fileWriter.write("\n");
            }
        } catch (IOException exception) {
            throw new ManagerSaveException("Произошла ошибка во время записи в файл!");
        }
    }

    public static String readFileContentsOrNull(String filePath) {
        try {
            return Files.readString(Path.of(filePath));
        } catch (IOException e) {
            System.out.println("Невозможно прочитать файл. Возможно, файл не находится в нужной директории.");
            return null;
        }
    }

    public static void restore(FileBackedTasksRepository tasksRepository) {

        String line = TaskRepositoryStorageService.readFileContentsOrNull(tasksRepository.filePath);
        if (line != null) {
            String[] lines = line.split("\n");

            for (int i = 1; i < lines.length; i++) {
                Task task = fromString(lines[i]);
                tasksRepository.tasks.put(task.getId(), task);
            }
        } else {
            System.out.println("Файл не был прочитан!");
        }
    }

    public static String toString(Task task) {

        Type type = findTypeTask(task);

        StringBuilder taskString = new StringBuilder((task.getId() + "," + type.currency + ","
                + task.getTitle() + "," + task.getStatus().currency + ","
                + task.getDescription() + "," + task.getStartTime() + "," + task.getEstimationTime().toSeconds()));

        if (task instanceof Subtask) {
            taskString.append(",");
            taskString.append(((Subtask) task).getEpicTaskId());
        }

        if (task instanceof Epic) {
            List<Integer> subtasksId = ((Epic) task).getSubtasksIds();
            taskString.append(",");
            for (int i = 0; i < subtasksId.size(); i++) {
                taskString.append(subtasksId.get(i));
                if (i != subtasksId.size() - 1) {
                    taskString.append(",");
                }
            }
        }
        return taskString.toString();
    }

    public static Task fromString(String value) {

        String[] fromString = value.split(",");
        int taskId = Integer.parseInt(fromString[0]);
        Type type = Type.getTypeByString(fromString[1]);
        String title = fromString[2];
        Status status = Status.getStatusByString(fromString[3]);
        String description = fromString[4];
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(fromString[5]);
        Duration duration = Duration.ofSeconds(Long.parseLong(fromString[6]));

        if (fromString[1].equals("SUBTASK")) {
            int epicId = Integer.parseInt(fromString[7]);
            Subtask task = new Subtask(title, description, status, zonedDateTime, duration, epicId);
            task.setType(type);
            task.setId(taskId);
            return task;
        } else if (fromString[1].equals("EPIC")) {
            List<Integer> subtasksId = new ArrayList<>();
            for (int i = 7; i < fromString.length; i ++) {
                subtasksId.add(Integer.parseInt(fromString[i]));
            }
            Epic task = new Epic(title, description, status);
            task.setSubtasksIds(subtasksId);
            task.setId(taskId);
            task.setType(type);
            return task;
        } else {
            Task task = new Task(title, description, status, zonedDateTime, duration);
            task.setId(taskId);
            task.setType(type);
            return task;
        }



    }

    public static Type findTypeTask(Task task) {
        if (task instanceof Subtask) {
            return Type.SUBTASK;
        } else if (task instanceof Epic) {
            return Type.EPIC;
        } else {
            return Type.TASK;
        }
    }

}
