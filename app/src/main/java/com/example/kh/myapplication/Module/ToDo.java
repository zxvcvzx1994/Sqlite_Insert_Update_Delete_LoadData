package com.example.kh.myapplication.Module;

/**
 * Created by kh on 7/3/2017.
 */

public class ToDo {
    private int id;
    private String task;
    private String doi;
    public ToDo(int id, String task, String doi){
        this.id = id;
        this.task = task;
        this.doi = doi;
    }
    public int getId() {
        return id;
    }

    public String getDoi() {
        return doi;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }
}
