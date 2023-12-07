module edu.augustana {
    requires javafx.controls;
    requires javafx.fxml;
    requires opencsv;
    requires com.google.gson;
    requires org.controlsfx.controls;
    requires java.prefs;


    opens edu.augustana to javafx.fxml, com.google.gson;
    exports edu.augustana;
    exports edu.augustana.filters;
    opens edu.augustana.filters to javafx.fxml;


}
