package cz.tmktc.todolistapp.view;

import cz.tmktc.todolistapp.controller.HomeController;
import cz.tmktc.todolistapp.model.Task;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListCell;
import javafx.scene.control.MenuItem;

import java.io.IOException;

public class ListCellTask extends ListCell<Task> {

    HomeController homeController = new HomeController();

    @Override
    protected void updateItem(Task task, boolean empty) {
        super.updateItem(task, empty);
        if (empty) setText(null);
        else {
            setText(task.getName());

            MenuItem markCompleted = new MenuItem("Mark Completed");
            MenuItem edit = new MenuItem("Edit");
            MenuItem delete = new MenuItem("Delete");

            markCompleted.setOnAction(event -> homeController.completeTask(task));
            edit.setOnAction(event -> {
                try {
                    homeController.editTask(task);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            delete.setOnAction(event -> homeController.deleteTask(task));

            setContextMenu(new ContextMenu(markCompleted, edit, delete));

        }
    }

}
