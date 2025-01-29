package cz.tmktc.todolistapp.model;

import java.util.Date;

public class Task {
    private final int id;
    private Category category;
    private String name;
    private Date dueDate;
    private boolean completed;
    private static int idCounter;

    public Task(String name, Category category, Date dueDate) {
        this.id = (idCounter+1);
        this.category = category;
        this.name = name;
        this.dueDate = dueDate;
        this.completed = false;
        idCounter++;
    }

    public int getId() {
        return id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
