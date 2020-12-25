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
    //конструктор
    public Controller() throws IOException, ClassNotFoundException {
        layout = new DBLayout();
        listTask = layout.getAllTasks();
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
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        Date date = new Date();
        if (true == newT.compareString(dateFormat.format(date))){
            layout.updateTask(newT);
            updateListTask();
        } else {
            System.out.println("Не удалось обновить задачу. Неправильная дата.");
        }
    }

    public boolean checkDate(String date1){
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        Date date = new Date();
        Task newT = new Task(0, date1, null, null);
        if (true == newT.compareString(dateFormat.format(date))){
            return true;
        } else {
            System.out.println("Не удалось обновить задачу. Неправильная дата.");
            return false;
        }
    }

    //удалить таск
    public void deleteTask(Integer id) throws IOException {
        Integer[] allId = layout.getAllId();
        boolean flag = false;
        for (Integer i: allId){
            if (i == id){
                flag = true;
            }
        }
        if (flag == false){
            System.out.println("Невозможно удалить файл: такого id не существует");
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
            System.out.println("Невозможно получить список задач на заданную дату: такой даты нет в списке");
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
            if (i == check){
                flag = true;
            }
        }
        if (flag == false){
            System.out.println("Невозможно получить список задач по типу и дате: таких задач в списке нет");
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
        ///////костыль
        if (listTask.size() == 1 && listTask.get(0).getId() == -1) {
            return out = "Cписок задач пуст";
            /////////
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
