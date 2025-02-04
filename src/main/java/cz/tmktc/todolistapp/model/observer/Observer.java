package cz.tmktc.todolistapp.model.observer;

/**
 * Observer interface.
 */
public interface Observer {

    /**
     * When called, the update method for every observer in the list for a given game change is called.
     */
    void update();
}
