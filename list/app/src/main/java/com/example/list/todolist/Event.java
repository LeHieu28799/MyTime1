package com.example.list.todolist;

public class Event {

    private int id;
    private String name;
    private  String date;
    private  String date_format;

    public Event(int id, String name, String date, String date_format) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.date_format = date_format;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate_format() {
        return date_format;
    }

    public void setDate_format(String date_format) {
        this.date_format = date_format;
    }
}
