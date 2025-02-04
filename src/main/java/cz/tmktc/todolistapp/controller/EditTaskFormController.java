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

/**
 * Controller for the task edit form window.
 */
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

    /**
     * Sets the current name, list of categories and due date into the form fields.
     */
    @FXML
    private void initialize() {
        fieldName.setText(UserDataContainer.getInstance().getTask().getName());

        categories.addAll(CategoryManager.getInstance().categoryList.values());
        boxCategory.setItems(categories);
        boxCategory.getSelectionModel().select(UserDataContainer.getInstance().getTask().getCategory());

        datePickerDueDate.setValue(UserDataContainer.getInstance().getTask().getDueDate());
    }

    /**
     * Takes the input from the fields and updates the task.
     * <p>
     * If the fields are empty, it shows a warning that all the fields have to be filled.
     */
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