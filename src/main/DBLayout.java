package main;

import java.io.*;
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DBLayout {

    private List<Task> listTask;
///////////
    //Заполнение конструктора
    private LinkedList<Task> getAllTasksConstr() throws IOException, ClassNotFoundException {

        File checkExist = new File("Tasks.dat");
        if (checkExist.exists() == true) {
            if (checkExist.length() == 0){
                System.out.println("Файл пуст");
                return new LinkedList<Task>();
            }else {
                ObjectInputStream in = new ObjectInputStream(new FileInputStream("Tasks.dat"));
                return DBLayout.deserializeListTask(in);
            }
        } else {
            System.out.println("Файла Tasks.dat не существует на данном устройстве. \nФайл Tasks.dat был создан.");
            checkExist.createNewFile();
            return new LinkedList<Task>();
        }
    }
    //сортировка листа по дате
    private void sortList(){
        listTask.sort(new Comparator<Task>() {
            @Override
            public int compare(Task o1, Task o2) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
                Calendar calendar = Calendar.getInstance();
                try {
                    calendar.setTime(dateFormat.parse(o1.getDate()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Calendar calendar1 = Calendar.getInstance();
                try {
                    calendar1.setTime(dateFormat.parse(o2.getDate()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return calendar.compareTo(calendar1);
            }
        });
    }

    //сериализация
    public static void serializeListTask (LinkedList<Task> listTask, OutputStream out) throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(out);
        objectOutputStream.writeObject(listTask);
        objectOutputStream.close();
    }
    //десериализация
    public static LinkedList<Task> deserializeListTask(ObjectInputStream in) throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(in);
        return (LinkedList<Task>) objectInputStream.readObject();
    }
    //сохранение листа в файл после каждого изменения
    private void saveListTask() throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("Tasks.dat"));
        serializeListTask((LinkedList<Task>) listTask, out);
        out.close();
    }

////////////

    //конструктор
    public DBLayout() throws IOException, ClassNotFoundException {
        listTask = getAllTasksConstr();
    }
    //передача всех задач в контроллер
    public LinkedList<Task> getAllTasks(){
        return (LinkedList<Task>) listTask;
    }
    //добавление задачи в список
    public void addTask(Task task) throws IOException {
        listTask.add(task);
        sortList();
        saveListTask();
    }
    //удаление задачи
    public void deleteTask(Integer id) throws IOException {
        Iterator<Task> i = listTask.iterator();
        Task temp;
        while (i.hasNext()){
            temp = i.next();
            if (temp.getId().equals(id)){
                i.remove();
            }
        }
        saveListTask();
    }
    //получить длину листа
    public int getLength(){ return listTask.size();}
    //обновить таск
    public void updateTask(Task newT) throws IOException {
        for (Task i: listTask){
            if (i.getId().equals(newT.getId())){
                i.setDate(newT.getDate());
                i.setText(newT.getText());
                i.setType(newT.getType());
            }
        }
        sortList();
        saveListTask();
    }
    //получить список по задач по определённой дате
    public LinkedList<Task> getTaskByQuery(String date){
        List<Task> newTasks = new LinkedList<Task>();
        for (Task i: listTask){
            if (i.getDate().substring(0, 10).equals(date))
                newTasks.add(i);
        }
        return (LinkedList<Task>) newTasks;
    }
    //получить список задач по дате и типу
    public LinkedList<Task> getTaskByQuery(String date, String type){
        LinkedList<Task> newTasks = new LinkedList<Task>();
        for (Task i: listTask){
            if (i.getDate().substring(0, 10).equals(date) && i.getType().equals(type))
                newTasks.add(i);
        }
        return newTasks;
    }
    //возвращает массив id для проверки в контроллере
    public Integer[] getAllId(){
        Integer[] allId = new Integer[listTask.size()];
        for (int i = 0; i < listTask.size(); i++){
            allId[i] = listTask.get(i).getId();
        }
        return allId;
    }
    //возвращает массив дат для проверки в контроллере
    public String[] getAllDate(){
        String[] allDates = new String[listTask.size()];

        for (int i = 0; i < listTask.size(); i++){
           allDates[i] = listTask.get(i).getDate().substring(0, 10);
        }
        return allDates;
    }
    //вернуть конкантенацию строк даты и типа для проверки в контроллере
    public String[] getAllDateType(){
        String[] allDatesType = new String[listTask.size()];
        Task temp;
        for (int i = 0; i < listTask.size(); i++){
            temp = listTask.get(i);
            allDatesType[i] = temp.getDate().substring(0,10) + temp.getType();
        }
        return allDatesType;
    }

    public Task getTaskById(Integer id){
        for (Task i : listTask){
            if (id.equals(i.getId()))
                return i;
        }
        return null;
    }

}
