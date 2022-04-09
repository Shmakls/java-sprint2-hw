package ru.andrianov.hmdata;

import ru.andrianov.data.ManagerSaveException;
import ru.andrianov.data.Task;
import ru.andrianov.data.TaskRepositoryStorageService;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;


public class HistoryRepositoryStorageService {

    public static void save(FileBackedHistoryRepository historyRepository) {

        String fileTitle = "id,type,name,status,description,startTime,estimationTime,epic\n";
        try (Writer fileWriter = new FileWriter(historyRepository.filePath)) {
            fileWriter.write(fileTitle);
            if (historyRepository.getHistory() != null) {
                for (Task task : historyRepository.getHistory()) {
                    String stringTask = TaskRepositoryStorageService.toString(task);
                    fileWriter.write(stringTask);
                    fileWriter.write("\n");
                }
            } else {
                System.out.println("");
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
            System.out.println("Невозможно прочитать файл. Возможно, файл не находится в нужной директории.");
            return null;
        }
    }

    public static void restore(FileBackedHistoryRepository historyRepository) {

        try {
            String[] lines = HistoryRepositoryStorageService.readFileContentsOrNull(historyRepository.filePath).split("\n");
            for (int i = 1; i < lines.length; i++) {
                Task task = TaskRepositoryStorageService.fromString(lines[i]);
                Integer taskId = task.getId();
                if (historyRepository.nodeWithId.containsKey(taskId)) {
                    Node node = historyRepository.nodeWithId.get(taskId);
                    historyRepository.viewedTasks.removeNode(node);
                    historyRepository.viewedTasks.linkLast(task);
                    historyRepository.nodeWithId.remove(taskId);
                } else {
                    Node node = historyRepository.viewedTasks.linkLast(task);
                    historyRepository.nodeWithId.put(taskId, node);
                }
            }
        } catch (NullPointerException e) {
            System.out.println("Данные с файла не были считаны");
        }
    }

}
