package cz.tmktc.todolistapp.model.observer;

/**
 * Observable interface.
 */
public interface Observable {

    /**
     * Adds observer to the list of observers of given game change
     *
     * @param changeType type of change
     * @param observer to be registered
     */
    void register(ChangeType changeType, Observer observer);
}
