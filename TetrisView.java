package views;

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



/**
 * Tetris View
 *
 * Based on the Tetris assignment in the Nifty Assignments Database, authored by Nick Parlante
 */
public class TetrisView {

    TetrisModel model; //reference to model
    Stage stage;

    Button menustartButton, instructionsButton, settingsButton, soundButton, loadButton;

    Button backButton;


    Label scoreLabel = new Label("");
    Label gameModeLabel = new Label("");

    Label title = new Label("");

    BorderPane borderPane;
    Canvas canvas;
    GraphicsContext gc; //the graphics context will be linked to the canvas

    Boolean paused;
    Timeline timeline;

    int pieceWidth = 20; //width of block on display
    private double width; //height and width of canvas
    private double height;

    private double screen_width;
    private double screen_height;

    /**
     * Constructor
     *
     * @param model reference to tetris model
     * @param stage application stage
     */

    public TetrisView(TetrisModel model, Stage stage) {
        this.model = model;
        this.stage = stage;
        menuUi();
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





//UI Class For The Settings
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
        backButton = new Button("Back");
        backButton.setId("Back");
        backButton.setPrefSize(180, 60);
        backButton.setFont(new Font(15));
        backButton.setStyle("-fx-background-color: #0000FF; -fx-text-fill: white;");

        title.setText("Settings");
        title.setId("title");
        title.setFont(new Font(60));
        title.setStyle("-fx-text-fill: #e8e6e3");
        title.setAlignment(Pos.TOP_CENTER);

        Label temp = new Label("");
        Label temp2 = new Label("");
        HBox bot = new HBox(20, backButton);
        VBox controls = new VBox(25, bot, title, temp, temp2);
        controls.setPadding(new Insets(50, 20, 20, 20));
        controls.setAlignment(Pos.TOP_CENTER);

        //configure this such that you restart the game when the user hits the startButton
        //Make sure to return the focus to the borderPane once you're done!
        backButton.setOnAction(e -> {
            menuUi();
            borderPane.requestFocus();
        });

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////

        borderPane.setTop(controls);
        borderPane.setCenter(canvas);

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

    private void gameOverUI(){
        //Create new borderpane
        borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: #121212;");

        //add canvas
        canvas = new Canvas(this.width, this.height);
        canvas.setId("Canvas");
        gc = canvas.getGraphicsContext2D();

        //add buttons
        title.setText("Game Over");
        title.setId("Game Over");
        title.setFont(new Font(60));
        title.setStyle("-fx-text-fill: #e8e6e3");
        title.setAlignment(Pos.TOP_CENTER);

//        menustartButton = new Button("Back");
//        resumeButton.setId("Back");
//        resumeButton.setPrefSize(180, 60);
//        resumeButton.setFont(new Font(15));
//        resumeButton.setStyle("-fx-background-color: #0000FF; -fx-text-fill: white;");

        // Align the display using inbuilt javaFX layouts
        Label temp = new Label("");
        HBox bot = new HBox(20, menustartButton);
        VBox controls = new VBox(25, bot, title, temp);
        controls.setPadding(new Insets(50, 20, 20, 20));
        controls.setAlignment(Pos.TOP_CENTER);

        //Button to go back to pause menu
        menustartButton.setOnAction(e -> {
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

    private void initUI() {
        this.paused = false;
        this.stage.setTitle("Tetris Unlocked");
//        this.width = this.model.getWidth()*pieceWidth + 2;
//        this.height = this.model.getHeight()*pieceWidth + 2;

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


        Slider slider = new Slider(0, 100, 50);
        slider.setShowTickLabels(true);
        slider.setStyle("-fx-control-inner-background: palegreen;");

        VBox vBox = new VBox(20, slider);
        vBox.setPadding(new Insets(20, 20, 20, 20));
        vBox.setAlignment(Pos.TOP_CENTER);

        VBox scoreBox = new VBox(20, scoreLabel, gameModeLabel, pilotButtonHuman, pilotButtonComputer);
        scoreBox.setPadding(new Insets(20, 20, 20, 20));
        vBox.setAlignment(Pos.TOP_CENTER);

        toggleGroup.selectedToggleProperty().addListener((observable, oldVal, newVal) -> swapPilot(newVal));

        //timeline structures the animation, and speed between application "ticks"
        timeline = new Timeline(new KeyFrame(Duration.seconds(0.25), e -> updateBoard()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

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
                    gc.setFill(Color.RED);
                    gc.fillRect(left+1, yPixel(y)+1, dx, dy);
                    gc.setFill(Color.GREEN);
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


}