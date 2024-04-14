import java.util.ArrayList;
import java.util.Random;

public class Monster extends Character {
    private int fleeCost;
    private int fleeNbCase;
    private ArrayList<Element> types;

    public Monster(String name, int pv, int def, int shield, int xp, int coin, ArrayList<Die> dieDeck,
            int fleeCost, int fleeNbCase, ArrayList<Element> types) {
        super(name, pv, def, shield, xp, coin, dieDeck);
        this.fleeCost = fleeCost;
        this.fleeNbCase = fleeNbCase;
        this.types = types;
        
    }

    public Monster(double targettedLvl){
        this("Monstre" , (int)(10+targettedLvl* (new Random().nextInt(3))), (int)(targettedLvl*(new Random().nextDouble())), 0, 20, 5, new ArrayList<Die>(), 1, 1, new ArrayList<Element>());
    }

    public int getFleeCost() {return fleeCost;}
    public int getFleeNbCase() {return fleeNbCase;}
    public ArrayList<Element> getTypes() {return types;}
    
    public void degatSubit(int degat){
        int degatSubi;
        setPv(getPv()-degat);
        degatSubi = degat - this.getShield();
        setShield(degat- degatSubi);
        setPv(getPv()-degatSubi);
    }


    @Override
    public String toString() {
        return super.toString() +  
        
                "fleeCost=" + fleeCost + "\n" +
                "fleeNbCase=" + fleeNbCase + "\n" +
                "types=" + types + "\n"
                ;
    }

    @Override
    public Die selectDie() {
        Random r = new Random();
        return getDieDeck().get(r.nextInt(getDieDeck().size()));
    }

    /*
    private int isWeak(Element atkElement){
        int i = 0;
        int weakness = (atkElement.ordinal() % 2) - types.get(i).ordinal();
        while(i < types.size() && weakness != 1){
            i++;
            weakness = (atkElement.ordinal() % 2) - types.get(i).ordinal();
        }return weakness;
    }
    */
}