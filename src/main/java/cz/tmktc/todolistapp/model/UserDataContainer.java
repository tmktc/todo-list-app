package cz.tmktc.todolistapp.model;

public class UserDataContainer {
    private static UserDataContainer userDataContainer = null;
    private int categoryID;
    private int taskID;

    private UserDataContainer() {
        categoryID = 0;
        taskID = 0;
    }

    public static UserDataContainer getInstance() {
        if (userDataContainer == null) userDataContainer = new UserDataContainer();
        return userDataContainer;
    }

    public void storeCategoryID(int id) {
        this.categoryID = id;
    }

    public void storeTaskID(int id) {
        this.taskID = id;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public int getTaskID() {
        return taskID;
    }
}
