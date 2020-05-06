module com.project {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires assistant;
    requires sdk.core;

    opens com.project to javafx.fxml;
    exports com.project;
}