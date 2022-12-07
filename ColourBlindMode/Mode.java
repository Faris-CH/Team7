package ColourBlindMode;

/**
 * Interface Mode.
 * This is an interface for the colourblind modes.
 */
public interface Mode {
    void draw(String hex);

    public String getNew_hex();

    public String getName();
}
