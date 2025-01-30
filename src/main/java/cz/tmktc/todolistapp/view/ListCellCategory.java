package cz.tmktc.todolistapp.view;

import cz.tmktc.todolistapp.controller.HomeController;
import cz.tmktc.todolistapp.model.Category;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListCell;
import javafx.scene.control.MenuItem;

import java.io.IOException;

public class ListCellCategory extends ListCell<Category> {

    HomeController homeController = new HomeController();

    @Override
    protected void updateItem(Category category, boolean empty) {
        super.updateItem(category, empty);
        if (empty) setText(null);
        else {
            setText(category.getName());

            MenuItem rename = new MenuItem("Rename");
            MenuItem delete = new MenuItem("Delete");

            rename.setOnAction(event -> {
                try {
                    homeController.renameCategory(category);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            delete.setOnAction(event -> homeController.deleteCategory(category));

            setContextMenu(new ContextMenu(rename, delete));


        }
    }
}
