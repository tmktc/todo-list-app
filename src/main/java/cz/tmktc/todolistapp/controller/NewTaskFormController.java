package cz.tmktc.todolistapp.controller;

import cz.tmktc.todolistapp.api.Task;
import cz.tmktc.todolistapp.api.TaskService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controller for the new task form window.
 */
public class NewTaskFormController {

    @FXML
    private TextField fieldCategory;
    @FXML
    private Label labelWarning;
    @FXML
    private TextField fieldName;
    @FXML
    private DatePicker datePickerDueDate;
    @FXML
    private Button buttonCreate;

    /**
     * Takes the input from the fields and creates the task.
     * <p>
     * If the fields are empty, it shows a warning that all the fields have to be filled.
     */
    @FXML
    private void clickCreateButton() {

        if (fieldName.getText().isEmpty() || fieldCategory.getText() == null || datePickerDueDate.getValue() == null) {
            labelWarning.setText("All fields have to be filled");
        } else {
            Task task = new Task(fieldCategory.getText(), fieldName.getText(), datePickerDueDate.getValue());
            TaskService.getInstance().createTask(task);

            Stage stage = (Stage) buttonCreate.getScene().getWindow();
            stage.close();
        }
    }
}
