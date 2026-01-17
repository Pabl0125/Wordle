package app.wordle.desktopwordle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;

public class HelloAplication extends Application {
    private static Stage primaryStage;

    @Override
    public void start(Stage stage) {
        try {
            // 1.1 Carga del FXML: Al estar en la misma ruta de paquete en resources, se usa el nombre directo
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Scene.fxml"));

            if (loader.getLocation() == null) {
                // Si falla, imprimimos la ruta donde está buscando para depurar
                throw new RuntimeException("Error: No se encontró Scene.fxml. Verifica que esté en src/main/resources/app/wordle/desktopwordle/");
            }

            Parent root = loader.load();

            // 1.2 Configuración de la Escena y CSS
            Scene scene = new Scene(root);
            String cssPath = getClass().getResource("application.css").toExternalForm();
            scene.getStylesheets().add(cssPath);

            // 1.3 Inicialización del Controlador
            Controller controller = loader.getController();
            controller.initKeyListener(scene);

            // 1.4 Icono de la ventana
            Image icon = new Image(getClass().getResource("iconoWordle.png").toExternalForm());
            stage.getIcons().add(icon);

            // 1.5 Configuración del Stage
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Wordle");
            stage.show();

            // Guardar referencia global
            primaryStage = stage;

        } catch(Exception e) {
            System.err.println("Error al iniciar la aplicación:");
            e.printStackTrace();
        }
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    // 1.6 Método main para asegurar el arranque con Gradle
    public static void main(String[] args) {
        launch(args);
    }
}