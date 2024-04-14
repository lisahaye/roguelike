
public class Article {
    private int price ;
    private double proba;
    private boolean consumable;
    private String name;


    public Article(int price, double proba, boolean consumable, String name) {
        this.price = price;
        this.proba = proba;
        this.consumable = consumable;
        this.name = name;
        
    }


    public void setPrice(int price) {
        this.price = price;
    }


    public void setProba(double proba) {
        this.proba = proba;
    }


    public int getPrice() {
        return price;
    }


    public double getProba() {
        return proba;
    }


    public boolean isConsumable() {
        return consumable;
    }


    public String getName() {
        return name;
    }

    public EquipmentType getType(){
        return null;
    }

    @Override
    public String toString() {
        return "Article{" +
                "price=" + price +
                ", proba=" + proba +
                ", consumable=" + consumable +
                ", name='" + name + '\'' +
                '}';
    }
}
