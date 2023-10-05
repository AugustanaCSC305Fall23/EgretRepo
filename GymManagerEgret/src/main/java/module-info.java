module edu.augustana {
    requires javafx.controls;
    requires javafx.fxml;

    opens edu.augustana to javafx.fxml;
    exports edu.augustana;
}
