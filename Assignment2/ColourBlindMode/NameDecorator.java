package ColourBlindMode;
/**
 * Class NameDecorator.
 * This is a concrete decorator class.
 */
public class NameDecorator extends ModeDecorator{
    private String new_hex = "";
        public NameDecorator(Mode decoratedMode){
            super(decoratedMode);
        }
        public void draw(String hex){
            decoratedMode.draw(hex);

        }

        public String getName(){
            return decoratedMode.getName();
    }
    public String getNew_hex(){
        return new_hex;
    }
}
