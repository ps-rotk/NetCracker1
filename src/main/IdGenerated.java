package main;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;

public class IdGenerated implements main.Interface.IdGenerated {

    public IdGenerated(){ }
    @Override
    public int getIdByDate() {
        LocalDateTime localDateTime = LocalDateTime.now();
        return localDateTime.getDayOfMonth()*(int)(Math.random()*10)+localDateTime.getMinute();
    }

    @Override
    public int getIdBySequence(ArrayList<Task> t) {
        return t.size()+1;
    }

    @Override
    public int getRandomId() {
        Random rand = new Random();
        return rand.nextInt(9999 - 1000) + 1000;
    }
}
