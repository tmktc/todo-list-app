package cz.tmktc.todolistapp.model;

import cz.tmktc.todolistapp.model.observer.ChangeType;
import cz.tmktc.todolistapp.model.observer.Observable;
import cz.tmktc.todolistapp.model.observer.Observer;

import java.util.*;

public class CategoryManager implements Observable {
    private static CategoryManager categoryManager = null;
    public final List<Category> categoryList;
    private final Map<ChangeType, Set<Observer>> listOfObservers = new HashMap<>();

    private CategoryManager() {
        categoryList = new ArrayList<>();
        for (ChangeType changeType : ChangeType.values()) {
            listOfObservers.put(changeType, new HashSet<>());
        }
    }

    public static CategoryManager getInstance() {
        if (categoryManager == null) categoryManager = new CategoryManager();
        return categoryManager;
    }

    public void create(String name) {
        categoryList.add(new Category(name));
        notifyObserver();
    }

    public void update(int id, String name) {
        for (Category category : categoryList) {
            if (category.getId() == id) {
                category.setName(name);
            }
        }
        notifyObserver();
    }

    public void delete(int id) {
        TaskManager.getInstance().taskList.removeIf(task -> task.getCategory().getId() == id);

        categoryList.removeIf(category -> category.getId() == id);

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
