module com.example.main {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.persistence;
    requires java.naming;
    requires org.hibernate.orm.core;
    requires java.sql;


    opens com.example.main to javafx.fxml;
    exports com.example.main;
    exports com.example.main.models;
    opens com.example.main.models to javafx.fxml;
    exports com.example.main.game;
    opens com.example.main.game to javafx.fxml;
}