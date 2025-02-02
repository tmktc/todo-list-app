package cz.tmktc.todolistapp.controller;

import cz.tmktc.todolistapp.model.Category;
import cz.tmktc.todolistapp.model.CategoryManager;
import cz.tmktc.todolistapp.model.TaskManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class NewTaskFormController {

    private final ObservableList<Category> categories = FXCollections.observableArrayList();
    @FXML
    private Label labelWarning;
    @FXML
    private TextField fieldName;
    @FXML
    private DatePicker datePickerDueDate;
    @FXML
    private ChoiceBox<Category> boxCategory;
    @FXML
    private Button buttonCreate;

    @FXML
    private void initialize() {
        categories.addAll(CategoryManager.getInstance().categoryList.values());
        boxCategory.setItems(categories);
    }

    @FXML
    private void clickCreateButton() {

        if (fieldName.getText().isEmpty() || boxCategory.getValue() == null || datePickerDueDate.getValue() == null) {
            labelWarning.setText("All fields have to be filled");
        } else {
            TaskManager.getInstance().create(fieldName.getText(), boxCategory.getValue(), datePickerDueDate.getValue());
            Stage stage = (Stage) buttonCreate.getScene().getWindow();
            stage.close();
        }
    }
}
