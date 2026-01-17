module app.wordle.desktopwordle {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;
    requires org.controlsfx.controls;

    opens app.wordle.desktopwordle to javafx.fxml;
    exports app.wordle.desktopwordle;
}