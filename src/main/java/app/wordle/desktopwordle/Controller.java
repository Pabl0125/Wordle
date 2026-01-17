package app.wordle.desktopwordle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Rotate;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;


public class Controller {

    //Etiquetas del tablero
    @FXML private Label label00;
    @FXML private Label label01;
    @FXML private Label label02;
    @FXML private Label label03;
    @FXML private Label label04;

    @FXML private Label label10;
    @FXML private Label label11;
    @FXML private Label label12;
    @FXML private Label label13;
    @FXML private Label label14;

    @FXML private Label label20;
    @FXML private Label label21;
    @FXML private Label label22;
    @FXML private Label label23;
    @FXML private Label label24;

    @FXML private Label label30;
    @FXML private Label label31;
    @FXML private Label label32;
    @FXML private Label label33;
    @FXML private Label label34;

    @FXML private Label label40;
    @FXML private Label label41;
    @FXML private Label label42;
    @FXML private Label label43;
    @FXML private Label label44;

    @FXML private Label label50;
    @FXML private Label label51;
    @FXML private Label label52;
    @FXML private Label label53;
    @FXML private Label label54;


    // Botones del teclado
    @FXML private Button buttonQ;
    @FXML private Button buttonW;
    @FXML private Button buttonE;
    @FXML private Button buttonR;
    @FXML private Button buttonT;
    @FXML private Button buttonY;
    @FXML private Button buttonU;
    @FXML private Button buttonI;
    @FXML private Button buttonO;
    @FXML private Button buttonP;

    @FXML private Button buttonA;
    @FXML private Button buttonS;
    @FXML private Button buttonD;
    @FXML private Button buttonF;
    @FXML private Button buttonG;
    @FXML private Button buttonH;
    @FXML private Button buttonJ;
    @FXML private Button buttonK;
    @FXML private Button buttonL;
    @FXML private Button buttonNspecial;

    @FXML private Button buttonEnter;
    @FXML private Button buttonZ;
    @FXML private Button buttonX;
    @FXML private Button buttonC;
    @FXML private Button buttonV;
    @FXML private Button buttonB;
    @FXML private Button buttonN;
    @FXML private Button buttonM;
    @FXML private Button buttonDel;

    @FXML private Label warningLabel;

    @FXML private AnchorPane scenePane;
    @FXML private AnchorPane popUpPane;

    private Button[] keyboardButtons;
    private Label[][] cuadricula;
    private int currentRow = 0;
    private int currentColumn = 0;

    List<String> wordleWordList = new ArrayList<>();
    String correctWord;
    int validCharacterCounter=0;

    @FXML
    public void initialize() {
        keyboardButtons = new Button[] {
                buttonQ, buttonW, buttonE, buttonR, buttonT, buttonY, buttonU, buttonI, buttonO, buttonP,
                buttonA, buttonS, buttonD, buttonF, buttonG, buttonH, buttonJ, buttonK, buttonL, buttonNspecial,
                buttonEnter, buttonZ, buttonX, buttonC, buttonV, buttonB, buttonN, buttonM, buttonDel
        };
        cuadricula = new Label[][] {
                { label00, label01, label02, label03, label04 },
                { label10, label11, label12, label13, label14 },
                { label20, label21, label22, label23, label24 },
                { label30, label31, label32, label33, label34 },
                { label40, label41, label42, label43, label44 },
                { label50, label51, label52, label53, label54 }
        };
        updateCurrentCellBorder();
        importWords(wordleWordList);
        chooseRandomWord(wordleWordList);
    }


