module com.example.baudot {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com.example.baudot to javafx.fxml;
    exports com.example.baudot;
}