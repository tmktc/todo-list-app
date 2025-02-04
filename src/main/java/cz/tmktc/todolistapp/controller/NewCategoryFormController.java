package cz.tmktc.todolistapp.controller;

import cz.tmktc.todolistapp.model.CategoryManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controller for the new category form window.
 */
public class NewCategoryFormController {

    @FXML
    private Label labelWarning;

    @FXML
    private Button buttonCreate;

    @FXML
    private TextField fieldName;

    /**
     * Takes the input from the name textField and creates the category.
     * <p>
     * If the textField is empty, it shows a warning that the category name can not be empty.
     */
    @FXML
    private void clickCreateButton() {

        if (fieldName.getText().isEmpty()) {
            labelWarning.setText("Category name can not be empty");
        } else {
            CategoryManager.getInstance().create(fieldName.getText());
            Stage stage = (Stage) buttonCreate.getScene().getWindow();
            stage.close();
        }
    }
}
