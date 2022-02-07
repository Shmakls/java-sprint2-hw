package ru.andrianov;


import ru.andrianov.data.Epic;
import ru.andrianov.data.Status;
import ru.andrianov.data.Subtask;
import ru.andrianov.data.Task;

public class Main {

    public static void main(String[] args) {

        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();

        test1(inMemoryTaskManager);

        inMemoryTaskManager.printAllTasks();

        test2(inMemoryTaskManager);

        inMemoryTaskManager.printAllTasks();

        inMemoryTaskManager.removeTaskById(7);
        inMemoryTaskManager.removeTaskById(1);

        inMemoryTaskManager.printAllTasks();

        inMemoryTaskManager.getSubtaskListByEpic(4);

        inMemoryTaskManager.clearAllTasks();

        inMemoryTaskManager.printAllTasks();




        }

        public static void test1(InMemoryTaskManager inMemoryTaskManager) {
            inMemoryTaskManager.createNewTask(new Epic("Сделать ремонт",
                    "Привести квартиру в жилоподный вид",
                    Status.NEW));

            inMemoryTaskManager.createNewTask(new Subtask("Купить материал",
                    "Позвонить в магазин и сделать заказ",
                    Status.NEW,1));

            inMemoryTaskManager.createNewTask(new Subtask("Сделать проект",
                    "Заказать проект у дизайнера",
                    Status.IN_PROGRESS, 1));

            inMemoryTaskManager.createNewTask(new Epic("Написать тест-программу",
                    "но я пока не умею",
                    Status.NEW));

            inMemoryTaskManager.createNewTask(new Subtask("Попытка состряпать подзадачу 1",
                    "попытка успешно провалена",
                    Status.NEW, 4));

            inMemoryTaskManager.createNewTask(new Task("Выкинуть мусор",
                    "взять пакет и дойти до мусорки",
                    Status.NEW));

            inMemoryTaskManager.createNewTask(new Task("Заварить дверь соседа",
                    "А нехер шуметь по ночам",
                    Status.NEW));
        }

        public static void test2(InMemoryTaskManager inMemoryTaskManager) {

            inMemoryTaskManager.updateTask(new Subtask("Купить материал",
                    "Позвонить в магазин и сделать заказ",
                    Status.DONE,1), 2);

            inMemoryTaskManager.updateTask(new Subtask("Сделать проект",
                    "Заказать проект у дизайнера",
                    Status.DONE, 1), 3);

            inMemoryTaskManager.updateTask(new Subtask("Попытка состряпать подзадачу 1",
                    "попытка успешно провалена",
                    Status.IN_PROGRESS, 4), 5);

            inMemoryTaskManager.updateTask(new Task("Заварить дверь соседа",
                    "А нехер шуметь по ночам",
                    Status.DONE), 7);

        }

}
