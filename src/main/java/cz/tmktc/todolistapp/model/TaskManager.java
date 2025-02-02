package cz.tmktc.todolistapp.model;

import cz.tmktc.todolistapp.model.observer.ChangeType;
import cz.tmktc.todolistapp.model.observer.Observable;
import cz.tmktc.todolistapp.model.observer.Observer;

import java.time.LocalDate;
import java.util.*;

public class TaskManager implements Observable {
    private static TaskManager taskManager = null;
    public final List<Task> taskList;
    private final Map<ChangeType, Set<Observer>> listOfObservers = new HashMap<>();

    private TaskManager() {
        taskList = new ArrayList<>();
        for (ChangeType changeType : ChangeType.values()) {
            listOfObservers.put(changeType, new HashSet<>());
        }
    }

    public static TaskManager getInstance() {
        if (taskManager == null) taskManager = new TaskManager();
        return taskManager;
    }

    public void create(String name, Category category, LocalDate dueDate) {
        taskList.add(new Task(name, category, dueDate));
        notifyObserver();
    }

    public void update(int id, String name, Category category, LocalDate dueDate) {
        for (Task task : taskList) {
            if (task.getId() == id) {
                task.setName(name);
                task.setCategory(category);
                task.setDueDate(dueDate);
            }
        }
        notifyObserver();
    }

    public void delete(int id) {
        taskList.removeIf(task -> task.getId() == id);
        notifyObserver();
    }

    public void complete(int id, boolean complete) {
        for (Task task : taskList) {
            if (task.getId() == id) task.setFinished(complete);
        }
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
