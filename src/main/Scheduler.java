package main;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static java.lang.Thread.sleep;

public class Scheduler /*implements Runnable*/ extends Thread {
    // private ArrayList<Task> list;
    private boolean stop;
    private Controller controller;

    public Scheduler(Controller controller) {
        this.controller = controller;
        stop = false;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }



    @Override
    public void run() {
        while (!stop) {
            if (controller.getListTasks().size() != 0) {
                LocalDateTime l = LocalDateTime.now();
                for (Task value : controller.getListTasks()) {
                    if (!value.getPerformed()) {
                        if ((value.getDate().getYear() == l.getYear()) &&
                                (value.getDate().getMonth() == l.getMonth()) &&
                                (value.getDate().getDayOfMonth() == l.getDayOfMonth()) &&
                                (value.getDate().getHour() == l.getHour()) &&
                                (value.getDate().getMinute() == l.getMinute())) {
                            System.out.println("Задача наступила: " + value.toString());
                            controller.setPerformed(value.getId(), true);
                        } else break;
                    }
                }
                try {
                    for (Task task : controller.getListTasks()) {
                        if (!task.getPerformed() && task.getDate().isAfter(LocalDateTime.now())) {
                            long time = ChronoUnit.MILLIS.between(LocalDateTime.now(), task.getDate());
                           // System.out.println(time + "   "+ task.getDate());
                            sleep(time);
                            break;
                        }
                    }
                } catch (InterruptedException e) {
                    continue;
                }

            }
        }
    }
}