module cz.tmktc.todolistapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens cz.tmktc.todolistapp to javafx.fxml;
    exports cz.tmktc.todolistapp;
}