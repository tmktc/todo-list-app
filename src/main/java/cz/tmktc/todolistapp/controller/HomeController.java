package cz.tmktc.todolistapp.controller;

import cz.tmktc.todolistapp.ToDoApp;
import cz.tmktc.todolistapp.model.*;
import cz.tmktc.todolistapp.model.observer.ChangeType;
import cz.tmktc.todolistapp.view.ListCellCategory;
import cz.tmktc.todolistapp.view.ListCellTask;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeController {

    @FXML
    private ListView<Task> panelTasks;

    @FXML
    private ListView<Category> panelCategories;

    private final ObservableList<Category> categoryList = FXCollections.observableArrayList();
    private final ObservableList<Task> taskList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        TaskManager.getInstance().register(ChangeType.TASKS_CHANGE, this::updateTaskList);
        CategoryManager.getInstance().register(ChangeType.CATEGORIES_CHANGE, () -> {
            updateCategoryList();
            updateTaskList();
        });

        updateCategoryList();
        updateTaskList();

        panelCategories.setCellFactory(param -> new ListCellCategory());
        panelTasks.setCellFactory(param -> new ListCellTask());
    }


    @FXML
    private void clickNewTaskButton() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ToDoApp.class.getResource("newTaskForm.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("New Task");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    @FXML
    private void clickNewCategoryButton() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ToDoApp.class.getResource("newCategoryForm.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("New Category");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    @FXML
    private void updateCategoryList() {
        categoryList.clear();
        categoryList.addAll(CategoryManager.getInstance().categoryList);
        panelCategories.setItems(categoryList);
    }

    @FXML
    private void updateTaskList() {
        taskList.clear();
        taskList.addAll(TaskManager.getInstance().taskList);
        panelTasks.setItems(taskList);
    }

    @FXML
    private void clickCategoryPanel() {
        Category target = panelCategories.getSelectionModel().getSelectedItem();
        if (target == null) return;
        taskList.clear();
        taskList.addAll(TaskManager.getInstance().taskList.stream()
                .filter(task -> task.getCategory().getId() == target.getId()).toList());

        panelTasks.setItems(taskList);
    }

    @FXML
    private void clickShowAllTasksButton() {
        updateTaskList();
    }

    public void renameCategory(int categoryID) throws IOException {
        UserDataContainer.getInstance().storeCategory(categoryID);

        FXMLLoader fxmlLoader = new FXMLLoader(ToDoApp.class.getResource("editCategoryForm.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Edit category");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    public void deleteCategory(int categoryID) {
        CategoryManager.getInstance().deleteCategory(categoryID);
    }

    public void completeTask(int taskID) {
        TaskManager.getInstance().completeTask(taskID, true);
    }

    public void editTask(int taskID) throws IOException {
        UserDataContainer.getInstance().storeTask(taskID);

        FXMLLoader fxmlLoader = new FXMLLoader(ToDoApp.class.getResource("editTaskForm.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Edit task");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    public void deleteTask(int taskID) {
        TaskManager.getInstance().deleteTask(taskID);
    }
}