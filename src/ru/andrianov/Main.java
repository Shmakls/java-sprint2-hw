package ru.andrianov;


import ru.andrianov.data.*;

public class Main {

    public static void main(String[] args) {

        /*
        Спасибо за предложение, но я пока не совсем понимаю как правильно(.
        Я еще прошлый раз попробовал переделать типы коллекций и списков, но столкнулся с проблемой что надо делать
        приведение типов. Подскажите, пожалуйста, как правильно делать.

        За похвалу по связанному списку спасибо, но честно скажу я передрал методы из интернета, немного подправив под себя.
        Да и сама реализация в классе InMemoryHistoryManager мне не понравилась, до сих пор чешутся руки вынести это
        в отдельный класс, не совсем понимаю зачем жесткое требование в ТЗ что он должен именно в том классе.

        По части последнего вебинара я понял что в данном случае и не надо было двусвязный список делать, достаточно было
        односвязного. Так ли это и имеет смысл переделать?

        Заранее спасибо, извините что сразу не написал вопросы и предложения в первой итерации.
         */

        TaskManager taskManager = Managers.getTaskManager();

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


        taskManager.getSubtaskListByEpic(4);

        taskManager.clearAllTasks();                    // чистим задачник

        taskManager.printAllTasks();                    //проверяем что почистилось

        taskManager.printHistory();                     //проверям что история тоже почистилась

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
