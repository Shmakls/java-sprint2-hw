package ru.andrianov;


import ru.andrianov.data.Epic;
import ru.andrianov.data.Status;
import ru.andrianov.data.Subtask;
import ru.andrianov.data.Task;

public class Main {

    public static void main(String[] args) {

        TaskManager taskManager = new TaskManager();

        test1(taskManager);

        taskManager.printAllTasks();

        test2(taskManager);

        taskManager.printAllTasks();

        taskManager.removeTaskById(7);
        taskManager.removeTaskById(1);

        taskManager.printAllTasks();

        taskManager.getSubtaskListByEpic(4);

        taskManager.clearAllTasks();

        taskManager.printAllTasks();




        }

        public static void test1(TaskManager taskManager) {
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
        }

        public static void test2(TaskManager taskManager) {

            taskManager.updateTask(new Subtask("Купить материал",
                    "Позвонить в магазин и сделать заказ",
                    Status.DONE,1), 2);

            taskManager.updateTask(new Subtask("Сделать проект",
                    "Заказать проект у дизайнера",
                    Status.DONE, 1), 3);

            taskManager.updateTask(new Subtask("Попытка состряпать подзадачу 1",
                    "попытка успешно провалена",
                    Status.IN_PROGRESS, 4), 5);

            taskManager.updateTask(new Task("Заварить дверь соседа",
                    "А нехер шуметь по ночам",
                    Status.DONE), 7);

        }

}
