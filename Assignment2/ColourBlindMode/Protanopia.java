package ColourBlindMode;

import javafx.scene.paint.*;

/**
 * Class Protanopia.
 * This is a concrete class that implements Mode.
 * This class is for switching colours of the game GUI
 * to benefit users with Protanopia
 */
public class Protanopia implements Mode{

    private String new_hex;
    public void draw(String hex){
        if (hex.equalsIgnoreCase(Color.BLUE.toString())){
            this.new_hex = "719cff";
        }
        if (hex.equalsIgnoreCase(Color.RED.toString())){
            this.new_hex = "968726";
        }
        if (hex.equalsIgnoreCase(Color.GREEN.toString())){
            this.new_hex = "f6dc00";
        }

    }

    public String getName(){
        return "Protanopia";
    }
    public String getNew_hex(){
        return this.new_hex;
    }
}
