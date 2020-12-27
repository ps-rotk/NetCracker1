package main;

import javax.management.ObjectName;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Controller {
    private List<Task> listTask;
    private DBLayout layout;
    //метод, который обновляет лист тасков в контроллере
    private void updateListTask(){
        listTask = layout.getAllTasks();
    }
    //проверяет при открытии программы наличие старых тасков
    private void checkOldTask() throws IOException {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        Date date = new Date();
        LinkedList<Task> deletedList = new LinkedList<>();
        if (listTask.size() == 0)
            return;
        else {
            System.out.println("Проверка задач...\n");
            for (Task t : listTask) {
                if (true != t.compareString(dateFormat.format(date))) {
                    System.out.printf("Задача удалена (время исполнения прошло)\t");
                    System.out.println(t.toString());
                    deletedList.add(t);
                }
            }
            for (Task t : deletedList) {
                deleteTask(t.getId());
            }
            System.out.println("Проверка задач завершена!\n");
        }

    }
    //конструктор
    public Controller() throws IOException, ClassNotFoundException {
        layout = new DBLayout();
        listTask = layout.getAllTasks();
        Scheduler scheduler = new Scheduler((LinkedList<Task>) listTask);
        Thread schedulerThread = new Thread(scheduler);
        schedulerThread.start();
        checkOldTask();
    }
    //получение длины листа
    public int getLength(){ return listTask.size();}
    //добавление таска
    public void addTask(Task t) throws IOException {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        Date date = new Date();
        if (true == t.compareString(dateFormat.format(date))){
            layout.addTask(t);
            updateListTask();
        } else {
            System.out.println("Не удалось добавить задачу. Неправильная дата.");
        }
    }
    //получение всех тасков
    public LinkedList<Task> getAllTasks(){
        return (LinkedList<Task>) listTask;
    }
    //изменить таск
    public void updateTask(Task newT) throws IOException {
        if (checkDate(newT.getDate()) == true) {
            layout.updateTask(newT);
            updateListTask();
        }
    }
    //проверка даты
    public boolean checkDate(String date1){
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
    }
    //удалить таск
    public void deleteTask(Integer id) throws IOException {
        Integer[] allId = layout.getAllId();
        boolean flag = false;
        for (Integer i: allId){
            if (i.equals(id)){
                flag = true;
            }
        }
        if (flag == false){
            System.out.println("Невозможно удалить задачу: такого id не существует");
        } else {
            layout.deleteTask(id);
            updateListTask();
        }
    }
    //получить таск по индексу
    public Task getTaskByIndex(int a){
        return listTask.get(a);
    }
    //получить таск по условию (по датам)
    public LinkedList<Task> getTaskByQuery(String date){
        String[] allDates = layout.getAllDate();
        boolean flag = false;

        for (String i: allDates){
            if (i.equals(date)){
                flag = true;
            }
        }
        if (flag == false){
           // System.out.println("Невозможно получить список задач на заданную дату: такой даты нет в списке");
            return null;
        } else {
            return layout.getTaskByQuery(date);
        }
    }
    //получить таск по условию (по строке и по типу)
    public LinkedList<Task> getTaskByQuery(String date, String type){
        String check = date + type;
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
        }
    }
    //получить стринг всех задач
   public String getStringListTasks() {
       String out = "Cписок задач: \n";
       if (listTask.size() == 0) {
           return out = "Cписок задач пуст";
       } else {

           for (Task t : listTask) {
               out += t.toString() + "\n";
           }
           return out;
       }
   }
    //получить стринг переданного листа
    public String getStringListTasks(LinkedList<Task> listTask) {
        String out = "Cписок задач: \n";
        if (listTask == null) {
            return out = "Cписок задач пуст";
        } else {
            for (Task t : listTask) {
                out += t.toString() + "\n";
            }
            return out;
        }
    }
   public Integer setNewId(){
       Random rand = new Random();
       return rand.nextInt(9999 - 1000) + 1000;
   }
   public Task getTaskById(Integer id){
       Integer[] allId = layout.getAllId();
       boolean flag = false;
       for (int i = 0; i < listTask.size(); i++){
           if (id.equals(allId[i])){
               flag = true;
           }
       }
       if (flag == false){
           System.out.println("Не удалось обновить задачу. Такого id не существует");
           return null;
       }
       return layout.getTaskById(id);
   }
}
