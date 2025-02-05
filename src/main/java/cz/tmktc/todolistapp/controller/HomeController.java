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

/**
 * Controller for the home window.
 */
public class HomeController {

    private final ObservableList<Category> categoryList = FXCollections.observableArrayList();
    private final ObservableList<Task> helperList = FXCollections.observableArrayList();
    private final ObservableList<Task> taskList = FXCollections.observableArrayList();
    private final String allMode = "Show all tasks";
    private final String unfinishedMode = "Show unfinished tasks";
    private final String finishedMode = "Show finished tasks";
    @FXML
    private ChoiceBox<String> boxTaskFilterMode;
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
    private Category currentCategoryFilter;
    private String currentTaskFilter = allMode;

    /**
     * Registers observers,
     * sets up tasks tableView and task filter mode choiceBox,
     * updates category and task lists
     * and sets cell factories.
     */
    @FXML
    private void initialize() {
        TaskManager.getInstance().register(ChangeType.TASKS_CHANGE, this::updateTasks);
        CategoryManager.getInstance().register(ChangeType.CATEGORIES_CHANGE, () -> {
            updateCategories();
            updateTasks();
        });

        setupTaskTable();
        setupTaskFilterModeChoiceBox();

        updateCategories();
        updateTasks();

        panelCategories.setCellFactory(param -> new ListCellCategory());
        columnCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        columnTask.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnDueDate.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        columnStatus.setCellValueFactory(new PropertyValueFactory<>("finished"));
    }

    /**
     * Clears the category list, then adds all categories and sets them to the category panel.
     */
    @FXML
    private void updateCategories() {
        categoryList.clear();
        categoryList.addAll(CategoryManager.getInstance().categoryList.values());
        panelCategories.setItems(categoryList);
    }

    /**
     * Sets up a context menu for each tableView row.
     */
    @FXML
    private void setupTaskTable() {
        tableTasks.setRowFactory(
                tableView -> {
                    final TableRow<Task> row = new TableRow<>();
                    final ContextMenu rowMenu = new ContextMenu();

                    MenuItem markComplete = new MenuItem("Mark Finished");
                    MenuItem markNotComplete = new MenuItem("Mark Unfinished");
                    MenuItem edit = new MenuItem("Edit");
                    MenuItem delete = new MenuItem("Delete");

                    markComplete.setOnAction(actionEvent -> finishTask(row.getItem().getId(), true));
                    markNotComplete.setOnAction(actionEvent -> finishTask(row.getItem().getId(), false));

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

    /**
     * Adds the modes to the task filter mode choiceBox.
     */
    @FXML
    private void setupTaskFilterModeChoiceBox() {
        ObservableList<String> modes = FXCollections.observableArrayList();
        modes.addAll(allMode, unfinishedMode, finishedMode);
        boxTaskFilterMode.setItems(modes);
        boxTaskFilterMode.getSelectionModel().select(0);
    }

    /**
     * Updates the task list based on the current filter options.
     */
    @FXML
    private void updateTasks() {
        taskList.clear();

        if (currentCategoryFilter != null) {
            taskList.addAll(TaskManager.getInstance().taskList.values().stream()
                    .filter(task -> task.getCategory() == currentCategoryFilter).toList());
        } else {
            taskList.addAll(TaskManager.getInstance().taskList.values());
        }

        helperList.clear();
        if (currentTaskFilter.equals(finishedMode)) {
            helperList.addAll(taskList.stream().filter(Task::isFinished).toList());
        } else if (currentTaskFilter.equals(unfinishedMode)) {
            helperList.addAll(taskList.stream().filter(task -> !task.isFinished()).toList());
        } else {
            helperList.addAll(taskList);
        }

        tableTasks.setItems(helperList);
    }

    /**
     * Updates the current category filter.
     */
    @FXML
    private void chooseCategoryFilter() {
        Category target = panelCategories.getSelectionModel().getSelectedItem();
        if (target == null) return;

        if (target == currentCategoryFilter) currentCategoryFilter = null;
        else currentCategoryFilter = target;
        updateTasks();
    }

    /**
     * Updates the current task filter.
     */
    @FXML
    private void chooseTaskFilter() {
        currentTaskFilter = boxTaskFilterMode.getValue();
        updateTasks();
    }

    /**
     * Launches the new category form window.
     */
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

    /**
     * Launches the new task form window.
     */
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

    /**
     * Launches the category edit form window.
     *
     * @param categoryID id of the category to be edited
     */
    @FXML
    public void editCategory(int categoryID) throws IOException {
        UserDataContainer.getInstance().storeCategory(categoryID);

        FXMLLoader fxmlLoader = new FXMLLoader(ToDoApp.class.getResource("editCategoryForm.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Edit category");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    /**
     * Deletes the given category.
     *
     * @param categoryID id of the category to be deleted
     */
    public void deleteCategory(int categoryID) {
        CategoryManager.getInstance().delete(categoryID);
    }

    /**
     * Changes the finish status of the task.
     *
     * @param taskID id of the task
     * @param status status to set
     */
    public void finishTask(int taskID, boolean status) {
        TaskManager.getInstance().complete(taskID, status);
    }

    /**
     * Launches the task edit form window.
     *
     * @param taskID id of the task to be edited
     */
    @FXML
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

    /**
     * Deletes the given task.
     *
     * @param taskID id of the task to be deleted
     */
    public void deleteTask(int taskID) {
        TaskManager.getInstance().delete(taskID);
    }
}