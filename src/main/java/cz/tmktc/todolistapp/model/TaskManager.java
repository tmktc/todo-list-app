package cz.tmktc.todolistapp.model;

import cz.tmktc.todolistapp.model.observer.ChangeType;
import cz.tmktc.todolistapp.model.observer.Observable;
import cz.tmktc.todolistapp.model.observer.Observer;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Manages tasks - keeps a list of them, creates them, updates them and deletes them.
 */
public class TaskManager implements Observable {
    private static TaskManager taskManager = null;
    public final Map<Integer, Task> taskList;
    private final Map<ChangeType, Set<Observer>> listOfObservers = new HashMap<>();

    private TaskManager() {
        taskList = new HashMap<>();
        for (ChangeType changeType : ChangeType.values()) {
            listOfObservers.put(changeType, new HashSet<>());
        }
    }

    public static TaskManager getInstance() {
        if (taskManager == null) taskManager = new TaskManager();
        return taskManager;
    }

    public void create(String name, Category category, LocalDate dueDate) {
        Task task = new Task(name, category, dueDate);
        taskList.put(task.getId(), task);
        notifyObserver();
    }

    public void update(int id, String name, Category category, LocalDate dueDate) {
        taskList.get(id).setName(name);
        taskList.get(id).setCategory(category);
        taskList.get(id).setDueDate(dueDate);
        notifyObserver();
    }

    public void delete(int id) {
        taskList.remove(id);
        notifyObserver();
    }

    public void complete(int id, boolean finished) {
        taskList.get(id).setFinished(finished);
        notifyObserver();
    }

    @Override
    public void register(ChangeType changeType, Observer observer) {
        listOfObservers.get(changeType).add(observer);
    }

    private void notifyObserver() {
        for (Observer observer : listOfObservers.get(ChangeType.TASKS_CHANGE)) {
            observer.update();
        }
    }
}
