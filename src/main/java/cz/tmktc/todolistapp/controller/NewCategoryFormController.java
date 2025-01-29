package cz.tmktc.todolistapp.controller;

import cz.tmktc.todolistapp.model.CategoryManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class NewCategoryFormController {

    @FXML
    private Label labelWarning;

    @FXML
    private Button buttonCreate;

    @FXML
    private TextField fieldName;

    @FXML
    public void clickCreateButton(MouseEvent mouseEvent) {

        if (fieldName.getText().isEmpty()) {
            labelWarning.setText("Category name can not be empty");
        } else {
            CategoryManager.getInstance().createCategory(fieldName.getText());
            Stage stage = (Stage) buttonCreate.getScene().getWindow();
            stage.close();
        }
    }
}
