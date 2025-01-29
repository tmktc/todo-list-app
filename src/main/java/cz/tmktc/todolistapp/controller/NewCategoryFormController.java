package cz.tmktc.todolistapp.controller;

import cz.tmktc.todolistapp.model.CategoryManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class NewCategoryFormController {

    @FXML
    private Button buttonCreate;

    @FXML
    private TextField fieldName;

    @FXML
    public void clickCreateButton(MouseEvent mouseEvent) {

        //TODO check whether field isn't empty

        CategoryManager.getInstance().createCategory(fieldName.getText());
        Stage stage = (Stage) buttonCreate.getScene().getWindow();
        stage.close();
    }
}
