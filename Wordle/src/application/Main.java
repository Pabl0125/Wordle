package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;


public class Main extends Application {
	private static Stage primaryStage;
	
	@Override
	public void start(Stage stage) {
	    try {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("Scene.fxml"));
	        Parent root = loader.load();

	        Scene scene = new Scene(root);
	        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

	        Controller controller = loader.getController();
	        controller.initKeyListener(scene);

	        Image icon = new Image(getClass().getResource("iconoWordle.png").toExternalForm());
	        stage.getIcons().add(icon);
	        
	        stage.setScene(scene);
	        stage.setResizable(false);
	        stage.setTitle("Wordle");
	        stage.show();

	        // Muy importante: guardar la referencia al Stage principal
	        primaryStage = stage;
	    } catch(Exception e) {
	        e.printStackTrace();
	    }
	}


	
	public static void main(String[] args) {
		launch(args);
	}


	public static Stage getPrimaryStage() {
		// TODO Auto-generated method stub
		return primaryStage;
	}
}
