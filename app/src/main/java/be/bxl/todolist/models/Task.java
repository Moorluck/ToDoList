package be.bxl.todolist.models;

import be.bxl.todolist.enums.Priority;

public class Task {
    private long id;
    private String name;
    private Priority priority;
    private String date;

    public Task(String name, Priority priority, String date) {
        this.name = name;
        this.priority = priority;
        this.date = date;
    }

    public Task(long id, String name, Priority priority, String date) {
        this(name, priority, date);
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Priority getPriority() {
        return priority;
    }

    public String getDate() {
        return date;
    }
}
