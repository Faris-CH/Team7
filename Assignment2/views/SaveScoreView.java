package views;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.function.Predicate;


public class SaveScoreView {
    static String saveFileSuccess = "Saved score!!";
    static String saveFileExistsError = "Error: Name already taken";
    private Label saveFileErrorLabel = new Label("");
    private Label saveBoardLabel = new Label(String.format("Enter name of file to save"));
    private TextField saveFileNameTextField = new TextField("highscores.ser");
    private Button saveBoardButton = new Button("Save board");
    TetrisView tetrisView;

    /**
     * Constructor
     *
     * @param tetrisView master view
     */
    public SaveScoreView(TetrisView tetrisView) {
        this.tetrisView = tetrisView;

        tetrisView.paused = true;
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(tetrisView.stage);
        VBox dialogVbox = new VBox(20);
        dialogVbox.setPadding(new Insets(20, 20, 20, 20));
        dialogVbox.setStyle("-fx-background-color: #121212;");

        saveBoardLabel.setId("SaveScore"); // DO NOT MODIFY ID
        saveFileErrorLabel.setId("SaveFileErrorLabel");
        saveFileNameTextField.setId("SaveFileNameTextField");
        saveBoardLabel.setStyle("-fx-text-fill: #e8e6e3;");
        saveBoardLabel.setFont(new Font(16));
        saveFileErrorLabel.setStyle("-fx-text-fill: #e8e6e3;");
        saveFileErrorLabel.setFont(new Font(16));
        saveFileNameTextField.setStyle("-fx-text-fill: #000000;");
        saveFileNameTextField.setFont(new Font(16));

        String boardName = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        saveFileNameTextField.setText(boardName);

        saveBoardButton = new Button("Save Score");
        saveBoardButton.setId("SaveScore"); // DO NOT MODIFY ID
        saveBoardButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");
        saveBoardButton.setPrefSize(200, 50);
        saveBoardButton.setFont(new Font(16));
        saveBoardButton.setOnAction(e -> saveScore());

        VBox saveBoardBox = new VBox(10, saveBoardLabel, saveFileNameTextField, saveBoardButton, saveFileErrorLabel);

        dialogVbox.getChildren().add(saveBoardBox);
        Scene dialogScene = new Scene(dialogVbox, 400, 400);
        dialog.setScene(dialogScene);
        dialog.show();
        dialog.setOnCloseRequest(event -> {
            tetrisView.paused = false;
        });

    }

    /**
     * Save the board to a file
     */
    public void saveScore() {

        File file = new File(".\\scores\\highscores.ser");

        String name = saveFileNameTextField.getText();

        if (tetrisView.scores.getHighScores().containsValue(name)){
            saveFileErrorLabel.setText(saveFileExistsError);
        } else {
            tetrisView.scores.addHighScore(saveFileNameTextField.getText(), tetrisView.model.getScore());

            tetrisView.scores.saveScores(file);
            saveFileErrorLabel.setText(saveFileSuccess);
        }
    }
}
