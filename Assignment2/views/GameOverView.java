package views;

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
        Button controlsButton, visualSettingsButton;

        Label title = new Label("");
        Label end_score = new Label("");

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

        resumeButton = new Button("Back");
        resumeButton.setId("Back");
        resumeButton.setPrefSize(180, 60);
        resumeButton.setFont(new Font(15));
        resumeButton.setStyle("-fx-background-color: #0000FF; -fx-text-fill: white;");

        controlsButton = new Button("Controls");
        controlsButton.setId("Controls");
        controlsButton.setPrefSize(180, 60);
        controlsButton.setFont(new Font(15));
        controlsButton.setStyle("-fx-background-color: #0000FF; -fx-text-fill: white;");

        visualSettingsButton = new Button("Display");
        visualSettingsButton.setId("Display");
        visualSettingsButton.setPrefSize(180, 60);
        visualSettingsButton.setFont(new Font(15));
        visualSettingsButton.setStyle("-fx-background-color: #0000FF; -fx-text-fill: white;");

        // Align the display using inbuilt javaFX layouts
        Label temp = new Label("");
        HBox bot = new HBox(20, resumeButton);
        VBox controls = new VBox(25, bot, title, temp, controlsButton, visualSettingsButton);
        controls.setPadding(new Insets(50, 20, 20, 20));
        controls.setAlignment(Pos.TOP_CENTER);

        //Button to go back to pause menu
        resumeButton.setOnAction(e -> {
            gameoverUI();
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