    public void importWords(List<String> lista) {
        try (BufferedReader br = new BufferedReader( new InputStreamReader(getClass().getResourceAsStream("wordleWords.txt")))) {

            String linea;
            while ((linea = br.readLine()) != null) {
                if(linea.length() == 5 && linea.equals(linea.toLowerCase())) {
                    lista.add(linea);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void chooseRandomWord(List<String> lista) {
        int max = lista.size();
        int min = 1;
        int randomIndex = (int)(Math.random() * (max - min + 1)) + min;
        correctWord = lista.get(randomIndex);
    }

    @FXML
    public void pressedLetter(ActionEvent event) {
        if (currentColumn < 5) {
            Button boton = (Button) event.getSource();
            String letra = boton.getText();
            cuadricula[currentRow][currentColumn].setText(letra);
            currentColumn++;
            updateCurrentCellBorder();

        }
    }
    @FXML
    public void delete() {
        if (currentColumn > 0) {
            currentColumn--;
            cuadricula[currentRow][currentColumn].setText("");
            updateCurrentCellBorder();
        }
    }

    @FXML
    public void enter() {

        if (currentColumn >= 5) {
            if (wordVerification()) {
                if(validCharacterCounter==5) {
                    secondaryStagePopUp("Victoria!!");
                }
                else if(validCharacterCounter!=5 && currentRow!=5) {
                    currentRow++;
                    currentColumn = 0;
                    updateCurrentCellBorder();
                }
                else if (validCharacterCounter!=5 && currentRow==5) {
                    PauseTransition waitAnimation = new PauseTransition(Duration.millis(2000));
                    waitAnimation.setOnFinished(event -> Platform.runLater(() ->
                            secondaryStagePopUp("Derrota, respuesta: " + correctWord)
                    ));
                    waitAnimation.play();

                }
            }
        }
    }
    public boolean wordVerification() {
        validCharacterCounter=0;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            sb.append(cuadricula[currentRow][i].getText());
        }
        String inputWord = removeAccents(sb.toString().toLowerCase());

        if (wordleWordList.contains(inputWord)) {
            warningLabel.setText("Palabra valida");
            PauseTransition pause = new PauseTransition(Duration.seconds(2));
            pause.setOnFinished(event -> warningLabel.setText(""));
            pause.play();
            final int row = currentRow;
            for (int i = 0; i < 5; i++) {
                final int index = i;
                String color;
                if (inputWord.charAt(i) == correctWord.charAt(i)) {
                    color = "#5da348";
                    validCharacterCounter++;
                } else if (correctWord.contains("" + inputWord.charAt(i))) {
                    color = "#c49300";
                } else {
                    color = "#3a3a3c";
                }

                final String finalColor = color;

                RotateTransition firstHalf = new RotateTransition(Duration.millis(250), cuadricula[row][index]);
                firstHalf.setFromAngle(0);
                firstHalf.setToAngle(90);
                firstHalf.setAxis(Rotate.X_AXIS);
                firstHalf.setDelay(Duration.millis(index * 250));

                RotateTransition secondHalf = new RotateTransition(Duration.millis(250), cuadricula[row][index]);
                secondHalf.setFromAngle(90);
                secondHalf.setToAngle(0);
                secondHalf.setAxis(Rotate.X_AXIS);

                firstHalf.setOnFinished(event -> {
                    cuadricula[row][index].setStyle(
                            "-fx-background-color: " + finalColor + ";" +
                                    "-fx-border-width:2px;" +
                                    "-fx-border-radius:10%;" +
                                    "-fx-background-radius:10%;" +
                                    "-fx-border-color:#787c7f;" +
                                    "-fx-max-height:70px;" +
                                    "-fx-max-width:70px;" +
                                    "-fx-text-fill:white;" +
                                    "-fx-font-weight:bold;" +
                                    "-fx-font-size:25px;"
                    );
                    secondHalf.play();
                });
                secondHalf.setOnFinished(event -> {
                    paintKeyonBoard(inputWord.charAt(index), finalColor);
                });
                firstHalf.play();
            }
            return true;
        } else {
            warningLabel.setText("Palabra no encontrada en el diccionario");
            PauseTransition pause = new PauseTransition(Duration.seconds(2));
            pause.setOnFinished(event -> warningLabel.setText(""));
            pause.play();
            return false;
        }
    }
    public void insertKeyboardInput(String letra) {
        if (currentColumn < 5) {
            cuadricula[currentRow][currentColumn].setText(letra);
            currentColumn++;
            updateCurrentCellBorder();
        }
    }

    public void initKeyListener(Scene scene) {
        scene.setOnKeyPressed(event -> {
            KeyCode code = event.getCode();

            if (code == KeyCode.ENTER) {
                enter();
            }
            else if (code == KeyCode.BACK_SPACE || code == KeyCode.DELETE) {
                delete();
            }
            else {
                String text = event.getText();
                if (!text.isEmpty() && text.matches("[a-zA-ZñÑ]")) { // acepta ñ y letras
                    String letra = text.toUpperCase();
                    insertKeyboardInput(letra);
                }
            }
        });

        scenePane.setFocusTraversable(true);
        scenePane.requestFocus();
    }

    private void updateCurrentCellBorder() {
        for (int i = 0; i < cuadricula.length; i++) {
            for (int j = 0; j < cuadricula[i].length; j++) {
                Label cell = cuadricula[i][j];

                // Si la celda ya tiene color de fondo, mantenlo
                String bgColor = "";
                if (cell.getStyle().contains("#5da348")) bgColor = "-fx-background-color:#5da348;";
                else if (cell.getStyle().contains("#c49300")) bgColor = "-fx-background-color:#c49300;";
                else if (cell.getStyle().contains("#3a3a3c")) bgColor = "-fx-background-color:#3a3a3c;";

                // Todas las celdas pierden el borde azul excepto la actual
                cell.setStyle(
                        "-fx-border-width:2px;" +
                                "-fx-border-radius:5%;" +
                                "-fx-background-radius: 5%;"+
                                "-fx-border-color:#787c7f;" +
                                "-fx-max-height:70px;" +
                                "-fx-max-width:70px;" +
                                "-fx-text-fill:white;" +
                                "-fx-font-weight:bold;" +
                                "-fx-font-size:25px;" +
                                bgColor
                );
            }
        }
        // Resaltar la celda actual con borde azul
        if (currentRow < cuadricula.length && currentColumn < cuadricula[0].length) {
            Label cell = cuadricula[currentRow][currentColumn];

            // Mantener fondo si existe
            String bgColor = "";
            if (cell.getStyle().contains("#5da348")) bgColor = "-fx-background-color:#5da348;";
            else if (cell.getStyle().contains("#c49300")) bgColor = "-fx-background-color:#c49300;";

            cell.setStyle(
                    "-fx-border-width:2px;" +
                            "-fx-border-radius:5%;" +
                            "-fx-background-radius:5%;"+
                            "-fx-border-color:lightblue;" +  // borde de cursor
                            "-fx-max-height:70px;" +
                            "-fx-max-width:70px;" +
                            "-fx-text-fill:white;" +
                            "-fx-font-weight:bold;" +
                            "-fx-font-size:25px;" +
                            bgColor
            );
        }
    }

    private void secondaryStagePopUp(String message) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PopUpScene.fxml"));
            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add(getClass().getResource("applicationPopUp.css").toExternalForm());
            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL); // bloquea la ventana principal
            popupStage.setTitle(message);
            popupStage.setResizable(false);
            popupStage.setScene(scene);

            // Pasamos el stage al controlador del popup
            PopUpController controller = loader.getController();
            controller.setStage(popupStage,message);
            controller.setMainController(this);

            popupStage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void resetBoard() {
        // 1. Limpiar toda la cuadrícula
        for (int i = 0; i < cuadricula.length; i++) {
            for (int j = 0; j < cuadricula[i].length; j++) {
                Label cell = cuadricula[i][j];
                cell.setText("");
                cell.setStyle(
                        "-fx-border-width:2px;" +
                                "-fx-border-radius:5%;" +
                                "-fx-background-radius:5%;"+
                                "-fx-border-color:#787c7f;" +
                                "-fx-max-height:70px;" +
                                "-fx-max-width:70px;" +
                                "-fx-text-fill:white;" +
                                "-fx-font-weight:bold;" +
                                "-fx-font-size:25px;"
                );
            }
        }

        currentRow = 0;
        currentColumn = 0;
        validCharacterCounter = 0;

        chooseRandomWord(wordleWordList);
        updateCurrentCellBorder();
    }
    public String removeAccents(String word) {
        return word
                .replaceAll("[áàäâ]", "a")
                .replaceAll("[éèëê]", "e")
                .replaceAll("[íìïî]", "i")
                .replaceAll("[óòöô]", "o")
                .replaceAll("[úùüû]", "u");
    }


    public void paintKeyonBoard(char letter, String color) {
        for (Button boton : keyboardButtons) {
            if (boton.getText().equalsIgnoreCase(String.valueOf(letter))) {
                String currentStyle = boton.getStyle();

                boolean isGreen = currentStyle.contains("#5da348");
                boolean isYellow = currentStyle.contains("#c49300");

                if (color.equals("#5da348") || (!isGreen && color.equals("#c49300")) || (!isGreen && !isYellow)) {
                    boton.setStyle(
                            "-fx-text-fill: white;" +
                                    "-fx-background-color:" + color + ";" +
                                    "-fx-font-size: 18px;" +
                                    "-fx-font-weight: bold;" +
                                    "-fx-border-color: lightgrey;" +
                                    "-fx-border-width: 2px;" +
                                    "-fx-border-radius: 10px;" +
                                    "-fx-background-radius: 10px;"
                    );
                }
            }
        }
    }

    public void resetButtons() {
        for (Button boton: keyboardButtons) {
            boton.setStyle(
                    "-fx-text-fill: white;" +
                            "-fx-background-color: #787c7f;" +
                            "-fx-font-size: 18px;" +
                            "-fx-font-weight: bold;" +
                            "-fx-border-color: lightgrey;" +
                            "-fx-border-width: 2px;" +
                            "-fx-border-radius: 10px;" +
                            "-fx-background-radius: 10px;"
            );
        }
    }
}
