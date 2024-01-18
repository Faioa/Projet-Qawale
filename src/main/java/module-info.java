module fr.serialcoders.qawaleproject {
    requires javafx.controls;
    requires javafx.fxml;

    exports fr.serialcoders.qawaleproject.ui.controller;
    opens fr.serialcoders.qawaleproject.ui.controller to javafx.fxml;
    exports fr.serialcoders.qawaleproject.ui.app;
    opens fr.serialcoders.qawaleproject.ui.app to javafx.fxml;
}