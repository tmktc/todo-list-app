module cz.tmktc.todolistapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens cz.tmktc.todolistapp to javafx.fxml;
    exports cz.tmktc.todolistapp;
    exports cz.tmktc.todolistapp.controller;
    opens cz.tmktc.todolistapp.controller to javafx.fxml;
    opens cz.tmktc.todolistapp.model to javafx.base;
}