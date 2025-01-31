package cz.tmktc.todolistapp.controller;

import cz.tmktc.todolistapp.model.Category;
import cz.tmktc.todolistapp.model.CategoryManager;
import cz.tmktc.todolistapp.model.TaskManager;
import cz.tmktc.todolistapp.model.UserDataContainer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditTaskFormController {

    @FXML
    private Label labelWarning;

    @FXML
    private TextField fieldName;

    @FXML
    private DatePicker datePickerDueDate;

    @FXML
    private ChoiceBox<Category> boxCategory;

    @FXML
    private Button buttonEdit;

    private final ObservableList<Category> categories = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        fieldName.setText(UserDataContainer.getInstance().getTask().getName());

        categories.addAll(CategoryManager.getInstance().categoryList);
        boxCategory.setItems(categories);
        //TODO set default category choice

        datePickerDueDate.setValue(UserDataContainer.getInstance().getTask().getDueDate());
    }

    @FXML
    private void clickEditButton() {
        if (fieldName.getText().isEmpty() || boxCategory.getValue() == null || datePickerDueDate.getValue() == null) {
            labelWarning.setText("All fields have to be filled");
        } else {
            TaskManager.getInstance().updateTask(UserDataContainer.getInstance().getTask().getId(), fieldName.getText(), boxCategory.getValue(), datePickerDueDate.getValue());
            Stage stage = (Stage) buttonEdit.getScene().getWindow();
            stage.close();
        }
    }
}