import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestCard {
    Card c;

    @Before
    public void initialisation() {
        c = new Card(60, 0.2, true, "carte", 30, 0, 0, 0, "ceci est une carte", Element.AQUA);
    }


    @Test
    public void CardTest() {
        assertEquals(c.getAtk(),30);
        assertEquals(c.getPv(),0);
        assertEquals(c.getDef(),0);
        assertEquals(c.getShield(),0);
        assertEquals(c.getShield(),0);
        assertEquals(c.getAttackType(),Element.AQUA);
    }
}