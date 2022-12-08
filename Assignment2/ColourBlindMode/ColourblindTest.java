package ColourBlindMode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ColourblindTest {

    @Test
    void Protanopia() {
        Mode type = new Protanopia();
        Mode Name = new NameDecorator(type);
        assertEquals("Protanopia", type.getName());
        assertEquals(Name.getName(), "Protanopia");

    }
    @Test
    void Deuteranopia() {
        Mode type = new Deuteranopia();
        Mode Name = new NameDecorator(type);
        assertEquals("Deuteranopia", type.getName());
        assertEquals(Name.getName(), "Deuteranopia");

    }
    @Test
    void Tritanopia() {
        Mode type = new Tritanopia();
        Mode Name = new NameDecorator(type);
        assertEquals("Tritanopia", type.getName());
        assertEquals(Name.getName(), "Tritanopia");

    }

}
