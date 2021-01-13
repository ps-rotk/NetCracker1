package main.Interface;

import main.Task;

import java.util.ArrayList;

public interface IdGenerated {

    int getIdByDate();

    int getIdBySequence(ArrayList<Task> t);

    int getRandomId();
}
