module com.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.example to javafx.fxml;
    opens com.example.model to javafx.base;
    exports com.example;
}
