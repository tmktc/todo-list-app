package cz.tmktc.todolistapp.model;

/**
 * Stores an IDs of categories and tasks to use across different controllers.
 */
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
        this.category = CategoryManager.getInstance().categoryList.get(id);
    }

    public void storeTask(int id) {
        this.task = TaskManager.getInstance().taskList.get(id);
    }

    public Category getCategory() {
        return category;
    }

    public Task getTask() {
        return task;
    }
}
