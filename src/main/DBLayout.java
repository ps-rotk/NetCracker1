package main;

import java.io.*;
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.*;

public class DBLayout implements Serializable {
    //TODO: сохранять при выходе
    //TODO: передалть в map; ready
    private Map<Integer, Task> mapTask;

    ///////////
    //Заполнение конструктора
    private Map<Integer, Task> getAllTasksConstr() throws IOException, ClassNotFoundException {

        File checkExist = new File("Tasks.dat");
        if (checkExist.exists()) {
            if (checkExist.length() == 0) {
                System.out.println("Файл пуст");
                return new HashMap<Integer, Task>();
            } else {
                ObjectInputStream in = new ObjectInputStream(new FileInputStream("Tasks.dat"));
                return DBLayout.deserializeListTask(in);
            }
        } else {
            System.out.println("Файла Tasks.dat не существует на данном устройстве. \nФайл Tasks.dat был создан.");
            checkExist.createNewFile();
            return new HashMap<Integer, Task>();
        }
    }
    //сортировка листа по дате
    /*private void sortMap(){ // смысл сортировать...
        mapTask.(new Comparator<Task>() {
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
    }*/

    //сериализация
    private static void serializeListTask(Map<Integer, Task> mapTask, OutputStream out) throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(out);
        objectOutputStream.writeObject(mapTask);
        objectOutputStream.close();
    }

    //десериализация
    private static Map<Integer, Task> deserializeListTask(ObjectInputStream in) throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(in);
        return (Map<Integer, Task>) objectInputStream.readObject();
    }

    //сохранение листа в файл после каждого изменения
    private void saveListTask() throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("Tasks.dat"));
        serializeListTask(mapTask, out);
        out.close();
    }

////////////

    //конструктор
    public DBLayout() throws IOException, ClassNotFoundException {
        mapTask = getAllTasksConstr();
    }

    //передача всех задач в контроллер
    public Map<Integer, Task> getAllTasks() {
        return mapTask;
    }

    //добавление задачи в список
    public void addTask(Task task) throws IOException {
        mapTask.put(task.getId(), task);
        // sortList();
        saveListTask();
    }

    //удаление задачи
    public void deleteTask(Integer id) throws IOException {
        /*Iterator<Task> i = mapTask.iterator();
        Task temp;
        while (i.hasNext()){
            temp = i.next();
            if (temp.getId().equals(id)){
                i.remove();
            }
        }*/
        mapTask.remove(id);
        saveListTask();
    }

    //получить размер мапы
    public int getLength() {
        return mapTask.size();
    }

    //обновить таск
    public void updateTask(Task newT) throws IOException {
        /*for (Task i: listTask){
            if (i.getId().equals(newT.getId())){
                i.setDate(newT.getDate());
                i.setText(newT.getText());
                i.setType(newT.getType());
            }
        }
        sortList();
        */
        mapTask.replace(newT.getId(), newT);
        saveListTask();
    }

    //получить мапу задач по определённой дате
    public Map<Integer, Task> getTaskByDate(LocalDate date) {
        Map<Integer, Task> newTasks = new HashMap<Integer, Task>();
        for (Map.Entry<Integer, Task> integerTaskEntry : mapTask.entrySet()) {
            Task value = integerTaskEntry.getValue();
            if (value.getDate().isAfter(LocalDateTime.of(date, LocalTime.of(0,0)))
                    && value.getDate().isBefore(LocalDateTime.of(date.plusDays(1), LocalTime.of(0,0)))) {
                newTasks.put(value.getId(), value);
            }
        }
        if (newTasks.isEmpty()) {
            return null;
        }
        return newTasks;
    }

    //получить список задач по дате и типу
    public Map<Integer, Task> getTaskByDateAndType(LocalDate date, String type) {
        Map<Integer, Task> newTasks = new HashMap<Integer, Task>();
        for (Map.Entry<Integer, Task> integerTaskEntry : mapTask.entrySet()) {
            Task value = integerTaskEntry.getValue();
           if (value.getDate().isAfter(LocalDateTime.of(date, LocalTime.of(0,0)))
                   && value.getDate().isBefore(LocalDateTime.of(date.plusDays(1), LocalTime.of(0,0)))
                   && value.getType().equals(type)) {
                newTasks.put(value.getId(), value);
            }
        }
        if (newTasks.isEmpty()) {
            return null;
        }
        return newTasks;
    }

    //возвращает массив id для проверки в контроллере
    public Integer[] getAllId() {
        Integer[] allId = new Integer[mapTask.size()];
        int i = 0;
        for (Map.Entry<Integer, Task> integerTaskEntry : mapTask.entrySet()) {
            Task value = integerTaskEntry.getValue();
            allId[i] = value.getId();
            i++;
        }
        return allId;
    }

    //возвращает массив дат для проверки в контроллере
    public LocalDateTime[] getAllDate() {
        LocalDateTime[] allDates = new LocalDateTime[mapTask.size()];
        int i = 0;
        for (Map.Entry<Integer, Task> integerTaskEntry : mapTask.entrySet()) {
            Task value = integerTaskEntry.getValue();
            allDates[i] = value.getDate();
            i++;
        }
        return allDates;
    }

    //вернуть конкантенацию строк даты и типа для проверки в контроллере
    public String[] getAllDateType() {
        String[] allDatesType = new String[mapTask.size()];
        int i = 0;
        for (Map.Entry<Integer, Task> integerTaskEntry : mapTask.entrySet()) {
            Task value = integerTaskEntry.getValue();
            allDatesType[i] = value.getDate() + "    " + value.getType();
        }
        return allDatesType;
    }

    public Task getTaskById(Integer id) {
        for (Map.Entry<Integer, Task> integerTaskEntry : mapTask.entrySet()) {
            Task value = integerTaskEntry.getValue();
            if (id.equals(value.getId()))
                return value;
        }
        return null;
    }

}
