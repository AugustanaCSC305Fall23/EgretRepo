module edu.augustana {
    requires javafx.controls;
    requires javafx.fxml;
    requires opencsv;

    opens edu.augustana to javafx.fxml;
    exports edu.augustana;
    exports edu.augustana.filters;
    opens edu.augustana.filters to javafx.fxml;
}
