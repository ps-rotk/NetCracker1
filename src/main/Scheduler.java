package main;


import main.Interface.IObservable;
import main.Interface.IObserver;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static java.lang.Thread.sleep;

public class Scheduler extends Thread implements IObservable {
    private ArrayList<Task> list;
    private ArrayList<IObserver> observers;
    private boolean stop;

    public Scheduler(ArrayList<Task> list) {
        this.list = list;
        stop = false;
        observers = new ArrayList<>();
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }

    public void setList(ArrayList<Task> list) {
        this.list = list;
        interruptThread();
    }

    @Override
    public void addObserver(IObserver o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(IObserver o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers(Task task) {
        for (IObserver observer : observers)
            observer.update(task);
    }

    public void startThread(){
        this.start();
    }

    public void interruptThread(){
        this.interrupt();
    }

    @Override
    public void run() {
        while (!stop) {
            if (list.size() != 0) {
                for (Task task : list) {
                    if (!task.getPerformed()) {
                        long time = ChronoUnit.MILLIS.between(LocalDateTime.now(), task.getDate());
                        try {
                            sleep(time);
                            notifyObservers(task);
                            System.out.println("Задача наступила: " + task.toString());

                        } catch (InterruptedException e) {
                            break;
                        }
                    }
                }
            }

        }
    }


}