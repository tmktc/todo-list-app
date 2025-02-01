package cz.tmktc.todolistapp.model.observer;

public interface Observable {

    void register(ChangeType changeType, Observer observer);
}
