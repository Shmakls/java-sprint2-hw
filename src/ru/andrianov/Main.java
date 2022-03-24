package ru.andrianov;


import ru.andrianov.data.*;

public class Main {

    public static void main(String[] args) {

    /*
    Здравствуйте! Сразу скажу, что тут много отклонений от ТЗ, т.к. у меня независимая реализация истории и репозитория.
    Постараюсь вкратце изложить, чтобы удалось быстрее понять как это работает:
    Класс TaskManager, как и задумывалось вначале, стал независимым от типа базы данных.
    Класс FileBacketTaskRepository имплементирует интерфейс TaskRepository (хотя в ТЗ рекомендовали унаследовать от
    InMemoryTaskRepository), но для универсальности я решил так не делать. Для записи/чтения в файл и конвертации данных
    в строку я сделал класс TaskRepositoryStorageService.
    Аналогично с HistoryRepository (Бывший HistoryManager).
    По тестам чтобы заполнить файлы .csv надо выполнить п. 1 - заполнение (см. ниже), потом закоментить п. 1 и раскоментить
    п. 2 чтобы выполнить проверку восстановления из файла.

    п.с. Дополнительно переопределил методы toString() для Task, Epic и Subtask, чтобы убрать из TaskManager форматирование
    текста для вывода задач.
    Надеюсь Вы не сильно запутаетесь в моей самодеятельности))))
    Заранее благодарен.
    */

        // 1. Заполнение менеджера и провека его работы

        /* TaskManager taskManager = Managers.getTaskManager();

        test1(taskManager);                             // Создаём 7 новых задач.

        taskManager.printAllTasks();                    // печатаем список

        test2(taskManager);                             // обновляем задачи

        taskManager.printAllTasks();                    // печатаем список и проверям что обновилось

        Task task = taskManager.getTaskById(2);   // вызываем поочереди 3 задачи для проверки попадания в историю
        task = taskManager.getTaskById(7);
        task = taskManager.getTaskById(1);
        task = taskManager.getTaskById(6);

        System.out.println("");                         //просто для красоты вывода

        taskManager.printHistory();                     //проверяем что задачки попали в историю

        task = taskManager.getTaskById(2);        //повторно вызываем задачу 2, чтобы она удалилась из списка
                                                        //просмотров и переехала в конец списка

        taskManager.printHistory();

        taskManager.removeTaskById(7);            // удаляем задачку
        taskManager.removeTaskById(1);            // удаляем задачку

        taskManager.printAllTasks();                    // печатаем задачи

        taskManager.printHistory();                     // проверяем что часть задачек из истории улетела


        taskManager.getSubtaskListByEpic(4); */

        // конец пункта 1.


        // эти операции были для прошлых тестов, они проверяли работу очистки, пока их оставил.

        // taskManager.clearAllTasks();                    // чистим задачник

        // taskManager.printAllTasks();                    //проверяем что почистилось

        // taskManager.printHistory();                     //проверям что история тоже почистилась


            // 2. Проверка восстановления

        TaskManager taskManager = Managers.getTaskManager();

        taskManager.printAllTasks();
        taskManager.printHistory();

        // Конец пункта 2.

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
