package cz.tmktc.todolistapp.model;

public class Category {
    private final int id;
    private String name;
    private static int idCounter;

    public Category(String name) {
        this.id = (idCounter+1);
        this.name = name;
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
}
