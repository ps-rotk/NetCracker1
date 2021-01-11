package main;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import static java.lang.Thread.sleep;

public class Scheduler implements Runnable {
    private Map<Integer, Task> map;
    private boolean stop;

    public Scheduler(Map<Integer, Task> mapTask) {
        map = mapTask;
        stop = false;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }

    @Override
    public void run() {
        while (!stop) {
            while (!map.isEmpty()) {

                /*for (Map.Entry<Integer, Task> integerTaskEntry : map.entrySet()) {
                    Task value = integerTaskEntry.getValue();*/
                LocalDateTime l = LocalDateTime.now();
                for(Iterator<Integer> iterator = map.keySet().iterator(); iterator.hasNext(); ) {
                    Integer key = iterator.next();
                    Task value = map.get(key);
                    if ((value.getDate().getYear() == l.getYear()) &&
                            (value.getDate().getMonth() == l.getMonth()) &&
                            (value.getDate().getDayOfMonth() == l.getDayOfMonth()) &&
                            (value.getDate().getHour() == l.getHour()) &&
                            (value.getDate().getMinute() == l.getMinute())) {
                        System.out.println("Задача наступила: " + value.toString());
                        iterator.remove();//TODO: помечать выполнение, а не удалять, новое поле для задачи
                    } else break;
                }
            }
            try {
                sleep(300);//TODO: спать до ближайшей задачи
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }


}
