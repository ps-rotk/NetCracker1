package main;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Task implements Serializable {
    private Integer id;
    private LocalDateTime date;
    private String type;
    private String text;

    public Task(){
        id = 0;
        date = LocalDateTime.now();
        type = "o";
        text = "oo";
    }

    public Task(Integer id, LocalDateTime date, String type, String text){
        this.id = id;
        this.date = date;
        this.type = type;
        this.text = text;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


   /* @Override
    public int compareTo(Task o) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(dateFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar1 = Calendar.getInstance();
        try {
            calendar1.setTime(dateFormat.parse(o.getDate()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return calendar.compareTo(calendar1);
    }

    public boolean compareString(String o) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(dateFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar1 = Calendar.getInstance();
        try {
            calendar1.setTime(dateFormat.parse(o));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (calendar.compareTo(calendar1) > 0)
            return true;
        else return false;
    }*/
    public String toString(){
        return "ID: " + id + "\t" +
                "Дата: " + date.getDayOfMonth() + "."+ date.getMonthValue() + "."+ date.getYear() + " " + date.getHour() + ":" + date.getMinute()+ "\t" +
                "Тип: " + type + "\t" +
                "Текст: " + text;
    }
}
