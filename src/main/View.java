package main;


import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

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

    public void start() throws IOException {
        Map<Integer, Task> deleteMap = controller.checkOldTask();
        if (deleteMap != null && deleteMap.size() != 0) {
            System.out.println("Желаете удалить из списка задачи с прошедшим временем выполнения? Y для удаления:");
            Scanner dl = new Scanner(System.in);
            String del = dl.nextLine();
            if (del.equals("Y")) {
                System.out.println("Вы выбрали удаление прошедших задач. \nУдаляю...");
                for (Map.Entry<Integer, Task> integerTaskEntry : deleteMap.entrySet()) {
                    Task t = integerTaskEntry.getValue();
                    controller.deleteTaskById(t.getId());
                    System.out.println("Удаление Выполнено успешно");
                }
            } else {
                System.out.println("Вы выбрали не удалять");
            }
        }
        else { System.out.println("Устареших задач не обнаружено");}

        Scanner in = new Scanner(System.in);
        boolean stop = false;
        int number;
        while (!stop) {
            printMenu();
            System.out.println("Введите пункт меню");
            String numberIn = in.nextLine();
            try {
                number = Integer.parseInt(numberIn);
                switch (number) {
                    case 1:
                        printListTask();
                        break;
                    case 2:
                        addTask();
                        break;
                    case 3:
                        updateTask();
                        break;
                    case 4:
                        getTaskByDate();
                        break;
                    case 5:
                        getTaskByDateType();
                        break;
                    case 6:
                        deleteTaskById();
                        break;
                    case 7:
                        stop = true;
                        controller.exit();
                        break;
                    default:
                        System.out.println("Неверное число, повторите ввод");
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println();
                System.out.println("Что-то пошло не так. Введите число, равное пункту меню.");
            }
        }
    }

    //1
    public void printListTask() {
        System.out.println(controller.getStringListTasks());
        System.out.println();
    }

    //2
    public void addTask() throws IOException {
        LocalDate localDate = checkDate();
        LocalTime localTime = checkTime();
        if (localDate == null || localTime == null) {
            return;
        }
        LocalDateTime date = LocalDateTime.of(localDate, localTime);
        System.out.println("Введите тип задачи");
        Scanner in = new Scanner(System.in);
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
        LocalDate localDate = checkDate();
        LocalTime localTime = checkTime();
        if (localDate == null || localTime == null) {
            return;
        }
        LocalDateTime date = LocalDateTime.of(localDate, localTime);
        System.out.println("Введите тип задачи");
        String type = in.nextLine();
        System.out.println("Введите заметку");
        String text = in.nextLine();
        controller.updateTask(new Task(id, date, type, text));
        System.out.println("Задача обновлена");
    }

    //4
    public void getTaskByDate() {
        LocalDate localDate = checkDate();
        if (localDate == null) {
            return;
        }
        LocalDate date = LocalDate.of(localDate.getYear(), localDate.getMonthValue(), localDate.getDayOfMonth());
        System.out.println(controller.getStringListTasks(controller.getTaskByDate(date)));

    }

    //5
    public void getTaskByDateType() {
        LocalDate localDate = checkDate();
        if (localDate == null) {
            return;
        }
        LocalDate date = LocalDate.of(localDate.getYear(), localDate.getMonthValue(), localDate.getDayOfMonth());
        System.out.println("Введите тип задачи");
        Scanner in = new Scanner(System.in);
        String type = in.nextLine();
        System.out.println(controller.getStringListTasks(controller.getTaskByQuery(date, type)));
        System.out.println();


    }

    //6
    public void deleteTaskById() throws IOException {
        printListTask();
        System.out.println("Введите id задачи, которую хотите удалить");
        Scanner in = new Scanner(System.in);
        Integer id = in.nextInt();
        controller.deleteTaskById(id);
        System.out.println("Задача удалена");
    }

    private LocalDate checkDate() {
        boolean stop = false;
        while (!stop) {
            System.out.println("Введите дату в формате dd.MM.yyyy");
            Scanner in = new Scanner(System.in);
            String dateIn = in.nextLine();
            if (dateIn.equals("Y")) {
                stop = true;
            }
            String[] date1 = dateIn.split("\\.");
            int[] date = new int[3];
            for (int i = 0; i < date1.length; i++) {
                date[i] = Integer.parseInt(date1[i]);
            }
            LocalDate localDate = LocalDate.of(date[2], date[1], date[0]);
            if (localDate.isAfter(LocalDate.now()) || localDate.isEqual(LocalDate.now())) {
                stop = true;
                return localDate;
            } else {
                System.out.println("Не удалось выполнить операцию. Неправильная дата. \nПовторите ввод или введите Y для выхода");
            }
        }
        return null;
    }

    private LocalTime checkTime() {
        System.out.println("Введите время в формате HH:mm");
        Scanner in = new Scanner(System.in);
        String timeIn = in.nextLine();
        String[] time1 = timeIn.split(":");
        int[] time = new int[2];
        for (int i = 0; i < time1.length; i++) {
            time[i] = Integer.parseInt(time1[i]);
        }
        return LocalTime.of(time[0], time[1]);

    }
}
