package cz.tmktc.todolistapp.view;

import javafx.scene.control.ListCell;
import javafx.scene.text.Font;

/**
 * Sets up cells in category listView - sets contents and font size.
 */
public class ListCellCategory extends ListCell<String> {

    @Override
    protected void updateItem(String category, boolean empty) {
        super.updateItem(category, empty);
        if (empty) setText(null);
        else {
            setText(category);
            setFont(Font.font(15));
        }
    }
}
