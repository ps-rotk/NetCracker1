package main;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class View {
    private Controller controller;
    public View() throws IOException, ClassNotFoundException {
         controller = new Controller();
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

    /*public void updateMenu() {
        for (int i = 0; i < 10; i++) {
            System.out.println();
        }
    }*/

    public void start() throws IOException {
        Scanner in = new Scanner(System.in);
        boolean stop = false;
        int number;
        boolean checkB = false;
        while (!stop) {
            printMenu();
            if (checkB){
                System.out.println("Неверное число, повторите ввод");
            }
            System.out.println("Введите пункт меню");
            number = in.nextInt();
            switch (number) {
                case 1:
                    printListTask();
                    checkB = false;
                    break;
                case 2:
                    checkB = false;
                    addTask();
                    break;
                case 3:
                    checkB = false;
                    updateTask();
                    break;
                case 4:
                    checkB = false;
                    getTaskByQueryDate();
                    break;
                case 5:
                    getTaskByQueryDateType();
                    checkB = false;
                    break;
                case 6:
                    deleteTaskById();
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
        }
    }

    //1
    public void printListTask(){
        System.out.println(controller.getStringListTasks());
    }
    //2
    public void addTask() throws IOException {
        System.out.println("Введите дату (dd.MM.yyyy HH:mm)");
        Scanner in = new Scanner(System.in);
        String date = in.nextLine();
        if (controller.checkDate(date) == false){ return; }
        System.out.println("Введите тип задачи");
        String type = in.nextLine();
        System.out.println("Введите заметку");
        String text = in.nextLine();
        Integer id = controller.setNewId();
        Task newTask = new Task(id, date, type, text);
        controller.addTask(newTask);
    }
    //3
    public void updateTask() throws IOException {
        printListTask();
        System.out.println("Введите ID задачи");
        Scanner in = new Scanner(System.in);
        Integer id = in.nextInt();
        Task temp = controller.getTaskById(id);
        if (temp == null)
            return;
        System.out.println("Введите дату (dd.MM.yyyy HH:mm)");
        Scanner on = new Scanner(System.in);
        String date = on.nextLine();
        if (controller.checkDate(date) == false){
            return;
        }
        System.out.println("Введите тип задачи");
        String type = on.nextLine();
        System.out.println("Введите заметку");
        String text = on.nextLine();
        controller.updateTask(new Task(id, date, type, text));
    }
    //4
    public void getTaskByQueryDate(){
        System.out.println("Введите дату (dd.MM.yyyy)");
        Scanner in = new Scanner(System.in);
        String date = in.nextLine();
        System.out.println(controller.getStringListTasks(controller.getTaskByQuery(date)));
    }
    //5
    public void getTaskByQueryDateType(){
        System.out.println("Введите дату (dd.MM.yyyy)");
        Scanner in = new Scanner(System.in);
        String date = in.nextLine();
        if (controller.checkDate(date) == false)
            return;
        System.out.println("Введите тип задачи");
        String type = in.nextLine();
        System.out.println(controller.getStringListTasks(controller.getTaskByQuery(date, type)));
    }
    //6
    public void deleteTaskById() throws IOException {
        printListTask();
        System.out.println("Введите id задачи, которую хотите удалить");
        Scanner in = new Scanner(System.in);
        Integer id = in.nextInt();
        controller.deleteTask(id);
    }
}
