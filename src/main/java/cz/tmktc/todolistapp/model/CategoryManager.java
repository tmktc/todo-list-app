package cz.tmktc.todolistapp.model;

import cz.tmktc.todolistapp.model.observer.ChangeType;
import cz.tmktc.todolistapp.model.observer.Observable;
import cz.tmktc.todolistapp.model.observer.Observer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Manages categories - keeps a list of them, creates them, updates them and deletes them.
 */
public class CategoryManager implements Observable {
    private static CategoryManager categoryManager = null;
    public final Map<Integer, Category> categoryList;
    private final Map<ChangeType, Set<Observer>> listOfObservers = new HashMap<>();

    private CategoryManager() {
        categoryList = new HashMap<>();
        for (ChangeType changeType : ChangeType.values()) {
            listOfObservers.put(changeType, new HashSet<>());
        }
    }

    public static CategoryManager getInstance() {
        if (categoryManager == null) categoryManager = new CategoryManager();
        return categoryManager;
    }

    public void create(String name) {
        Category category = new Category(name);
        categoryList.put(category.getId(), category);
        notifyObserver();
    }

    public void update(int id, String name) {
        categoryList.get(id).setName(name);
        notifyObserver();
    }

    public void delete(int id) {
        TaskManager.getInstance().taskList.values().removeIf(task -> task.getCategory().getId() == id);
        categoryList.remove(id);
        notifyObserver();
    }

    @Override
    public void register(ChangeType changeType, Observer observer) {
        listOfObservers.get(changeType).add(observer);
    }

    private void notifyObserver() {
        for (Observer observer : listOfObservers.get(ChangeType.CATEGORIES_CHANGE)) {
            observer.update();
        }
    }
}