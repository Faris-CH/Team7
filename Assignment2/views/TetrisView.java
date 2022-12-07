package views;


import ColourBlindMode.*;

import ColourBlindMode.Protanopia;
import ColourBlindMode.Deuteranopia;
import ColourBlindMode.Tritanopia;

import java.io.*;
import java.util.*;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;
import javafx.stage.*;
import model.TetrisModel;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;


import java.io.IOException;
import java.io.File;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;



/**
 * Tetris View
 *
 * Based on the Tetris assignment in the Nifty Assignments Database, authored by Nick Parlante
 */
public class TetrisView {

    TetrisModel model; //reference to model
    Stage stage;

    Button menustartButton, instructionsButton, settingsButton, soundButton, loadButton;


    Button backButton, protanopiaButton, deuteranopiaButton, tritanopiaButton, defaultButton;


    Button startButton, stopButton, loadButton, saveButton, newButton, soundButton; //buttons for functions
    Clip currentlyPlaying; // audio clip object to play background music

    Label scoreLabel = new Label("");
    Label gameModeLabel = new Label("");
    Label template = new Label("");

    Label title = new Label("");

    Label mode_type = new Label("");

    BorderPane borderPane;
    Canvas canvas;
    GraphicsContext gc; //the graphics context will be linked to the canvas

    Boolean paused;
    Timeline timeline;
    File settings;

    int pieceWidth = 20; //width of block on display
    private double width; //height and width of canvas
    private double height;
    private String mode = "";

    /**
     * Constructor
     *
     * @param model reference to tetris model
     * @param stage application stage
     */

