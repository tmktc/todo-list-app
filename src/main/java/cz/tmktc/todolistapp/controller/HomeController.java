package cz.tmktc.todolistapp.controller;

import cz.tmktc.todolistapp.ToDoApp;
import cz.tmktc.todolistapp.model.*;
import cz.tmktc.todolistapp.model.observer.ChangeType;
import cz.tmktc.todolistapp.view.ListCellCategory;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class HomeController {

    private final ObservableList<Category> categoryList = FXCollections.observableArrayList();
    private final ObservableList<Task> taskList = FXCollections.observableArrayList();
    @FXML
    private TableView<Task> tableTasks;
    @FXML
    private TableColumn<Task, Category> columnCategory;
    @FXML
    private TableColumn<Task, String> columnTask;
    @FXML
    private TableColumn<Task, LocalDate> columnDueDate;
    @FXML
    private TableColumn<Task, Boolean> columnStatus;
    @FXML
    private ListView<Category> panelCategories;

    @FXML
    private void initialize() {
        TaskManager.getInstance().register(ChangeType.TASKS_CHANGE, this::updateTaskList);
        CategoryManager.getInstance().register(ChangeType.CATEGORIES_CHANGE, () -> {
            updateCategoryList();
            updateTaskList();
        });

        updateCategoryList();
        updateTaskList();
        setupTasksTable();

        panelCategories.setCellFactory(param -> new ListCellCategory());
        columnCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        columnTask.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnDueDate.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        columnStatus.setCellValueFactory(new PropertyValueFactory<>("completed"));
    }

    @FXML
    private void setupTasksTable() {
        tableTasks.setRowFactory(
                tableView -> {
                    final TableRow<Task> row = new TableRow<>();
                    final ContextMenu rowMenu = new ContextMenu();

                    MenuItem markComplete = new MenuItem("Mark Complete");
                    MenuItem markNotComplete = new MenuItem("Mark Not Complete");
                    MenuItem edit = new MenuItem("Edit");
                    MenuItem delete = new MenuItem("Delete");

                    markComplete.setOnAction(actionEvent -> completeTask(row.getItem().getId(), true));
                    markNotComplete.setOnAction(actionEvent -> completeTask(row.getItem().getId(), false));

                    edit.setOnAction(actionEvent -> {
                        try {
                            editTask(row.getItem().getId());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                    delete.setOnAction(actionEvent -> deleteTask(row.getItem().getId()));

                    rowMenu.getItems().addAll(markComplete, markNotComplete, edit, delete);

                    //only display context menu for non-null items
                    row.contextMenuProperty().bind(
                            Bindings.when(Bindings.isNotNull(row.itemProperty()))
                                    .then(rowMenu)
                                    .otherwise((ContextMenu) null));
                    return row;
                }
        );
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
        tableTasks.setItems(taskList);
    }

    @FXML
    private void clickCategoryPanel() {
        Category target = panelCategories.getSelectionModel().getSelectedItem();
        if (target == null) return;
        taskList.clear();
        taskList.addAll(TaskManager.getInstance().taskList.stream()
                .filter(task -> task.getCategory().getId() == target.getId()).toList());

        tableTasks.setItems(taskList);
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
        CategoryManager.getInstance().delete(categoryID);
    }

    public void completeTask(int taskID, boolean status) {
        TaskManager.getInstance().complete(taskID, status);
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
        TaskManager.getInstance().delete(taskID);
    }

    @FXML
    private void clickShowOnlyCompletedTasks() {
        taskList.clear();
        taskList.addAll(TaskManager.getInstance().taskList.stream()
                .filter(Task::isCompleted).toList());

        tableTasks.setItems(taskList);
    }

    @FXML
    private void clickShowOnlyNotCompletedTasks() {
        taskList.clear();
        taskList.addAll(TaskManager.getInstance().taskList.stream()
                .filter(task -> !task.isCompleted()).toList());

        tableTasks.setItems(taskList);
    }
}