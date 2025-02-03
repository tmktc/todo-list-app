package cz.tmktc.todolistapp.controller;

import cz.tmktc.todolistapp.model.CategoryManager;
import cz.tmktc.todolistapp.model.UserDataContainer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controller for the category edit form window.
 */
public class EditCategoryFormController {

    @FXML
    private Label labelWarning;

    @FXML
    private Button buttonEdit;

    @FXML
    private TextField fieldName;

    /**
     * Sets the current category name into the name textField.
     */
    @FXML
    private void initialize() {
        fieldName.setText(UserDataContainer.getInstance().getCategory().getName());
    }

    /**
     * Takes the input from the name textField and updates the category.
     * <p>
     * If the textField is empty, it shows a warning that the category name can not be empty.
     */
    @FXML
    private void clickEditButton() {
        if (fieldName.getText().isEmpty()) {
            labelWarning.setText("Category name can not be empty");
        } else {
            CategoryManager.getInstance().update(UserDataContainer.getInstance().getCategory().getId(), fieldName.getText());
            Stage stage = (Stage) buttonEdit.getScene().getWindow();
            stage.close();
        }
    }
}
