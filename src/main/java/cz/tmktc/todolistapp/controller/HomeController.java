package cz.tmktc.todolistapp.controller;

import cz.tmktc.todolistapp.ToDoListAPP;
import cz.tmktc.todolistapp.api.Task;
import cz.tmktc.todolistapp.api.TaskService;
import cz.tmktc.todolistapp.model.UserDataContainer;
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
import java.util.HashSet;
import java.util.Set;

/**
 * Controller for the home window.
 */
public class HomeController {
    private final ObservableList<String> categoryList = FXCollections.observableArrayList();
    private final ObservableList<Task> currentlyDisplayedTasksList = FXCollections.observableArrayList();
    private final ObservableList<Task> taskList = FXCollections.observableArrayList();
    private final Set<String> helperCategorySet = new HashSet<>();
    private final String allMode = "Show all tasks";
    private final String unfinishedMode = "Show unfinished tasks";
    private final String finishedMode = "Show finished tasks";
    @FXML
    private ChoiceBox<String> boxTaskFilterMode;
    @FXML
    private TableView<Task> tableTasks;
    @FXML
    private TableColumn<Task, String> columnCategory;
    @FXML
    private TableColumn<Task, String> columnTask;
    @FXML
    private TableColumn<Task, LocalDate> columnDueDate;
    @FXML
    private TableColumn<Task, Boolean> columnStatus;
    @FXML
    private ListView<String> panelCategories;
    private String currentCategoryFilter;
    private String currentTaskFilter = allMode;

    /**
     * Registers observers,
     * sets up tasks tableView and task filter mode choiceBox,
     * updates category and task lists
     * and sets cell factories.
     */
    @FXML
    private void initialize() {
        TaskService.getInstance().register(ChangeType.TASKS_CHANGE, this::updateTasks);

        setupTaskTable();
        setupTaskFilterModeChoiceBox();

        updateTasks();

        panelCategories.setCellFactory(param -> new ListCellCategory());
        columnCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        columnTask.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnDueDate.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        columnStatus.setCellValueFactory(new PropertyValueFactory<>("finished"));
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

                    markComplete.setOnAction(actionEvent -> finishTask(row.getItem().getID(), true));
                    markNotComplete.setOnAction(actionEvent -> finishTask(row.getItem().getID(), false));

                    edit.setOnAction(actionEvent -> {
                        try {
                            editTask(row.getItem().getID());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                    delete.setOnAction(actionEvent -> deleteTask(row.getItem().getID()));

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
     * Updates the current task filter.
     */
    @FXML
    private void chooseTaskFilter() {
        currentTaskFilter = boxTaskFilterMode.getValue();
        updateTasks();
    }

    /**
     * Updates the current category filter.
     */
    @FXML
    private void chooseCategoryFilter() {
        String target = panelCategories.getSelectionModel().getSelectedItem();
        if (target == null) return;

        if (target.equals(currentCategoryFilter)) currentCategoryFilter = null;
        else currentCategoryFilter = target;
        updateTasks();
    }

    /**
     * Updates the task list based on the current filter options.
     */
    @FXML
    private void updateTasks() {
        taskList.clear();
        categoryList.clear();
        helperCategorySet.clear();

        for (Task t : TaskService.getInstance().getAllTasks()) {
            helperCategorySet.add(t.getCategory());
        }
        categoryList.addAll(helperCategorySet);

        if (currentCategoryFilter != null) {
            taskList.addAll(TaskService.getInstance().getAllTasks().stream()
                    .filter(task -> task.getCategory().equals(currentCategoryFilter)).toList());
        } else {
            taskList.addAll(TaskService.getInstance().getAllTasks());
        }

        currentlyDisplayedTasksList.clear();
        if (currentTaskFilter.equals(finishedMode)) {
            currentlyDisplayedTasksList.addAll(taskList.stream().filter(Task::isFinished).toList());
        } else if (currentTaskFilter.equals(unfinishedMode)) {
            currentlyDisplayedTasksList.addAll(taskList.stream().filter(task -> !task.isFinished()).toList());
        } else {
            currentlyDisplayedTasksList.addAll(taskList);
        }

        tableTasks.setItems(currentlyDisplayedTasksList);
        panelCategories.setItems(categoryList);
    }

    /**
     * Launches the new task form window.
     */
    @FXML
    private void clickNewTaskButton() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ToDoListAPP.class.getResource("newTaskForm.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("New Task");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    /**
     * Changes the finish status of the task.
     *
     * @param taskID id of the task
     * @param status status to set
     */
    public void finishTask(String taskID, boolean status) {
        for (Task t : currentlyDisplayedTasksList) {
            if (t.getID().equals(taskID)) {
                t.setFinished(status);
                TaskService.getInstance().createTask(t);
                break;
            }
        }
    }

    /**
     * Launches the task edit form window.
     *
     * @param taskID id of the task to be edited
     */
    @FXML
    public void editTask(String taskID) throws IOException {
        UserDataContainer.getInstance().storeTask(taskID);

        FXMLLoader fxmlLoader = new FXMLLoader(ToDoListAPP.class.getResource("editTaskForm.fxml"));
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
    public void deleteTask(String taskID) {
        TaskService.getInstance().deleteTask(taskID);
    }
}