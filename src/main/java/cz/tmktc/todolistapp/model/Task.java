package cz.tmktc.todolistapp.model;

import java.util.Date;

public class Task {
    private final int id;
    private String name;
    private String description;
    private Date dueDate;
    private boolean completed;
    private static int idCounter;

    public Task(String name, String description, Date dueDate) {
        this.id = (idCounter+1);
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
        this.completed = false;
        idCounter++;
    }



    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
