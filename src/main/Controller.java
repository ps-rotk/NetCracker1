package main;


import IdGenerated.IdGeneratorFactory;
import main.Interface.IObservable;
import main.Interface.IObserver;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class Controller {
    private DBLayout layout;
    private Scheduler scheduler;
    private IdGeneratorFactory idGeneratorFactory;
    private Observer observer;

    //проверяет при открытии программы наличие старых тасков
    public ArrayList<Task> checkOldTask() {
        ArrayList<Task> deletedList = new ArrayList<>();
        if (layout.getAllTasks().size() == 0)
            return null;
        else {
            for (Task t : layout.getAllTasks()) {
                if (t.getPerformed() || t.getDate().isBefore(LocalDateTime.now())) {
                    t.setPerformed(true);
                    deletedList.add(t);
                }
            }
        }
        return deletedList;
    }

    //конструктор
    public Controller() throws IOException, ClassNotFoundException {
        layout = new DBLayout();
        idGeneratorFactory = new IdGeneratorFactory();
        scheduler = new Scheduler(getNotExecuted());
        scheduler.startThread();
        observer = new Observer(this);
    }

    public Scheduler getScheduler(){
        return scheduler;
    }

    //добавление таска
    public void addTask(Task t) throws IOException {
        layout.addTask(t);
        scheduler.setList(getNotExecuted());
    }

    //изменить таск
    public void updateTask(Task newT) throws IOException {
        layout.updateTask(newT);
        scheduler.setList(getNotExecuted());
    }

    //удалить таск
    public void deleteTaskById(Integer id) throws IOException {
        layout.deleteTask(id);
        scheduler.setList(getNotExecuted());
    }

    //получить мапу таск по условию (по датам)
    public ArrayList<Task> getTaskByDate(LocalDate date) {
        return layout.getTaskByDate(date);
    }

    //получить таск по условию (по строке и по типу)
    public ArrayList<Task> getTaskByQuery(LocalDate date, String type) {
        return layout.getTaskByDateAndType(date, type);

    }

    //получить стринг всех задач
    public ArrayList<Task> getListTasks() {
        return layout.getAllTasks();
    }

    public Task getTaskById(Integer id) {
        return layout.getTaskById(id);
    }

    public void exit() {
        scheduler.setStop(true);
    }

    public void setPerformed(int id, boolean check) {
        layout.setPerformed(id, check);
    }

    public int setNewId() {
        return idGeneratorFactory.createId(layout.getAllTasks());
    }

    private ArrayList<Task> getNotExecuted() {
        ArrayList<Task> newArray = new ArrayList<>();
        for (Task task : layout.getAllTasks()) {
            if (!task.getPerformed() && task.getDate().isAfter(LocalDateTime.now())) {
                newArray.add(task);
            }
        }
        return newArray;
    }


}
