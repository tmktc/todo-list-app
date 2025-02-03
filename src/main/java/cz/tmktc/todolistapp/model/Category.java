package cz.tmktc.todolistapp.model;

/**
 * Category class.
 */
public class Category {
    private static int idCounter;
    private final int id;
    private String name;

    public Category(String name) {
        this.id = (idCounter + 1);
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

    @Override
    public String toString() {
        return this.name;
    }
}
