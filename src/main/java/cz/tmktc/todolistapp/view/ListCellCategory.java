package cz.tmktc.todolistapp.view;

import cz.tmktc.todolistapp.model.Category;
import javafx.scene.control.ListCell;

public class ListCellCategory extends ListCell<Category> {

    @Override
    protected void updateItem(Category category, boolean empty) {
        super.updateItem(category, empty);
        if (empty) setText(null);
        else {
            setText(category.getName());
        }
    }

}
