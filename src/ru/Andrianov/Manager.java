package ru.Andrianov;

import ru.Andrianov.data.TaskRepository;
import ru.Andrianov.operations.AdditionalMethods;
import ru.Andrianov.operations.EpicStatus;
import ru.Andrianov.operations.TaskCreationAndUpdate;
import ru.Andrianov.operations.TaskOperations;
import java.util.Scanner;

public class Manager {

    int taskID;
    TaskRepository taskCollections;
    TaskCreationAndUpdate taskCreation;
    TaskOperations taskOperations;
    EpicStatus epicStatus;
    AdditionalMethods additionalMethods;

    public Manager() {
        taskCollections = new TaskRepository();
        taskID = 0;
        taskCreation = new TaskCreationAndUpdate();
        taskOperations = new TaskOperations();
        epicStatus = new EpicStatus();
        additionalMethods = new AdditionalMethods();
    }

    public void printWelcomeMessage() {
        System.out.println("");
        System.out.println("Вас приветствует программа \"Трекер задач\"!");
    }

    public void printMenu() {
        System.out.println("Выберите действие:");
        System.out.println("1 - Создать задачу");
        System.out.println("2 - Получение списка всех задач");
        System.out.println("3 - Удаление всех задач");
        System.out.println("4 - Получение информации о задаче по идентификатору");
        System.out.println("5 - Удаление задачи по идентификатору");
        System.out.println("6 - Обновление выбранной задачи");
        System.out.println("7 - Получение списка всех подзадач выбранного epic");
        System.out.println("exit - Выход");
        System.out.println("");
        System.out.print("Ваш выбор: ");
    }

    public void runCommand(String command) {

        Scanner scanner = new Scanner(System.in);
        Integer id = null;

        switch (command) {
            case "1":
                taskID++;
                taskCreation.createNewTask(taskCollections, taskID);
                break;
            case "2":
                taskOperations.getTaskList(taskCollections);
                break;
            case "3":
                taskOperations.clearAllTasks(taskCollections);
                break;
            case "4":
                System.out.print("Введите идентификатор задачи: ");
                id = Integer.parseInt(scanner.next());
                taskOperations.getTaskByID(taskCollections, id);
                break;
            case "5":
                System.out.print("Введите идентификатор задачи: ");
                id = Integer.parseInt(scanner.next());
                taskOperations.removeTaskByID(taskCollections, id);
                break;
            case "6":
                System.out.print("Введите идентификатор задачи, которую нужно заменить: ");
                Integer replaceID = Integer.parseInt(scanner.next());
                taskCreation.createNewTask(taskCollections, replaceID);
                break;
            case "7":
                System.out.print("Введите идентификатор epic задачи: ");
                Integer epicTask = Integer.parseInt(scanner.next());
                additionalMethods.getSubtaskListByEpic(taskCollections, epicTask);
                break;
            default:
                System.out.println("Извините, такой команды пока нет");
                break;

        }

    }

}
