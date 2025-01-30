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
    public void initialize() {

        categories.addAll(CategoryManager.getInstance().categoryList);

        boxCategory.setItems(categories);
    }

    public void clickEditButton() {

        if (fieldName.getText().isEmpty() || boxCategory.getValue() == null || datePickerDueDate.getValue() == null) {
            labelWarning.setText("All fields have to be filled");
        } else {
            TaskManager.getInstance().updateTask(UserDataContainer.getInstance().getTaskID(), fieldName.getText(), boxCategory.getValue(), datePickerDueDate.getValue());
            Stage stage = (Stage) buttonEdit.getScene().getWindow();
            stage.close();
        }
    }
}