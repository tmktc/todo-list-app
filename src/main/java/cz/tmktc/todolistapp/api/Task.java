package cz.tmktc.todolistapp.api;

import java.time.LocalDate;

/**
 * Task class.
 */
public class Task {
    private String id;
    private String category;
    private String name;
    private LocalDate dueDate;
    private boolean finished;

    public Task() {
    }

    public Task(String category, String name, LocalDate dueDate) {
        this.category = category;
        this.name = name;
        this.dueDate = dueDate;
        this.finished = false;
    }

    public String getID() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }
}
