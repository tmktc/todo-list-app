package cz.tmktc.todolistapp.controller;

import cz.tmktc.todolistapp.model.Category;
import cz.tmktc.todolistapp.model.CategoryManager;
import cz.tmktc.todolistapp.model.TaskManager;
import cz.tmktc.todolistapp.model.UserDataContainer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class EditTaskFormController {

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
    private Button buttonEdit;

    @FXML
    private void initialize() {
        fieldName.setText(UserDataContainer.getInstance().getTask().getName());

        categories.addAll(CategoryManager.getInstance().categoryList.values());
        boxCategory.setItems(categories);
        boxCategory.getSelectionModel().select(UserDataContainer.getInstance().getTask().getCategory());

        datePickerDueDate.setValue(UserDataContainer.getInstance().getTask().getDueDate());
    }

    @FXML
    private void clickEditButton() {
        if (fieldName.getText().isEmpty() || boxCategory.getValue() == null || datePickerDueDate.getValue() == null) {
            labelWarning.setText("All fields have to be filled");
        } else {
            TaskManager.getInstance().update(UserDataContainer.getInstance().getTask().getId(), fieldName.getText(), boxCategory.getValue(), datePickerDueDate.getValue());
            Stage stage = (Stage) buttonEdit.getScene().getWindow();
            stage.close();
        }
    }
}