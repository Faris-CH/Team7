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

public class PauseMenu {
    TetrisModel model; //reference to model
    TetrisView view; //reference to view
    Stage stage;
    Scene backup_scene;

    Button resumeButton, settingsButton, newGameButton, saveButton, loadButton, mainMenuButton; //buttons for functions
    Button protanopiaButton, deuteranopiaButton, tritanopiaButton, defaultButton;

    Label title = new Label("");
    Label template = new Label("");

    BorderPane borderPane;
    Canvas canvas;
    GraphicsContext gc; //the graphics context will be linked to the canvas

    Boolean paused;

    int pieceWidth = 20; //width of block on display
    private double width; //height and width of canvas
    private double height;

    /**
     * Constructor
     *
     * @param view reference to tetris view
     * @param model reference to tetris model
     * @param stage reference to stage
     *
     */

    public PauseMenu(TetrisView view, TetrisModel model, Stage stage) {
        this.view = view;
        this.model = model;
        this.stage = stage;
        this.backup_scene = stage.getScene();
        this.paused = true;
        this.view.model.stopGame();
        pauseUI();
    }

    private void pauseUI(){

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
        title.setText("Paused");
        title.setId("paused");
        title.setFont(new Font(60));
        title.setStyle("-fx-text-fill: #e8e6e3");
        title.setAlignment(Pos.TOP_CENTER);

        resumeButton = new Button("Resume");
        resumeButton.setId("Resume");
        resumeButton.setPrefSize(180, 60);
        resumeButton.setFont(new Font(15));
        resumeButton.setStyle("-fx-background-color: #0000FF; -fx-text-fill: white;");

        newGameButton = new Button("New Game");
        newGameButton.setId("New Game");
        newGameButton.setPrefSize(180, 60);
        newGameButton.setFont(new Font(15));
        newGameButton.setStyle("-fx-background-color: #0000FF; -fx-text-fill: white;");

        saveButton = new Button("Save Game");
        saveButton.setId("Save Game");
        saveButton.setPrefSize(180, 60);
        saveButton.setFont(new Font(15));
        saveButton.setStyle("-fx-background-color: #0000FF; -fx-text-fill: white;");

        loadButton = new Button("Load Game");
        loadButton.setId("Load Game");
        loadButton.setPrefSize(180, 60);
        loadButton.setFont(new Font(15));
        loadButton.setStyle("-fx-background-color: #0000FF; -fx-text-fill: white;");

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
        VBox controls = new VBox(25, title, temp, resumeButton, newGameButton, saveButton, loadButton, settingsButton, mainMenuButton);
        controls.setPadding(new Insets(50, 20, 20, 20));
        controls.setAlignment(Pos.TOP_CENTER);

        //Execute what the buttons should do when pressed
        resumeButton.setOnAction(e -> {
            this.paused = false;
            this.stage.setScene(this.backup_scene);
            this.stage.show();
            this.model.unpauseGame();
            borderPane.requestFocus();
        });

        newGameButton.setOnAction(e -> {
            this.stage.setScene(this.backup_scene);
            try {
                this.model.newGame();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            borderPane.requestFocus();
        });

        saveButton.setOnAction(e -> {
            this.view.createSaveView();
            borderPane.requestFocus();
        });

        loadButton.setOnAction(e -> {
            this.view.createLoadView();
            borderPane.requestFocus();
        });

        settingsButton.setOnAction(e -> {
            settings();
            borderPane.requestFocus();

        });
        mainMenuButton.setOnAction(e -> {
            this.model = new TetrisModel(stage); // create a model
            try {
                this.view = new TetrisView(model, this.stage); //tie the model to the view
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            try {
                this.model.startGame(); //begin
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
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
            pauseUI();
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
}
