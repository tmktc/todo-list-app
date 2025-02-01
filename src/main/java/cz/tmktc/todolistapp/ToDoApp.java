package cz.tmktc.todolistapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ToDoApp extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ToDoApp.class.getResource("home.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("To-Do App");
        stage.setScene(scene);
        stage.show();
    }
}