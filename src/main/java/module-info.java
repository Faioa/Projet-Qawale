module fr.serialcoders.qawaleproject {
    requires javafx.controls;
    requires javafx.fxml;


    opens fr.serialcoders.qawaleproject to javafx.fxml;
    exports fr.serialcoders.qawaleproject;
    exports fr.serialcoders.qawaleproject.logic.objects;
    opens fr.serialcoders.qawaleproject.logic.objects to javafx.fxml;
    exports fr.serialcoders.qawaleproject.logic.objects.qawale;
    opens fr.serialcoders.qawaleproject.logic.objects.qawale to javafx.fxml;
    exports fr.serialcoders.qawaleproject.logic.objects.quarto;
    opens fr.serialcoders.qawaleproject.logic.objects.quarto to javafx.fxml;
    exports fr.serialcoders.qawaleproject.logic.objects.commands;
    opens fr.serialcoders.qawaleproject.logic.objects.commands to javafx.fxml;
}