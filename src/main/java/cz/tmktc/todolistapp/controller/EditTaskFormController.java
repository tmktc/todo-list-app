package cz.tmktc.todolistapp.controller;

import cz.tmktc.todolistapp.api.Task;
import cz.tmktc.todolistapp.api.TaskService;
import cz.tmktc.todolistapp.model.UserDataContainer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controller for the task edit form window.
 */
public class EditTaskFormController {

    @FXML
    private TextField fieldCategory;
    @FXML
    private Label labelWarning;
    @FXML
    private TextField fieldName;
    @FXML
    private DatePicker datePickerDueDate;
    @FXML
    private Button buttonEdit;

    /**
     * Sets the current name, list of categories and due date into the form fields.
     */
    @FXML
    private void initialize() {
        Task task = UserDataContainer.getInstance().getTask();

        fieldName.setText(task.getName());
        fieldCategory.setText(task.getCategory());
        datePickerDueDate.setValue(task.getDueDate());
    }

    /**
     * Takes the input from the fields and updates the task.
     * <p>
     * If the fields are empty, it shows a warning that all the fields have to be filled.
     */
    @FXML
    private void clickEditButton() {
        if (fieldName.getText().isEmpty() || fieldCategory.getText() == null || datePickerDueDate.getValue() == null) {
            labelWarning.setText("All fields have to be filled");
        } else {
            Task task = UserDataContainer.getInstance().getTask();
            task.setName(fieldName.getText());
            task.setCategory(fieldCategory.getText().toUpperCase());
            task.setDueDate(datePickerDueDate.getValue());
            TaskService.getInstance().saveTask(task);

            Stage stage = (Stage) buttonEdit.getScene().getWindow();
            stage.close();
        }
    }
}