    public TetrisView(TetrisModel model, Stage stage) {
        this.model = model;
        this.stage = stage;
        
        createfile();
        try {
            File myObj = new File("views","Settings.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String next = myReader.nextLine();
                if (next == null){
                    this.mode = "default";
                    this.mode_type.setText("Mode: default");

                }
                else{
                    this.mode = next;
                    this.mode_type.setText("Mode: " + next);
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        menuUi();
    }

    public void createfile() { //Creates the colourblind settings file
        try {
            File setting = new File("views","Settings.txt");
            this.settings = setting;
            if (setting.createNewFile()){
                System.out.println("File created: " + setting.getName());
            }
        }
        catch (IOException e){
            System.out.println("Error Occurred");
        }

    }
        menuUi();
    }




    public void writetoSettings(String new_mode){
        try{
            FileWriter mywrite = new FileWriter("views/Settings.txt");
            mywrite.write(new_mode);
            mywrite.close();
            this.mode = new_mode;

        }
        catch (IOException e){
            System.out.println("An error occurred");
        }
    }

    /**
     * Initialize interface
     */

    //UI For The New Menu
    private void menuUi() {
        this.paused = true;
        this.stage.setTitle("Tetris Unlocked");
        this.width = this.model.getWidth() * pieceWidth + 2;
        this.height = this.model.getHeight() * pieceWidth + 2;

        borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: #121212;");

        //add canvas
        canvas = new Canvas(this.width, this.height);
        canvas.setId("Canvas");
        gc = canvas.getGraphicsContext2D();

        //add buttons
        menustartButton = new Button("New Game");
        menustartButton.setId("StartMenu");
        menustartButton.setPrefSize(225, 75);
        menustartButton.setFont(new Font(15));
        menustartButton.setStyle("-fx-background-color: #0000FF; -fx-text-fill: white;");

        loadButton = new Button("Load");
        loadButton.setId("Load");
        loadButton.setPrefSize(225, 75);
        loadButton.setFont(new Font(15));
        loadButton.setStyle("-fx-background-color: #0000FF; -fx-text-fill: white;");

        settingsButton = new Button("Settings");
        settingsButton.setId("Settings");
        settingsButton.setPrefSize(225, 75);
        settingsButton.setFont(new Font(15));
        settingsButton.setStyle("-fx-background-color: #0000FF; -fx-text-fill: white;");

        instructionsButton = new Button("Instructions");
        instructionsButton.setId("Instructions");
        instructionsButton.setPrefSize(225, 75);
        instructionsButton.setFont(new Font(15));
        instructionsButton.setStyle("-fx-background-color: #0000FF; -fx-text-fill: white;");

        soundButton = new Button("Sound: On");
        soundButton.setId("sound");
        soundButton.setPrefSize(180, 60);
        soundButton.setFont(new Font(15));
        soundButton.setStyle("-fx-background-color: #0000FF; -fx-text-fill: white;");
        soundButton.setLayoutX(200);
        soundButton.setLayoutY(200);

        title.setText("Tetris Unlocked");
        title.setId("title");
        Font font = Font.font("verdana", FontWeight.BOLD, 60);
//        title.setFont(new Font(60));
        title.setFont(font);
        title.setStyle("-fx-text-fill: #e8e6e3");
        title.setAlignment(Pos.TOP_CENTER);

        //Align the display using inbuilt JavaFX VBox layout
        Label temp = new Label("");
        Label temp2 = new Label("");
        VBox controls = new VBox(25, title, temp, temp2, menustartButton, loadButton, settingsButton, instructionsButton);
        controls.setPadding(new Insets(120, 20, 20, 20));
        controls.setAlignment(Pos.TOP_CENTER);

        //configure this such that you start a new game when the user hits the newButton
        menustartButton.setOnAction(e -> {
            initUI();
            this.model.newGame();
            borderPane.requestFocus();
        });
        //configure this such that the load view pops up when the loadButton is pressed.
        loadButton.setOnAction(e -> {
            this.createLoadView();
            borderPane.requestFocus();
        });
        //configure this such that the settings view pops up when the settingsButton is pressed.
        settingsButton.setOnAction(e -> {
            settingsUI();
            borderPane.requestFocus();
        });
        //configure this such that the instructions view pops up when the instructionsButton is pressed.
        instructionsButton.setOnAction(e -> {
            instructionsUI();
            borderPane.requestFocus();
        });
        //configure this such that the sound turns on or off when soundButton is pressed.
        soundButton.setOnAction(e -> {
            if (soundButton.getText().equalsIgnoreCase("sound: off")) {
                soundButton.setText("Sound: On");
            } else {
                soundButton.setText("Sound: Off");
            }
            borderPane.requestFocus();
        });


        borderPane.setTop(controls);
        borderPane.setCenter(canvas);

        HBox bot = new HBox(20, soundButton);
        controls.getChildren().add(bot);

        controls.setBackground(Background.fill(new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE, new Stop(0, Color.AQUA), new Stop(0.5, Color.INDIANRED), new Stop(1, Color.LIMEGREEN))));
        var scene = new Scene(borderPane, 800, 800, Color.GREEN);
//        scene.setFill(new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE, new Stop(0, Color.GREEN), new Stop(.5, Color.web("81c483"))));
        scene.setFill(Color.GREEN);
        this.stage.setScene(scene);
        this.stage.show();
    }


//Class For The Settings UI
    private void settingsUI() {
        this.paused = false;
        this.stage.setTitle("Tetris Unlocked");
        this.width = this.model.getWidth() * pieceWidth + 2;
        this.height = this.model.getHeight() * pieceWidth + 2;

        borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: #121212;");

        //add canvas
        canvas = new Canvas(this.width, this.height);
        canvas.setId("Canvas");
        gc = canvas.getGraphicsContext2D();


        //add buttons
        template.setText("Colourblind Templates");
        template.setId("templates");
        template.setFont(new Font(30));
        template.setStyle("-fx-text-fill: #e8e6e3");
        template.setAlignment(Pos.TOP_CENTER);


        backButton = new Button("Back");
        backButton.setId("Back");
        backButton.setPrefSize(180, 60);
        backButton.setFont(new Font(15));
        backButton.setStyle("-fx-background-color: #0000FF; -fx-text-fill: white;");

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
        HBox bot = new HBox(20, backButton);

        VBox controls = new VBox(25, bot, title, temp, temp2, template, temp3, protanopiaButton, deuteranopiaButton, tritanopiaButton, defaultButton);

        controls.setPadding(new Insets(50, 20, 20, 20));
        controls.setAlignment(Pos.TOP_CENTER);

        //Button to go back to pause menu

        backButton.setOnAction(e -> {
            menuUi();
            borderPane.requestFocus();
        });

        //Button to configure protanopia template
        protanopiaButton.setOnAction(e -> {
            PrintWriter reset = null;
            try {
                reset = new PrintWriter("Settings.txt");
                reset.close();
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            this.writetoSettings("protanopia");
            borderPane.requestFocus();
        });

        //Button to configure deuteranopia template
        deuteranopiaButton.setOnAction(e -> {
            PrintWriter reset = null;
            try {
                reset = new PrintWriter("Settings.txt");
                reset.close();
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            this.writetoSettings("deuteranopia");
            borderPane.requestFocus();
        });

        //Button to configure tritanopia template
        tritanopiaButton.setOnAction(e -> {
            PrintWriter reset = null;
            try {
                reset = new PrintWriter("Settings.txt");
                reset.close();
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            this.writetoSettings("tritanopia");
            borderPane.requestFocus();
        });

        // Button when pressed goes back to default colour template.
        defaultButton.setOnAction(e -> {
            PrintWriter reset = null;
            try {
                reset = new PrintWriter("Settings.txt");
                reset.close();
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            this.writetoSettings("default");
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


//UI Class For The Instructions Page
    private void instructionsUI(){
        this.paused = false;
        this.stage.setTitle("Tetris Unlocked");
        this.width = this.model.getWidth() * pieceWidth + 2;
        this.height = this.model.getHeight() * pieceWidth + 2;

        borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: #121212;");

        //add canvas
        canvas = new Canvas(this.width, this.height);
        canvas.setId("Canvas");
        gc = canvas.getGraphicsContext2D();

        //add buttons
        backButton = new Button("Back");
        backButton.setId("Back");
        backButton.setPrefSize(180, 60);
        backButton.setFont(new Font(15));
        backButton.setStyle("-fx-background-color: #0000FF; -fx-text-fill: white;");

        title.setText("How To Play");
        title.setId("title");
        title.setFont(new Font(60));
        title.setStyle("-fx-text-fill: #e8e6e3");
        title.setAlignment(Pos.TOP_CENTER);

        Label instructions = new Label(" " +
                "The aim in Tetris is simple; you bring down blocks from the top of the screen. You can move the blocks around, either left to " +
                "right and/or you can rotate them. The blocks fall at a certain rate, but you can make them fall faster " +
                "if you’re sure of your positioning. Your objective is to get all the blocks to fill all the empty space in a " +
                "line at the bottom of the screen; whenever you do this, you’ll find that the blocks vanish and you get awarded some points.");
        instructions.setId("instructions");
        instructions.setFont(new Font(30));
        instructions.setStyle("-fx-text-fill: #e8e6e3");
        instructions.setAlignment(Pos.CENTER);
        instructions.setWrapText(true);


        Label temp = new Label("");
        Label temp2 = new Label("");
        HBox bot = new HBox(20, backButton);
        VBox controls = new VBox(25, bot, title, temp, temp2, instructions);
        controls.setPadding(new Insets(50, 20, 20, 20));
        controls.setAlignment(Pos.TOP_CENTER);

        //configure this such that you restart the game when the user hits the startButton
        //Make sure to return the focus to the borderPane once you're done!
        backButton.setOnAction(e -> {
            menuUi();
            borderPane.requestFocus();
        });

        borderPane.setTop(controls);
        borderPane.setCenter(canvas);

        var scene = new Scene(borderPane, 800, 800);
        this.stage.setScene(scene);
        this.stage.show();
    }



    public void start(){
        initUI();
    }//Getter to grab the game initialization UI.

    public void start(){
        initUI();
    }

    private void initUI() {
        this.paused = false;
        this.stage.setTitle("Tetris Unlocked");

        borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: #121212;");

        //add canvas
        canvas = new Canvas(this.width, this.height);
        canvas.setId("Canvas");
        gc = canvas.getGraphicsContext2D();

        //labels
        gameModeLabel.setId("GameModeLabel");
        scoreLabel.setId("ScoreLabel");

        gameModeLabel.setText("Player is: Human");
        gameModeLabel.setMinWidth(250);
        gameModeLabel.setFont(new Font(20));
        gameModeLabel.setStyle("-fx-text-fill: #e8e6e3");

        final ToggleGroup toggleGroup = new ToggleGroup();

        RadioButton pilotButtonHuman = new RadioButton("Human");
        pilotButtonHuman.setToggleGroup(toggleGroup);
        pilotButtonHuman.setSelected(true);
        pilotButtonHuman.setUserData(Color.SALMON);
        pilotButtonHuman.setFont(new Font(16));
        pilotButtonHuman.setStyle("-fx-text-fill: #e8e6e3");

        RadioButton pilotButtonComputer = new RadioButton("Computer (Default)");
        pilotButtonComputer.setToggleGroup(toggleGroup);
        pilotButtonComputer.setUserData(Color.SALMON);
        pilotButtonComputer.setFont(new Font(16));
        pilotButtonComputer.setStyle("-fx-text-fill: #e8e6e3");

        scoreLabel.setText("Score is: 0");
        scoreLabel.setFont(new Font(20));
        scoreLabel.setStyle("-fx-text-fill: #e8e6e3");

        //add buttons
        startButton = new Button("Start");
        startButton.setId("Start");
        startButton.setPrefSize(150, 50);
        startButton.setFont(new Font(12));
        startButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");

        stopButton = new Button("Stop");
        stopButton.setId("Start");
        stopButton.setPrefSize(150, 50);
        stopButton.setFont(new Font(12));
        stopButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");

        saveButton = new Button("Save");
        saveButton.setId("Save");
        saveButton.setPrefSize(150, 50);
        saveButton.setFont(new Font(12));
        saveButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");

        loadButton = new Button("Load");
        loadButton.setId("Load");
        loadButton.setPrefSize(150, 50);
        loadButton.setFont(new Font(12));
        loadButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");

        newButton = new Button("New Game");
        newButton.setId("New");
        newButton.setPrefSize(150, 50);
        newButton.setFont(new Font(12));
        newButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");

        // 2.1 - Initializing info for background music player
        soundButton = new Button("Soundtrack");
        soundButton.setId("Select Sound");
        soundButton.setPrefSize(150, 50);
        soundButton.setFont(new Font(12));
        soundButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");

        HBox controls = new HBox(20, saveButton, loadButton, newButton, startButton, stopButton, soundButton);
        controls.setPadding(new Insets(20, 20, 20, 20));
        controls.setAlignment(Pos.CENTER);

        Slider slider = new Slider(0, 100, 50);
        slider.setShowTickLabels(true);
        slider.setStyle("-fx-control-inner-background: palegreen;");

        VBox vBox = new VBox(20, slider);
        vBox.setPadding(new Insets(20, 20, 20, 20));
        vBox.setAlignment(Pos.TOP_CENTER);


        title.setText("Tetris Unlocked");
        title.setId("type");
        title.setFont(new Font(55));
        title.setStyle("-fx-text-fill: #e8e6e3");
        title.setAlignment(Pos.TOP_CENTER);

        mode_type.setId("type");
        mode_type.setFont(new Font(40));
        mode_type.setStyle("-fx-text-fill: #e8e6e3");
        mode_type.setAlignment(Pos.TOP_CENTER);
        Label temp = new Label("");
        Label temp2 = new Label("");
        HBox top = new HBox(20, temp, temp2, title);
        VBox scoreBox = new VBox(20, scoreLabel, mode_type, gameModeLabel, pilotButtonHuman, pilotButtonComputer);

        scoreBox.setPadding(new Insets(20, 20, 20, 20));
        vBox.setAlignment(Pos.TOP_CENTER);

        toggleGroup.selectedToggleProperty().addListener((observable, oldVal, newVal) -> swapPilot(newVal));

        //timeline structures the animation, and speed between application "ticks"
        timeline = new Timeline(new KeyFrame(Duration.seconds(0.25), e -> updateBoard()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();


        //configure this such that you start a new game when the user hits the newButton
        //Make sure to return the focus to the borderPane once you're done!
        newButton.setOnAction(e -> {

            model.newGame();
            borderPane.requestFocus();
        });

        //configure this such that you restart the game when the user hits the startButton
        //Make sure to return the focus to the borderPane once you're done!
        startButton.setOnAction(e -> {
            paused = false;
            borderPane.requestFocus();
        });

        //configure this such that you pause the game when the user hits the stopButton
        //Make sure to return the focus to the borderPane once you're done!
        stopButton.setOnAction(e -> {
            paused = true;
            borderPane.requestFocus();
        });

        //configure this such that the save view pops up when the saveButton is pressed.
        //Make sure to return the focus to the borderPane once you're done!
        saveButton.setOnAction(e -> {
            createSaveView();
            borderPane.requestFocus();
        });

        //configure this such that the load view pops up when the loadButton is pressed.
        //Make sure to return the focus to the borderPane once you're done!
        loadButton.setOnAction(e -> {
            createLoadView();
            borderPane.requestFocus();
        });

        //configure this such that the background music begins to play when the soundButton is pressed.
        // Pressing an additional time pauses music and another press resumes.
        // Such a functionality takes care of user story 1.2 (as pausing/sound off is achieved) as well
        //Make sure to return the focus to the borderPane once you're done!
        soundButton.setOnAction(e -> {
            if (currentlyPlaying == null) {
                try {
                    selectSoundtrackView();
                } catch (UnsupportedAudioFileException a) {
                    System.out.println("Unsupported file");
                    a.printStackTrace();
                } catch (IOException i) {
                    System.out.println("File not Found");
                    i.printStackTrace();
                } catch (LineUnavailableException l) {
                    System.out.println("line unavailable");
                    l.printStackTrace();
                }
                soundButton.setText("Playing");
            } else {
                if (currentlyPlaying.isActive()) {
                    currentlyPlaying.stop();
                    soundButton.setText("Paused");
                } else {
                    currentlyPlaying.start();
                    soundButton.setText("Playing");
                }
            }

            borderPane.requestFocus();
        });

        //configure this such that you adjust the speed of the timeline to a value that
        //ranges between 0 and 3 times the default rate per model tick.  Make sure to return the
        //focus to the borderPane once you're done!
        slider.setOnMouseReleased(e -> {
            //TO DO
            double value = slider.getValue() / 100;
            timeline.setRate(value * 3);
            this.borderPane.requestFocus();

        });

        //configure this such that you can use controls to rotate and place pieces as you like!!
        //You'll want to respond to tie key presses to these moves:
        // TetrisModel.MoveType.DROP, TetrisModel.MoveType.ROTATE, TetrisModel.MoveType.LEFT
        //and TetrisModel.MoveType.RIGHT
        //make sure that you don't let the human control the board
        //if the autopilot is on, however.
        borderPane.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent k) {
                //TO DO
                if (!model.getAutoPilotMode()){
                    if (k.getCode() == KeyCode.A){
                        model.modelTick(TetrisModel.MoveType.LEFT);

                    }
                    if (k.getCode() == KeyCode.S){
                        model.modelTick(TetrisModel.MoveType.DROP);
                    }
                    if (k.getCode() == KeyCode.D){
                        model.modelTick(TetrisModel.MoveType.RIGHT);
                    }
                    if (k.getCode() == KeyCode.W){
                        model.modelTick(TetrisModel.MoveType.ROTATE);
                    }

                }
                if (k.getCode() == KeyCode.ESCAPE){
                    createPauseMenu();
                }
            }
        });

        borderPane.setRight(scoreBox);
        borderPane.setCenter(canvas);
        borderPane.setBottom(vBox);
        borderPane.setTop(top);


        var scene = new Scene(borderPane, 800, 800);
        this.stage.setScene(scene);
        this.stage.show();
    }

    /**
     * Get user selection of "autopilot" or human player
     *
     * @param value toggle selector on UI
     */
    private void swapPilot(Toggle value) {
        RadioButton chk = (RadioButton)value.getToggleGroup().getSelectedToggle();
        String strVal = chk.getText();
        if (strVal.equals("Computer (Default)")){
            this.model.setAutoPilotMode();
            gameModeLabel.setText("Player is: Computer (Default)");
        } else if (strVal.equals("Human")) {
            this.model.setHumanPilotMode();
            gameModeLabel.setText("Player is: Human");
        }
        borderPane.requestFocus(); //give the focus back to the pane with the blocks.
    }

    /**
     * Update board (paint pieces and score info)
     */
    private void updateBoard() {
        if (this.paused != true) {
            paintBoard();
            this.model.modelTick(TetrisModel.MoveType.DOWN);
            updateScore();
        }
    }

    /**
     * Update score on UI
     */
    private void updateScore() {
        if (this.paused != true) {
            scoreLabel.setText("Score is: " + model.getScore() + "\nPieces placed:" + model.getCount());
        }
    }

    /**
     * Methods to calibrate sizes of pixels relative to board size
     */
    private final int yPixel(int y) {
        return (int) Math.round(this.height -1 - (y+1)*dY());
    }
    private final int xPixel(int x) {
        return (int) Math.round((x)*dX());
    }
    private final float dX() {
        return( ((float)(this.width-2)) / this.model.getBoard().getWidth() );
    }
    private final float dY() {
        return( ((float)(this.height-2)) / this.model.getBoard().getHeight() );
    }

    /**
     * Draw the board
     */
    public void paintBoard() {

        // Draw a rectangle around the whole screen
        if (this.mode.equalsIgnoreCase("protanopia")){
            Mode template = new Protanopia();
            Mode name = new NameDecorator(template);
            mode_type.setText("Mode: " + name.getName());
            template.draw(Color.GREEN.toString());
            Color new_colour = Color.web(template.getNew_hex());
            gc.setStroke(new_colour);
            gc.setFill(new_colour);

        } else if (this.mode.equalsIgnoreCase("deuteranopia")) {
            Mode template = new Deuteranopia();
            Mode name = new NameDecorator(template);
            mode_type.setText("Mode: " + name.getName());
            template.draw(Color.GREEN.toString());
            Color new_colour = Color.web(template.getNew_hex());
            gc.setStroke(new_colour);
            gc.setFill(new_colour);

        } else if (this.mode.equalsIgnoreCase("tritanopia")) {
            Mode template = new Tritanopia();
            Mode name = new NameDecorator(template);
            mode_type.setText("Mode: " + name.getName());

            template.draw(Color.GREEN.toString());
            Color new_colour = Color.web(template.getNew_hex());
            gc.setStroke(new_colour);
            gc.setFill(new_colour);

        }
        else{
            mode_type.setText("Mode: Default");
            gc.setStroke(Color.GREEN);
            gc.setFill(Color.GREEN);
        }


            gc.setStroke(Color.GREEN);
            gc.setFill(Color.GREEN);
        gc.fillRect(0, 0, this.width-1, this.height-1);

        // Draw the line separating the top area on the screen
        gc.setStroke(Color.BLACK);
        int spacerY = yPixel(this.model.getBoard().getHeight() - this.model.BUFFERZONE - 1);
        gc.strokeLine(0, spacerY, this.width-1, spacerY);

        // Factor a few things out to help the optimizer
        final int dx = Math.round(dX()-2);
        final int dy = Math.round(dY()-2);
        final int bWidth = this.model.getBoard().getWidth();

        int x, y;
        // Loop through and draw all the blocks; sizes of blocks are calibrated relative to screen size
        for (x=0; x<bWidth; x++) {
            int left = xPixel(x);	// the left pixel
            // draw from 0 up to the col height
            final int yHeight = this.model.getBoard().getColumnHeight(x);
            for (y=0; y<yHeight; y++) {
                if (this.model.getBoard().getGrid(x, y)) {
                    if (this.mode.equalsIgnoreCase("protanopia")) {
                        Mode template = new Protanopia();
                        template.draw(Color.RED.toString());
                        Color new_colour1 = Color.web(template.getNew_hex());
                        gc.setFill(new_colour1);
                        gc.fillRect(left+1, yPixel(y)+1, dx, dy);
                        template.draw(Color.GREEN.toString());
                        Color new_colour2 = Color.web(template.getNew_hex());
                        gc.setFill(new_colour2);


                    } else if (this.mode.equalsIgnoreCase("deuteranopia")) {
                        Mode template = new Deuteranopia();
                        template.draw(Color.RED.toString());
                        Color new_colour1 = Color.web(template.getNew_hex());
                        gc.setFill(new_colour1);
                        gc.fillRect(left+1, yPixel(y)+1, dx, dy);

                        template.draw(Color.GREEN.toString());
                        Color new_colour2 = Color.web(template.getNew_hex());
                        gc.setFill(new_colour2);

                    } else if (this.mode.equalsIgnoreCase("tritanopia")) {
                        Mode template = new Tritanopia();
                        template.draw(Color.RED.toString());
                        Color new_colour1 = Color.web(template.getNew_hex());
                        gc.setFill(new_colour1);
                        gc.fillRect(left+1, yPixel(y)+1, dx, dy);

                        template.draw(Color.GREEN.toString());
                        Color new_colour2 = Color.web(template.getNew_hex());
                        gc.setFill(new_colour2);

                    }
                    else{

                        gc.setFill(Color.RED);
                        gc.fillRect(left+1, yPixel(y)+1, dx, dy);
                        gc.setFill(Color.GREEN);
                    }
                }
            }
        }

    }


    /**
     * Create the view to save a board to a file
     */
    public void createSaveView(){
        SaveView saveView = new SaveView(this);
    }

    /**
     * Create the view to select a board to load
     */
    public void createLoadView(){
        LoadView loadView = new LoadView(this);
    }
    private void createPauseMenu(){
        PauseMenu pauseMenu = new PauseMenu(this, this.model, this.stage);
    }



    /**
     * Helper function to initialize currentlyPlaying to the clip object associated to
     * the background music.
     *
     * @throws UnsupportedAudioFileException
     * @throws IOException
     * @throws LineUnavailableException
     */
    private void selectSoundtrackView() throws UnsupportedAudioFileException, IOException,
            LineUnavailableException {

        String path = System.getProperty("user.dir") + "\\Assignment2\\music\\bgMusic.wav";

        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
                new File(path).getAbsoluteFile());
        currentlyPlaying = AudioSystem.getClip();
        currentlyPlaying.open(audioInputStream);
        currentlyPlaying.loop(Clip.LOOP_CONTINUOUSLY);
    }

}