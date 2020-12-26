package main;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
      View view = new View();
       /*String filename = "Tasks.dat";
        // создадим список объектов, которые будем записывать
        LinkedList<Task> listTasks = new LinkedList<Task>();
        listTasks.add(new Task());
        listTasks.add(new Task());
        listTasks.add(new Task());
        listTasks.get(0).setDate("25.12.2020 22:22");
        listTasks.get(0).setId(0);
        listTasks.get(1).setDate("28.12.2020 00:00");
        listTasks.get(1).setId(1);
        listTasks.get(2).setId(2);
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename)))
        {
             DBLayout.serializeListTask(listTasks ,oos);
            System.out.println("File has been written");
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        // десериализация в новый список
        LinkedList<Task> newList = new LinkedList<Task>();
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename)))
        {
            newList = DBLayout.deserializeListTask(ois);
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        for(Task t : newList)
            System.out.println(t.getDate());*/
    }
}
