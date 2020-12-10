package main;

import java.util.Scanner;

public class View {
    public View() {
        start();
    }

    public void printMenu() {
        System.out.println("Меню");
        System.out.println("1) Посмотреть список задач");
        System.out.println("2) Добавить новую задачу");
        System.out.println("3) Изменить данные задачи");
        System.out.println("4) Найти задачи по дате");
        System.out.println("5) Найти задачи по дате и типу");
        System.out.println("6) Удалить задачу");
        System.out.println("7) Выход");
    }

    public void updateMenu() {
        for (int i = 0; i < 10; i++) {
            System.out.println();
        }
    }

    public void start() {
        Scanner in = new Scanner(System.in);
        boolean stop = false;
        int number;
        boolean checkB = false;
        while (!stop) {
            printMenu();
            if (checkB){
                System.out.println("Неверное число, повторите ввод");
            }
            System.out.println("Введите пункт меню тест");
            number = in.nextInt();
            switch (number) {
                case 1:
                    checkB = false;
                    break;
                case 2:
                    checkB = false;
                    break;
                case 3:
                    checkB = false;
                    break;
                case 4:
                    checkB = false;
                    break;
                case 5:
                    checkB = false;
                    break;
                case 6:
                    checkB = false;
                    break;
                case 7:
                    checkB = false;
                    stop = true;
                    break;
                default:
                    checkB = true;
                    break;
            }
            updateMenu();
        }
    }
}
