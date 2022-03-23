package ru.andrianov.data;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

public class TaskManagerStorageService {

    public static void save(FileBackedTasksRepository tasksRepository) {

        String fileTitle = "id,type,name,status,description,epic\n";
        try (Writer fileWriter = new FileWriter(tasksRepository.filePath)) {
            fileWriter.write(fileTitle);
            for (Integer taskId : tasksRepository.tasks.keySet()) {
                String stringTask = tasksRepository.toString(tasksRepository.tasks.get(taskId));
                fileWriter.write(stringTask);
            }
        } catch (IOException exception) {
            try {
                throw new ManagerSaveException("Произошла ошибка во время записи в файл!");
            } catch (ManagerSaveException mse) {
                mse.printStackTrace();
            }
        }

    }

    public static String readFileContentsOrNull(String filePath) {
        try {
            return Files.readString(Path.of(filePath));
        } catch (IOException e) {
            System.out.println("Невозможно прочитать файл с месячным отчётом. Возможно, файл не находится в нужной директории.");
            return null;
        }
    }

    public static void restore(FileBackedTasksRepository tasksRepository) {

        try {
            String[] lines = TaskManagerStorageService.readFileContentsOrNull(tasksRepository.filePath).split("\r\n");
            for (int i = 1; i < lines.length; i++) {
                Task task = tasksRepository.fromString(lines[i]);
                tasksRepository.tasks.put(task.getId(), task);
            }
        } catch (NullPointerException e) {
            System.out.println("Данные с файла не были считаны");
        }
    }

}
