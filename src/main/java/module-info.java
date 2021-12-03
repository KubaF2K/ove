module com.example.main {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.persistence;
    requires org.hibernate.orm.core;


    opens com.example.main to javafx.fxml;
    exports com.example.main;
    exports com.example.main.models;
    opens com.example.main.models to javafx.fxml;
}