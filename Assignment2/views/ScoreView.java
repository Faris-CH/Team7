package views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import model.TetrisModel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.TetrisScores;

import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

public class ScoreView {

    private TetrisView tetrisView;
    private Label selectBoardLabel;
    private ListView<String> scoresList;

    private TetrisScores scores;

    private TableView table = new TableView();


    public ScoreView (TetrisView tetrisView) throws IOException {
        TetrisScores highScores = this.getScores();

        tetrisView.paused = true;
        this.tetrisView = tetrisView;
        selectBoardLabel = new Label(String.format("High Scores"));
        scoresList = new ListView<>(); //list of tetris.boards

        final Stage dialog = new Stage(); //dialogue box
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(tetrisView.stage);
        VBox dialogVbox = new VBox(20);
        dialogVbox.setPadding(new Insets(20, 20, 20, 20));
        dialogVbox.setStyle("-fx-background-color: #121212;");

        selectBoardLabel.setId("CurrentBoard"); // DO NOT MODIFY ID

        scoresList.setId("scoresList");  // DO NOT MODIFY ID
        scoresList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        setScores(scoresList); //get scores for score viewer

        VBox selectBoardBox = new VBox(10, selectBoardLabel, scoresList);

        // Default styles which can be modified
        scoresList.setPrefHeight(100);

        selectBoardLabel.setStyle("-fx-text-fill: #e8e6e3");
        selectBoardLabel.setFont(new Font(16));

        selectBoardBox.setAlignment(Pos.CENTER);

        dialogVbox.getChildren().add(selectBoardBox);
        Scene dialogScene = new Scene(dialogVbox, 400, 400);
        dialog.setScene(dialogScene);
        dialog.show();
        dialog.setOnCloseRequest(event -> {
            tetrisView.paused = false;
        });
    }

    private TetrisScores getScores() throws IOException {
        FileInputStream file = null;
        ObjectInputStream in = null;
        File boardfile = new File("scores/highscores.ser");
//        File boardfile = new File(".\\scores\\highscores.ser");
        try {
            file = new FileInputStream(boardfile);
            in = new ObjectInputStream(file);
            return (TetrisScores) in.readObject();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            in.close();
            file.close();
        }
    }

    private void setScores(ListView<String> listView) throws IOException {
        TetrisScores highScores = this.getScores();

        ArrayList<String> scoresToAdd = new ArrayList<>();

        scoresToAdd.add("Name \t    Scores");
        for (Object i:
                highScores.getHighScores().keySet()) {
            scoresToAdd.add(i + "  ".repeat(10 - ((String) i).length()) + highScores.getHighScores().get(i));
        }

        ObservableList<String> lst = FXCollections.observableArrayList(scoresToAdd);
        listView.setItems(lst);
    }
}
