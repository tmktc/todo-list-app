package cz.tmktc.todolistapp.controller;

import cz.tmktc.todolistapp.model.Category;
import cz.tmktc.todolistapp.model.CategoryManager;
import cz.tmktc.todolistapp.model.TaskManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class NewTaskFormController {

    @FXML
    private TextField fieldName;

    @FXML
    private DatePicker datePickerDueDate;

    @FXML
    private ChoiceBox<Category> boxCategory;

    @FXML
    private Button buttonCreate;

    private final ObservableList<Category> categories = FXCollections.observableArrayList();

    @FXML
    public void initialize() {

        categories.addAll(CategoryManager.getInstance().categoryList);

        boxCategory.setItems(categories);
    }

    public void clickCreateButton(MouseEvent mouseEvent) {

        //TODO check whether fields aren't empty

        TaskManager.getInstance().createTask(fieldName.getText(), boxCategory.getValue(), datePickerDueDate.getValue());
        Stage stage = (Stage) buttonCreate.getScene().getWindow();
        stage.close();
    }
}
