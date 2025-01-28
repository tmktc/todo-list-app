package cz.tmktc.todolistapp.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaskManager {
    private static TaskManager taskManager = null;
    public final List<Task> taskList;

    private TaskManager() {
        taskList = new ArrayList<>();
    }

    public static TaskManager getInstance() {
        if (taskManager == null) taskManager = new TaskManager();
        return taskManager;
    }

    public void createTask(String name, String description, Date dueDate) {
        taskList.add(new Task(name, description, dueDate));
    }

    public Task readTask(int id) {
        for (Task task: taskList) {
            if (task.getId() == id) return task;
        }
        return null;
    }

    public void updateTask(int id, String name, String description, Date dueDate, boolean completed) {
        for (Task task: taskList) {
            if (task.getId() == id) {
                task.setName(name);
                task.setDescription(description);
                task.setDueDate(dueDate);
                task.setCompleted(completed);
            }
        }
    }

    public void deleteTask (int id) {
        taskList.removeIf(task -> task.getId() == id);
    }

}
