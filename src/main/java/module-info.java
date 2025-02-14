module cz.tmktc.todolistapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires com.fasterxml.jackson.databind;


    opens cz.tmktc.todolistapp to javafx.fxml;
    exports cz.tmktc.todolistapp;
    exports cz.tmktc.todolistapp.controller;
    opens cz.tmktc.todolistapp.controller to javafx.fxml;
    opens cz.tmktc.todolistapp.model to javafx.base;
    opens cz.tmktc.todolistapp.api to javafx.base, com.fasterxml.jackson.databind;
}