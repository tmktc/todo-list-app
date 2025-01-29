package cz.tmktc.todolistapp.controller;

import cz.tmktc.todolistapp.model.Category;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class NewTaskFormController {

    @FXML
    private TextField fieldName;

    @FXML
    private DatePicker datePickerDueDate;

    @FXML
    private ChoiceBox boxCategory;

    @FXML
    private Button buttonCreate;


    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
