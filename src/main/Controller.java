package main;

import javax.management.ObjectName;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class Controller {
    private Map<Integer, Task> mapTask;//TODO: Убрать
    private DBLayout layout;
    private Scheduler scheduler;

    //метод, который обновляет лист тасков в контроллере
    private void updateListTask() {
        mapTask = layout.getAllTasks();
    }

    //проверяет при открытии программы наличие старых тасков
    public Map<Integer, Task> checkOldTask() throws IOException {
        LocalDateTime date = LocalDateTime.now();
        Map<Integer, Task> deletedList = new HashMap<Integer, Task>();
        if (mapTask.size() == 0)
            return null;
        else {
            System.out.println("Проверка задач...\n");
            for (Map.Entry<Integer, Task> integerTaskEntry : mapTask.entrySet()) {
                Task t = integerTaskEntry.getValue();
                if (t.getDate().isBefore(date)) {
                    System.out.print("Время исполнения следуюших задач прошло: \n");
                    System.out.println(t.toString());
                    deletedList.put(t.getId(), t);
                }
            }
        }
        return deletedList;
    }

    //конструктор
    public Controller() throws IOException, ClassNotFoundException {
        layout = new DBLayout();
        mapTask = layout.getAllTasks();
        scheduler = new Scheduler(mapTask);
        Thread schedulerThread = new Thread(scheduler);
        schedulerThread.start();
    }

    //получение длины листа
    public int getLength() {
        return mapTask.size();
    }

    //добавление таска
    public void addTask(Task t) throws IOException {
        layout.addTask(t);
        updateListTask();
    }

    //получение всех тасков
    public Map<Integer, Task> getAllTasks() {
        return mapTask;
    }

    //изменить таск
    public void updateTask(Task newT) throws IOException {
        if (newT.getDate().isAfter(LocalDateTime.now())) {
            layout.updateTask(newT);
            updateListTask();
        }
    }

    //проверка даты
   /* public boolean checkDate(String date1){
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        Date date = new Date();
        int dd;int MM;int yyyy;int HH;int mm;
        try {
            dd = Integer.parseInt(date1.substring(0, 2));
            MM = Integer.parseInt(date1.substring(3,5));
            yyyy = Integer.parseInt(date1.substring(6, 10));
            HH = Integer.parseInt(date1.substring(11, 13));
            mm = Integer.parseInt(date1.substring(14));
        }catch (NumberFormatException nfe){
            System.out.println("Ошибка. Неправильная дата.");
            return false;
        }
        if (dd > 31 || (dd > 30 && (MM == 2 || MM == 4 || MM == 6 || MM == 9
        || MM == 11)) || MM > 12 || dd < 1 || MM < 1 ||  yyyy < 1 || HH > 23
        || HH < 0 || mm > 59 || mm < 0) {
            System.out.println("Ошибка. Неправильная дата.");
            return false;
        }
        Task newT = new Task(0, date1, null, null);
        if (true == newT.compareString(dateFormat.format(date))){
            return true;
        } else {
            System.out.println("Ошибка. Неправильная дата.");
            return false;
        }
    }*/
    //удалить таск
    public void deleteTaskById(Integer id) throws IOException {
        Integer[] allId = layout.getAllId();
        boolean flag = false;
        for (Integer i : allId) {
            if (i.equals(id)) {
                flag = true;
            }
        }
        if (!flag) {
            System.out.println("Невозможно удалить задачу: такого id не существует");
        } else {
            layout.deleteTask(id);
            updateListTask();
        }
    }

    //получить таск по id
    public Task getTaskByID(int a) {
        return mapTask.get(a);
    }

    //получить мапу таск по условию (по датам)
    public Map<Integer, Task> getTaskByDate(LocalDate date) {
       /* Date[] allDates = layout.getAllDate();
        boolean flag = false;

        for (Date i: allDates){
            if (i.equals(date)){
                flag = true;
            }
        }
        if (flag == false){
           // System.out.println("Невозможно получить список задач на заданную дату: такой даты нет в списке");
            return null;
        } else {
            return layout.getTaskByQuery(date);
        }*/
        return layout.getTaskByDate(date);
    }

    //получить таск по условию (по строке и по типу)
    public Map<Integer, Task> getTaskByQuery(LocalDate date, String type) {
        /*String check = date + type;
        String[] allDatesType = layout.getAllDateType();
        boolean flag = false;

        for (String i: allDatesType){
            if (i.equals(check)){
                flag = true;
            }
        }
        if (flag == false){
            //System.out.println("Невозможно получить список задач по типу и дате: таких задач в списке нет");
            return null;
        } else {
            return layout.getTaskByQuery(date, type);
        }*/
        return layout.getTaskByDateAndType(date, type);

    }

    //получить стринг всех задач
    public String getStringListTasks() {
        String out;
        if (mapTask.size() == 0) {
            return out = "Cписок задач пуст";
        }
        out = "Cписок задач: \n";

        for (Map.Entry<Integer, Task> integerTaskEntry : mapTask.entrySet()) {
            Task t = integerTaskEntry.getValue();
            out += t.toString() + "\n";
        }
        return out;
    }

    //получить стринг переданного листа
    public String getStringListTasks(Map<Integer, Task> mapTask) {
        String out;
        if (mapTask == null) {
            return out = "По данным критериям ничего не найдено";
        }
        out = "Cписок задач: \n";

        for (Map.Entry<Integer, Task> integerTaskEntry : mapTask.entrySet()) {
            Task t = integerTaskEntry.getValue();
            out += t.toString() + "\n";
        }
        return out;
    }

    public Integer setNewId() {
        /*return mapTask.size() + 1; /*/
        Random rand = new Random();
        return rand.nextInt(9999 - 1000) + 1000;
    }

    public Task getTaskById(Integer id) {
        // Integer[] allId = layout.getAllId();
        boolean flag = false;
        for (Map.Entry<Integer, Task> integerTaskEntry : mapTask.entrySet()) {
            Task t = integerTaskEntry.getValue();
            if (id.equals(t.getId())) {
                flag = true;
                break;
            }
        }
        if (!flag) {
            System.out.println("Не удалось обновить задачу. Такого id не существует");
            return null;
        }//TODO: убрать лишнее и все коменты
        return layout.getTaskById(id);
    }

    public void exit() {
        scheduler.setStop(true);
    }
}
