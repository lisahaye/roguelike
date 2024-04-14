public class Item extends Article{
    private int atk;
    private int pv;
    private int def;
    
    public Item(int price, double proba, boolean consumable, String name,int atk, int pv, int def) {
        super(price,proba , consumable , name );
        this.atk = atk;
        this.pv = pv;
        this.def = def;
    }


    public int getAtk() {
        return atk;
    }

    public int getPv() {
        return pv;
    }

    public int getDef() {
        return def;
    }

    public double getProba(){
        return super.getProba();
    }

    @Override
    public String toString() {
        return  "\nNom : " + super.getName() +
                "\nConsummé à l'usage : " + (super.isConsumable() ? "oui" : "non") +
                "\nDégats : " + atk +
                "\nSoin : " + pv +
                "\nDéfense : " + def +
                "\nRareté : " + super.getProba() +
                "\nPrix : " + getPrice() +
                "\n";
    }



   
}