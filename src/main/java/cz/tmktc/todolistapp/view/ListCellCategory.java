package cz.tmktc.todolistapp.view;

import cz.tmktc.todolistapp.controller.HomeController;
import cz.tmktc.todolistapp.model.Category;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListCell;
import javafx.scene.control.MenuItem;
import javafx.scene.text.Font;

import java.io.IOException;

/**
 * Sets up cells in category listView - sets contents, defines contextMenu and sets font size.
 */
public class ListCellCategory extends ListCell<Category> {

    private final HomeController homeController = new HomeController();

    @Override
    protected void updateItem(Category category, boolean empty) {
        super.updateItem(category, empty);
        if (empty) setText(null);
        else {
            setText(category.getName());

            MenuItem edit = new MenuItem("Edit");
            MenuItem delete = new MenuItem("Delete");

            edit.setOnAction(event -> {
                try {
                    homeController.editCategory(category.getId());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            delete.setOnAction(event -> homeController.deleteCategory(category.getId()));

            setContextMenu(new ContextMenu(edit, delete));
            setFont(Font.font(15));
        }
    }
}
