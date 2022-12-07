package views;

import java.io.*;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.canvas.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.*;
import model.*;

public class GameOverView {
        TetrisModel model; //reference to model
        TetrisView view; //reference to view
        Stage stage;
        Scene backup_scene;

        Button resumeButton, settingsButton, newGameButton, mainMenuButton; //buttons for functions
        Button protanopiaButton, deuteranopiaButton, tritanopiaButton, defaultButton;

        Label title = new Label("");
        Label end_score = new Label("");
        Label template = new Label("");

        BorderPane borderPane;
        Canvas canvas;
        GraphicsContext gc; //the graphics context will be linked to the canvas

        Boolean paused;

        int pieceWidth = 20; //width of block on display
        private double width; //height and width of canvas
        private double height;
        final int score;

        /**
         * Constructor
         *
         * @param stage reference to stage
         *
         */

    public GameOverView(Stage stage, int totalScore){
        this.backup_scene = stage.getScene();
        this.score = totalScore;
        this.model = new TetrisModel(stage);
        this.view = new TetrisView(this.model, stage);
        this.stage = stage;

    }

    public void start(){
        gameoverUI();
    }
    private void gameoverUI(){

        //Configure the width and height of canvas
        this.width = this.model.getWidth() * pieceWidth + 2;
        this.height = this.model.getHeight() * pieceWidth + 2;

        //Create new borderpane
        borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: #121212;");

        //add canvas
        canvas = new Canvas(this.width, this.height);
        canvas.setId("Canvas");
        gc = canvas.getGraphicsContext2D();

        //Add the various buttons on the pause menu
        title.setText("Game Over");
        title.setId("game over");
        title.setFont(new Font(60));
        title.setStyle("-fx-text-fill: #e8e6e3");
        title.setAlignment(Pos.TOP_CENTER);

        end_score.setText("Score: " + score);
        end_score.setId("score");
        end_score.setFont(new Font(60));
        end_score.setStyle("-fx-text-fill: #e8e6e3");
        end_score.setAlignment(Pos.TOP_CENTER);

        newGameButton = new Button("New Game");
        newGameButton.setId("New Game");
        newGameButton.setPrefSize(180, 60);
        newGameButton.setFont(new Font(15));
        newGameButton.setStyle("-fx-background-color: #0000FF; -fx-text-fill: white;");

        settingsButton = new Button("Settings");
        settingsButton.setId("Settings");
        settingsButton.setPrefSize(180, 60);
        settingsButton.setFont(new Font(15));
        settingsButton.setStyle("-fx-background-color: #0000FF; -fx-text-fill: white;");

        mainMenuButton = new Button("Main Menu");
        mainMenuButton.setId("Main Menu");
        mainMenuButton.setPrefSize(180, 60);
        mainMenuButton.setFont(new Font(15));
        mainMenuButton.setStyle("-fx-background-color: #0000FF; -fx-text-fill: white;");


        // Align the display using inbuilt VBox javaFX layout
        Label temp = new Label("");
        VBox controls = new VBox(25, title, end_score, temp, newGameButton, settingsButton, mainMenuButton);
        controls.setPadding(new Insets(50, 20, 20, 20));
        controls.setAlignment(Pos.TOP_CENTER);

        //Execute what the buttons should do when pressed
        newGameButton.setOnAction(e -> {
            this.view.start();
            this.model.newGame();
            borderPane.requestFocus();
        });
        settingsButton.setOnAction(e -> {
            settings();
            borderPane.requestFocus();

        });
        mainMenuButton.setOnAction(e -> {
            this.model = new TetrisModel(stage); // create a model
            this.view = new TetrisView(model, this.stage); //tie the model to the view
            this.model.startGame(); //begin
            borderPane.requestFocus();
        });

        borderPane.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent k) {
                //TO DO
                if (k.getCode() == KeyCode.ESCAPE) {
                    paused = false;
                    stage.setScene(backup_scene);
                    stage.show();
                    model.unpauseGame();
                    borderPane.requestFocus();
                }
            }
        });

        // Set up the borderpane
        borderPane.setTop(controls);
        borderPane.setCenter(canvas);

        // Create and show the screen
        var scene = new Scene(borderPane, 800, 800);
        this.stage.setScene(scene);
        this.stage.show();
    }

    private void settings(){

        //Create new borderpane
        borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: #121212;");

        //add canvas
        canvas = new Canvas(this.width, this.height);
        canvas.setId("Canvas");
        gc = canvas.getGraphicsContext2D();

        //add buttons
        title.setText("Settings");
        title.setId("settings");
        title.setFont(new Font(60));
        title.setStyle("-fx-text-fill: #e8e6e3");
        title.setAlignment(Pos.TOP_CENTER);

        template.setText("Colourblind Templates");
        template.setId("templates");
        template.setFont(new Font(30));
        template.setStyle("-fx-text-fill: #e8e6e3");
        template.setAlignment(Pos.TOP_CENTER);

        resumeButton = new Button("Back");
        resumeButton.setId("Back");
        resumeButton.setPrefSize(180, 60);
        resumeButton.setFont(new Font(15));
        resumeButton.setStyle("-fx-background-color: #0000FF; -fx-text-fill: white;");

        protanopiaButton = new Button("Protanopia");
        protanopiaButton.setId("Protanopia");
        protanopiaButton.setPrefSize(180, 60);
        protanopiaButton.setFont(new Font(15));
        protanopiaButton.setStyle("-fx-background-color: #0000FF; -fx-text-fill: white;");

        deuteranopiaButton = new Button("Deuteranopia");
        deuteranopiaButton.setId("Deuteranopia");
        deuteranopiaButton.setPrefSize(180, 60);
        deuteranopiaButton.setFont(new Font(15));
        deuteranopiaButton.setStyle("-fx-background-color: #0000FF; -fx-text-fill: white;");

        tritanopiaButton = new Button("Tritanopia");
        tritanopiaButton.setId("Tritanopia");
        tritanopiaButton.setPrefSize(180, 60);
        tritanopiaButton.setFont(new Font(15));
        tritanopiaButton.setStyle("-fx-background-color: #0000FF; -fx-text-fill: white;");

        defaultButton = new Button("Default");
        defaultButton.setId("default");
        defaultButton.setPrefSize(180, 60);
        defaultButton.setFont(new Font(15));
        defaultButton.setStyle("-fx-background-color: #0000FF; -fx-text-fill: white;");

        // Align the display using inbuilt javaFX layouts
        Label temp = new Label("");
        Label temp2 = new Label("");
        Label temp3 = new Label("");
        HBox bot = new HBox(20, resumeButton);
        VBox controls = new VBox(25, bot, title, temp, temp2, template, temp3, protanopiaButton, deuteranopiaButton, tritanopiaButton, defaultButton);
        controls.setPadding(new Insets(50, 20, 20, 20));
        controls.setAlignment(Pos.TOP_CENTER);

        //Button to go back to pause menu
        resumeButton.setOnAction(e -> {
            gameoverUI();
            borderPane.requestFocus();
        });

        protanopiaButton.setOnAction(e -> {
            PrintWriter reset = null;
            try {
                reset = new PrintWriter("Settings.txt");
                reset.close();
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            this.view.writetoSettings("protanopia");
            this.view.paintBoard();
            borderPane.requestFocus();
        });

        deuteranopiaButton.setOnAction(e -> {
            PrintWriter reset = null;
            try {
                reset = new PrintWriter("Settings.txt");
                reset.close();
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            this.view.writetoSettings("deuteranopia");
            this.view.paintBoard();
            borderPane.requestFocus();
        });

        tritanopiaButton.setOnAction(e -> {
            PrintWriter reset = null;
            try {
                reset = new PrintWriter("Settings.txt");
                reset.close();
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            this.view.writetoSettings("tritanopia");
            this.view.paintBoard();
            borderPane.requestFocus();
        });

        defaultButton.setOnAction(e -> {
            PrintWriter reset = null;
            try {
                reset = new PrintWriter("Settings.txt");
                reset.close();
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            this.view.writetoSettings("default");
            this.view.paintBoard();
            borderPane.requestFocus();
        });


        // Set up the borderpane
        borderPane.setTop(controls);
        borderPane.setCenter(canvas);

        // Create and show the screen
        var scene = new Scene(borderPane, 800, 800);
        this.stage.setScene(scene);
        this.stage.show();

    }

        public Stage getStage(){
            return this.stage;
        }

        public TetrisView get_view(){
        return this.view;
        }

        public TetrisModel get_model(){
            return this.model;
        }
    }

