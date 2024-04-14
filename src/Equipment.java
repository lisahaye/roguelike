public class Equipment extends Item {
    private EquipmentType type;



    public Equipment(int price, double proba, String name, EquipmentType type, int atk, int pv, int def) {
        super(price,proba, false , name, atk, pv, def );
        this.type = type;
    }

    public Equipment(String name, EquipmentType type, int atk, int pv, int def){
        super(0, 0, false, name, atk, pv, def);
        this.type = type;
    }

    public EquipmentType getType() {
        return type;
    }


    @Override
    public String toString() {
        return type +
                "\nNom : "  + getName() +
                "\nAttaque : " + getAtk() +
                "\nPV : " + getPv() +
                "\nDéfense : " + getDef() +
                "\nRareté : " + getProba() +
                "\nPrix : " + getPrice() +
                "\n";
    }
    
}
