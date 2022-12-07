package ColourBlindMode;

import javafx.scene.paint.*;
/**
 * Abstract Class ModeDecorator.
 * This is a abstract decorator class.
 */
public abstract class ModeDecorator implements Mode{
    protected Mode decoratedMode;

    public ModeDecorator(Mode decoratedMode){
        this.decoratedMode = decoratedMode;
    }
    public void draw(String hex){
        decoratedMode.draw(hex);

    }
    public String getName(){
        return decoratedMode.getName();
    }
}
