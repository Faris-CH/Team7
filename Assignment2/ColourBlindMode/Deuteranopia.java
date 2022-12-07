package ColourBlindMode;

import javafx.scene.paint.*;

/**
 * Class Deuteranopia.
 * This is a concrete class that implements Mode.
 * This class is for switching colours of the game GUI
 * to benefit users with Deuteranopia
 */
public class Deuteranopia implements Mode {

    String new_hex;
    public void draw(String hex){
        if (hex.equalsIgnoreCase(Color.BLUE.toString())){
            this.new_hex = "008bdf";
        }
        if (hex.equalsIgnoreCase(Color.RED.toString())){
            this.new_hex = "a98200";
        }
        if (hex.equalsIgnoreCase(Color.GREEN.toString())){
            this.new_hex = "ffcd72";
        }

    }
    public String getName(){
        return "Deuteranopia";
    }
    public String getNew_hex(){
        return new_hex;
    }
}
