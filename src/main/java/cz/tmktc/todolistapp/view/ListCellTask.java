package cz.tmktc.todolistapp.view;

import cz.tmktc.todolistapp.model.Task;
import javafx.scene.control.ListCell;

public class ListCellTask extends ListCell<Task> {

    @Override
    protected void updateItem(Task task, boolean empty) {
        super.updateItem(task, empty);
        if (empty) setText(null);
        else {
            setText(task.getName());
        }
    }

}
