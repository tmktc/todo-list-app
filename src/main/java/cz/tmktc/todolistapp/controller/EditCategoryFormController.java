package cz.tmktc.todolistapp.controller;

import cz.tmktc.todolistapp.model.CategoryManager;
import cz.tmktc.todolistapp.model.UserDataContainer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditCategoryFormController {

    @FXML
    private Label labelWarning;

    @FXML
    private Button buttonEdit;

    @FXML
    private TextField fieldName;

    @FXML
    private void clickEditButton() {
        if (fieldName.getText().isEmpty()) {
            labelWarning.setText("Category name can not be empty");
        } else {
            CategoryManager.getInstance().updateCategory(UserDataContainer.getInstance().getCategoryID(), fieldName.getText());
            Stage stage = (Stage) buttonEdit.getScene().getWindow();
            stage.close();
        }
    }
}
