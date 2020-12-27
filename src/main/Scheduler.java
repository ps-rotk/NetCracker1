package main;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import static java.lang.Thread.sleep;

public class Scheduler implements Runnable {
    LinkedList<Task> list;
    public Scheduler(LinkedList<Task> listTask){
        list = listTask;
    }
    @Override
    public void run() {
        while(true){
            DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
            Date date = new Date();
                while(!list.isEmpty()){
                    if (dateFormat.format(date).equals(list.get(0).getDate())){
                        System.out.println("Задача наступила: " + list.get(0).toString());
                        list.removeFirst();
                    }
                    else break;
                }
            try {
                sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }


}
