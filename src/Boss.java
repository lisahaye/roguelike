import java.util.ArrayList;
import java.util.Random;

public class Boss extends Character{
    private ArrayList<Equipment> equipment;
    private ArrayList<Element> types;
    private ArrayList<Element> weakness;
    private static final Random r = new Random(); 

    public Boss(int pv, int def, int shield, int xp, int coin, ArrayList<Die> dice, String desc, ArrayList<Element> type, ArrayList<Element> weakness) {
        super("boss", pv, def, shield, xp, coin, dice);
        this.types = type;
        this.weakness = weakness;
    }
    public Boss(int pv, int def, int shield, String desc, ArrayList<Element> type, ArrayList<Element> weakness) {
        this(pv, def, shield,100,10, new ArrayList<Die>(), desc, type, weakness);
    }

    public Boss(int targettedLvl){
        this(20 + targettedLvl * r.nextInt(3,5), r.nextInt(4), 10 * targettedLvl * r.nextInt(1) % 15, 100, 10, new ArrayList<Die>(),"boss", new ArrayList<Element>(), new ArrayList<Element>());
    }

    public ArrayList<Equipment> getEquipment() {
        return this.equipment;
    }

    public ArrayList<Element> getTypes() {
        return this.types;
    }

    public ArrayList<Element> getWeakness() {
        return this.weakness;
    }

    

    @Override
    public String toString() {
        return "Boss{" +
                "equipment=" + equipment +
                ", types=" + types +
                ", weakness=" + weakness +
                '}';
    }
}
