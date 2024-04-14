public class Slot {
    private Item item;
    private final EquipmentType type;

    public Slot(Item item, EquipmentType type) {
        this.item = item;
        this.type = type;
    }

    public Slot(){
        this(null,null);
    }

    public Item getItem() {
        return item;
    }

    public EquipmentType getType() {
        return type;
    }

    public void setItem(Item item){
        if(this.type != null && item.getType() != this.type){
            throw new IllegalArgumentException("The article is not compatible with the slot");
        }else{
            this.item = item;
        }
    }
    

    public void emptySlot(){
        this.item = null;
    }

    @Override
    public String toString() {
        return "Slot{" +
                "item=" + item +
                ", type=" + type +
                '}';
    }
}