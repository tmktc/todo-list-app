package cz.tmktc.todolistapp.model;

import cz.tmktc.todolistapp.api.Task;
import cz.tmktc.todolistapp.api.TaskService;

/**
 * Stores an IDs of categories and tasks to use across different controllers.
 */
public class UserDataContainer {
    private static UserDataContainer userDataContainer = null;
    private Task task;

    private UserDataContainer() {
        task = null;
    }

    public static UserDataContainer getInstance() {
        if (userDataContainer == null) userDataContainer = new UserDataContainer();
        return userDataContainer;
    }

    public void storeTask(String id) {
        this.task = TaskService.getInstance().getTask(id);
    }

    public Task getTask() {
        return task;
    }
}
