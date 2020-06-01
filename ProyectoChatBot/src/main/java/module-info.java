module com.project {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    requires transitive assistant;
    requires transitive sdk.core;

    opens com.project to javafx.fxml;
    exports com.project;
}