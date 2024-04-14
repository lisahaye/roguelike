public class Card extends Item {
    private int shield;
    private String desc;
    private Element type;
    
    public Card(int price , double proba ,boolean consumable, String name, int atk, int pv, int def, int shield, String desc, Element typElement) {
        super(price,proba , consumable , name, atk, pv, def);
        this.shield = shield;
        this.desc = desc;
        this.type = typElement;
    }

    public int getAtk() {
        return super.getAtk();
    }

    public int getPv() {
        return super.getPv();
    }

    public int getDef() {
        return super.getDef();
    }

    public int getShield() {
        return shield;
    }

    public String getDesc() {
        return desc;
    }

    public Element getAttackType() {
        return type;
    }

    @Override
    public String toString() {
        return  "Nom : "  + getName() +
                "\nRareté : " + getProba() +
                "\nDégats : " + getAtk() +
                "\nSoin : " + getPv() +
                "\nDéfense : " + getDef() +
                "\nBouclier : " + shield +
                "\nDescription : " + desc +
                "\nElement : " + (type != null ? type : "")+
                "\nPrix : " + getPrice() +
                "\n";
    }
}
