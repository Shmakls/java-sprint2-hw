package ru.andrianov.hmdata;

import ru.andrianov.data.ManagerSaveException;
import ru.andrianov.data.Task;
import ru.andrianov.data.TaskRepositoryStorageService;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;


public class HistoryManagerStorageService {

    public static void save(FileBackedHistoryManager historyManager) {

        String fileTitle = "id,type,name,status,description,epic\n";
        try (Writer fileWriter = new FileWriter(historyManager.filePath)) {
            fileWriter.write(fileTitle);
            if (historyManager.getHistory() != null) {
                for (Task task : historyManager.getHistory()) {
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

    public static void restore(FileBackedHistoryManager historyManager) {

        try {
            String[] lines = HistoryManagerStorageService.readFileContentsOrNull(historyManager.filePath).split("\n");
            for (int i = 1; i < lines.length; i++) {
                Task task = TaskRepositoryStorageService.fromString(lines[i]);
                Integer taskId = task.getId();
                if (historyManager.nodeWithId.containsKey(taskId)) {
                    Node node = historyManager.nodeWithId.get(taskId);
                    historyManager.viewedTasks.removeNode(node);
                    historyManager.viewedTasks.linkLast(task);
                    historyManager.nodeWithId.remove(taskId);
                } else {
                    Node node = historyManager.viewedTasks.linkLast(task);
                    historyManager.nodeWithId.put(taskId, node);
                }
            }
        } catch (NullPointerException e) {
            System.out.println("Данные с файла не были считаны");
        }
    }

}