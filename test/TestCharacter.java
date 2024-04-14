import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestCharacter {
    ArrayList<Die> dieDeck;
    int[] faces;
    Die d;
    Character p;

    @Before
    public void initialisation() {
        dieDeck = new ArrayList<>();
        faces = new int[] { 1, 2, 3, 4, 5, 6 };
        d = new Die(50, 0.5, "dé", faces, null);
        dieDeck.add(d);
        p = new Character("lisa", 100, 50, 10, 0, 30, dieDeck);
    }

    @Test
    public void TestCharacter() {
        assertEquals(p.getName(), "Lisa");
        assertEquals(p.getPv(), 100);
        assertEquals(p.getDef(), 50);
        assertEquals(p.getShield(), 10);
        assertEquals(p.getXp(), 0);
        assertEquals(p.getCoin(), 30);
        assertEquals(p.getDieDeck(), dieDeck);
    }

}