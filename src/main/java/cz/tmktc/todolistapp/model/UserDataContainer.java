package cz.tmktc.todolistapp.model;

public class UserDataContainer {
    private static UserDataContainer userDataContainer = null;
    private Category category;
    private Task task;

    private UserDataContainer() {
        category = null;
        task = null;
    }

    public static UserDataContainer getInstance() {
        if (userDataContainer == null) userDataContainer = new UserDataContainer();
        return userDataContainer;
    }

    public void storeCategory(int id) {
        for (Category c : CategoryManager.getInstance().categoryList) {
            if (c.getId() == id) this.category = c;
        }
    }

    public void storeTask(int id) {
        for (Task t: TaskManager.getInstance().taskList) {
            if (t.getId() == id) this.task = t;
        }
    }

    public Category getCategory() {
        return category;
    }

    public Task getTask() {
        return task;
    }
}
