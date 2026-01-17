package app.wordle.desktopwordle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class PopUpController {

    private Stage stage;
    private Controller Controller;

    @FXML
    private Label myLabel;
    @FXML
    private ImageView myImageView;

    public void initialize() {
        Image img = new Image(getClass().getResource("WordleLogo.png").toExternalForm());
        myImageView.setImage(img);
    }


    public void setStage(Stage stage,String message) {
        myLabel.setText(message);
        this.stage = stage;
    }

    public void setMainController(Controller mainController) {
        this.Controller = mainController;
    }

    @FXML
    private void playAgain() {
        if (Controller != null) {
            Controller.resetBoard();
            Controller.resetButtons();
        }
        stage.close();
    }

    @FXML
    private void exit() {
        Stage mainStage = HelloAplication.getPrimaryStage();
        if (mainStage != null) {
            mainStage.close();
        }
        stage.close();
    }

}
