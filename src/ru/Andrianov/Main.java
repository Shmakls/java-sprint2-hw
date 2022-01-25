package ru.Andrianov;


import ru.Andrianov.data.Epic;
import ru.Andrianov.data.Status;
import ru.Andrianov.data.Subtask;
import ru.Andrianov.data.Task;

public class Main {

    public static void main(String[] args) {

        TaskManager taskManager = new TaskManager();

        taskManager.createNewTask(new Epic("Сделать ремонт",
                                    "Привести квартиру в жилоподный вид",
                                            Status.NEW));

        taskManager.createNewTask(new Subtask("Купить материал",
                                        "Позвонить в магазин и сделать заказ",
                                                Status.NEW,1));

        taskManager.createNewTask(new Subtask("Сделать проект",
                                        "Заказать проект у дизайнера",
                                                Status.IN_PROGRESS, 1));

        taskManager.createNewTask(new Epic("Написать тест-программу",
                                        "но я пока не умею",
                                            Status.NEW));

        taskManager.createNewTask(new Subtask("Попытка состряпать подзадачу 1",
                                            "попытка успешно провалена",
                                            Status.NEW, 4));

        taskManager.createNewTask(new Task("Выкинуть мусор",
                                            "взять пакет и дойти до мусорки",
                                            Status.NEW));

        taskManager.createNewTask(new Task("Заварить дверь соседа",
                                            "А нехер шуметь по ночам",
                                            Status.NEW));

        taskManager.printAllTasks();


        }



}
