package Assignment2;//import model.TetrisModel;
import Assignment2.model.TetrisModel;
import Assignment2.views.TetrisView;
//import Assignment2.model.TetrisModel

import javafx.application.Application;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

import java.sql.SQLOutput;

/** 
 * A Tetris Application, in JavaFX
 * 
 * Based on the Tetris assignment in the Nifty Assignments Database, authored by Nick Parlante
 */
public class TetrisApp extends Application {
    TetrisModel model;
    TetrisView view;

    /** 
     * Main method
     * 
     * @param args agument, if any
     */
    public static void main(String[] args) {
        launch(args);
    }

    /** 
     * Start method.  Control of application flow is dictated by JavaFX framework
     * 
     * @param primaryStage stage upon which to load GUI elements
     */
    @Override
    public void start(Stage primaryStage) {
        int userChoice = 0;
        System.out.println("Welcome to TETRIS UNLOCKED");
        System.out.println("Type 1 to play TETRIS UNLOCKED, Type 2 to view controls and instructions");
        do {
            userChoice = Integer.parseInt(JOptionPane.showInputDialog(null,
                    "Number you would like to choose"));
            switch (userChoice) {
                case 1: {

                    this.model = new TetrisModel(); // create a model
                    this.view = new TetrisView(model, primaryStage); //tie the model to the view
                    this.model.startGame(); //begin

                }
                case 2: {
                    System.out.println("How to play TETRIS Unlocked:\n" +
                            "\n" +
                            "The way objective of TETRIS is easy, the goal is to bring the " +
                            "TETRIS blocks from the top of the screen to the bottom to create a single " +
                            "straight horizontal line." +
                            " On the way down the player can rotate the object clockwise and counter-clockwise," +
                            " using the keys 'E' and 'Q' all in rotations of 90 degrees. A player could also move " +
                            "the block left and right, using the keys 'A' and 'D', finally to move the block downwards" +
                            " a player could use the 'S' key to drop the block downwards at a faster rate.\n" +
                            "\n" +
                            "Controls: \n" +
                            "- Left - 'A'\n" +
                            "- Right - 'D'\n" +
                            "- Down - 'S'\n" +
                            "- Clockwise - 'E'\n" +
                            "- Counter-Clockwise - 'Q'");
                }
            }
        }while (userChoice != 1);



    }

}

