package ru.andrianov.hmdata;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class HistoryManagerStorageService {

    public static void save(FileBackedHistoryManager historyManager) {

    }

    public static String readFileContentsOrNull(String filePath) {
        try {
            return Files.readString(Path.of(filePath));
        } catch (IOException e) {
            System.out.println("Невозможно прочитать файл с месячным отчётом. Возможно, файл не находится в нужной директории.");
            return null;
        }
    }

    public static void restore(FileBackedHistoryManager historyManager) {
        try {
            String[] lines = HistoryManagerStorageService.readFileContentsOrNull(historyManager.filePath).split(",");
            for (String line : lines) {
                historyManager.nodeWithId.put(Integer.parseInt(line), null);
            }
        } catch (NullPointerException e) {
            System.out.println("Данные с файла не были считаны");
        }
    }

}
