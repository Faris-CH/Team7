package ColourBlindMode;

import javafx.scene.paint.*;
/**
 * Class Tritanopia.
 * This is a concrete class that implements Mode.
 * This class is for switching colours of the game GUI
 * to benefit users with Tritanopia
 */
public class Tritanopia implements Mode{

    String new_hex;

    public void draw(String hex){
        if (hex.equalsIgnoreCase(Color.BLUE.toString())){
            this.new_hex = "719cff";
        }
        if (hex.equalsIgnoreCase(Color.RED.toString())){
            this.new_hex = "fe1c00";
        }
        if (hex.equalsIgnoreCase(Color.GREEN.toString())){
            this.new_hex = "73edff";
        }

    }

    public String getName(){
        return "Tritanopia";
    }
    public String getNew_hex(){
        return new_hex;
    }
}
