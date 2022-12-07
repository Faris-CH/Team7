package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class TetrisScores implements Serializable{

    private HashMap<String, Integer> scores = null;

    private static final long serialVersionUID = 123454;

    // Declaring a variable of type String

    // Constructor
    // Here we will be creating private constructor
    // restricted to this class itself
    public TetrisScores()
    {
        scores = new HashMap<String, Integer>();
    }

    public void addHighScore(String name, int score){
        scores.put(name, score);
    }

    public HashMap<String, Integer> getHighScores(){
        return (scores);
    }

    public void saveScores(File file) {
        try {
            FileOutputStream fout = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getScores() throws IOException {
        File boardfile = new File(".\\scores\\highscores.ser");
        boolean exists = boardfile.exists();

        if (!exists){
            return;
        }

        FileInputStream file = null;
        ObjectInputStream in = null;
        try {
            file = new FileInputStream(boardfile);
            in = new ObjectInputStream(file);
            scores = ((TetrisScores) in.readObject()).getHighScores();
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
}
