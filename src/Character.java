import java.util.ArrayList;

public class Character{
    final static int DICEDECKSIZE = 3;
    private String name;
    private int pvmax;
    private int pv;
    private int def;
    private int shield;
    private int xp;
    private int coin;
    ArrayList<Die> dice;

    public Character(String name, int pv, int def, int shield, int xp, int coin, ArrayList<Die> dieDeck) {
        this.name = name;
        this.pvmax = pv;
        this.pv = pv;
        this.def = def;
        this.shield = shield;
        this.xp = xp;
        this.coin = coin;
        this.dice = dieDeck;
        if(dice.isEmpty())dice.add(new Die(1, 1, "Dédépart", new int[]{1,2,3,4,5,6}//, new int[]{1,2,3,4,5,6}
        ));
    }

    public Character(String name, int pv, int def, int shield, int xp, int coin) {
        this(name, pv, def, shield, xp, coin, new ArrayList<Die>());
    }

    public String getName() {
        return name;
    }

    public int getPv() {
        return pv;
    }

    public void setPv(int pv) {
        this.pv = pv;
    }

    public void setDef(int def) {
        this.def = def;
    }

    public void setShield(int shield) {
        this.shield = shield;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public void setDieDeck(ArrayList<Die> dieDeck) {
        this.dice = dieDeck;
    }

    public int getDef() {
        return def;
    }

    public int getShield() {
        return shield;
    }

    public int getXp() {
        return xp;
    }

    public int getCoin() {
        return coin;
    }

    public int getLevel(){
        return this.xp/100;
    }

    public void messageVictoire(){
        System.out.println("Bien joué ! Vous avez vaincu " + this.getName());
        System.out.println("Vous avez gagné : ");
        System.out.println(getXp() + " xp");
        System.out.println(getCoin() + " jetons");
    }

    public ArrayList<Element> getTypes(){
        return new ArrayList<>();
    }

    public ArrayList<Die> getDieDeck() {
        return dice;
    }

    @Override
    public String toString() {
        return 
                name + "\n" +
                "pv : " + pv + "/" + pvmax + "\n" +
                "armure : " + shield + "\n" +
                "def : " + def + "\n" +
                "xp : " + xp + "\n" +
                "coin : " + coin + "\n" +
                "dieDeck : " + dice + "\n"
                ;
    }

    public Die selectDie(){return null;}
}