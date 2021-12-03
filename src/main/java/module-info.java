module com.example.main {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.persistence;


    opens com.example.main to javafx.fxml;
    exports com.example.main;
}