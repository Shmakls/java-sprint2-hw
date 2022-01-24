package ru.Andrianov;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Manager manager = new Manager();

        manager.printWelcomeMessage();

        while (true) {
            manager.printMenu();
            String command = scanner.next();
            if (command.equalsIgnoreCase("exit")) {
                System.out.println("Завершаю программу. До свидания.");
                return;
            }
            manager.runCommand(command);
        }

    }

}
