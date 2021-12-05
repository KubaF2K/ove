module com.example.main {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.persistence;
    requires java.naming;
    requires org.hibernate.orm.core;
    requires java.sql;

    opens com.example.main to org.hibernate.orm.core, javafx.fxml;
    opens com.example.main.models to org.hibernate.orm.core, javafx.fxml;
    opens com.example.main.game to org.hibernate.orm.core, javafx.fxml;
    exports com.example.main;
    exports com.example.main.models;
    exports com.example.main.game;

}