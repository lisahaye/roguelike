import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestMonster {
    ArrayList<Die> dieDeck;
    int[] faces;
    Die d;
    ArrayList<Element> list;
    Monster m1;
    Monster m2;

    @Before
    public void initialisation() {
        dieDeck = new ArrayList<>();
        faces = new int[] { 1, 2, 3, 4, 5, 6 };
        d = new Die(50, 0.5, "dé", faces, null);
        dieDeck.add(d);
        list = new ArrayList<>();
        list.add(Element.FIRE);
        m1 = new Monster("sami", 20, 45, 60, 80, 200, dieDeck, 100, 5, list);
        m2 = new Monster(2.5);
    }

    @Test
    public void TestMonster() {
        assertEquals(m1.getFleeCost(),100);
        assertEquals(m2.getFleeCost(),0);
        assertEquals(m1.getFleeNbCase(),5);
        assertEquals(m2.getFleeNbCase(),0);
        assertEquals(m1.getTypes(),list);
        assertEquals(m2.getTypes(),null);
    }

}
