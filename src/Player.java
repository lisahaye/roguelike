import java.util.ArrayList;
import java.util.Scanner;

public class Player extends Character {
    private static final int INVENTORY_SIZE = 10;
    private int position;
    ArrayList<Card> CardDeck;
    ArrayList<Slot> inventory;
    private int gems;

    public Player(String name, int pv, int def, int shield, int xp, int coin, ArrayList<Die> dieDeck,
            int position, ArrayList<Card> cardDeck, ArrayList<Slot> inventory, int gems) {
        super(name, pv, def, shield, xp, coin, dieDeck);
        this.position = position;
        this.CardDeck = cardDeck;
        this.inventory = inventory;
        this.gems = gems;
    }

    public Player(String name){
        super(name, 100, 40, 0, 100, 100000, defaultDiceList());
        this.position = 0;
        this.CardDeck = new ArrayList<Card>();
        this.inventory = new ArrayList<Slot>();
        this.gems = 0;
        this.preparePlayer();
    }

    public int getPosition() {return position;}
    public void setPosition(int position) {this.position = position;}
    public ArrayList<Card> getCardDeck() {return CardDeck;}
    public ArrayList<Slot> getInventory() {return inventory;}
    public void setInventory(ArrayList<Slot> inventory) {this.inventory = inventory;}
    public int getGems() {return gems;}
    public void addInventory(Slot slot) {inventory.add(slot);}

    public void lvlUpCheck() {
        int nbLvl = super.getXp() % 100;
        if (nbLvl > 0) {
            setPv(getPv() + nbLvl + 3);
            //setDef(getDef());
            //setShield(getShield() + nbLvl * 20);
            //setCoin(getCoin() + nbLvl * 20);
            setXp(getXp() - nbLvl * 100);
        }
    }

    public boolean isDead(){
        return getPv()==0;
    }

    @Override
    public Die selectDie(){
        System.out.println("Choisissez un dé : ");
        for (int i = 0; i < this.getDieDeck().size(); i++) {
            System.out.println(i + " : " + this.getDieDeck().get(i).getName());
        }
        return this.getDieDeck().get(Game.choiceAndPrint(0, getDieDeck().size()-1));
    }

    public Card selectCard(){
        Card card ;
        Scanner sc = new Scanner(System.in);
        System.out.println("Choisissez une carte : ");
        for (int i = 0; i < this.getCardDeck().size(); i++) {
            System.out.println(i + " : " + this.getCardDeck().get(i).getName());
        }
        int choice = sc.nextInt();
        card= this.getCardDeck().get(choice);
        this.getCardDeck().remove(choice);
        sc.close();
        return card; 
    }

    public boolean gameOver() {
        if (getPv() <= 0) {
            return true;
        } return false;
    }

    public boolean equipItem(Equipment e) {
        for (Slot s : this.inventory) {
            switch (e.getType()) {
                case ARMOR -> {
                    if (s.getType() == EquipmentType.ARMOR && doReplace(e)) {
                        s.setItem(e);
                        return true;
                    }
                }
                case SWORD -> {
                    if (s.getType() == EquipmentType.SWORD && doReplace(e)) {
                        s.setItem(e);
                        return true;
                    }
                }
                case HELMET -> {
                    if (s.getType() == EquipmentType.HELMET && doReplace(e)) {
                        s.setItem(e);
                        return true;
                    }
                }
                case BOW -> {
                    if (s.getType() == EquipmentType.BOW && doReplace(e)) {
                        s.setItem(e);
                        return true;
                    }
                }
                case SHIELD -> {
                    if (s.getType() == EquipmentType.SHIELD && doReplace(e)) {
                        s.setItem(e);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void equipItem(Item a, Slot s){
        if(inventory.contains(s)){
            s.setItem(a);
        }
    }

    public boolean equipItem(Item a){
        if(a instanceof Equipment) return equipItem((Equipment) a);
        for (Slot s : this.inventory){
            if(s.getItem() == null){
                s.setItem(a);
                return true;
            }
        }
        return false;
    }

    public boolean doReplace(){
        System.out.println("1. Equiper l'objet");
        System.out.println("2. Jeter l'objet");
        return Game.choiceAndPrint(1, 2) == 1;
    }

    public boolean doReplace(Equipment s){
        System.out.println("Equipement trouvé :");
        System.out.println(s + "\n");
        System.out.println("Equipement porté :");
        System.out.println(inventory.get(s.getType().ordinal()) + "\n");
        return doReplace();
    }

    public Slot inventoryFull(Article a){
        System.out.println("Votre inventaire est plein!");
        System.out.println("Veuillez choisir un objet à remplacer:");
        ArrayList<Integer> allowed = new ArrayList<Integer>();
        for(int i = 0; i < this.getInventory().size(); i++){
            if(a instanceof Equipment && this.getInventory().get(i).getType() == ((Equipment) a).getType()){
                System.out.println(i + " : " + this.getInventory().get(i).getItem().getName());
                allowed.add(i);
            }else if(this.getInventory().get(i).getType() == null){
                System.out.println(i + " : " + this.getInventory().get(i).getItem().getName());
                allowed.add(i);
            }
        }


        int choice = Game.readInt();
        while(!allowed.contains(choice)){
            System.out.println("Veuillez choisir un objet valide:");
            choice = Game.readInt();
        }
        return this.getInventory().get(choice);
    }



    public boolean inventoryIsFull(){
        for (Slot s : this.inventory){
            if(s.getItem() == null){
                return false;
            }
        }
        return true;
    }
    
    public void preparePlayer(){
        // Default equipments
        Equipment weapon = new Equipment("Épée de base", EquipmentType.SWORD, 30, 0, 0);
        Equipment armor = new Equipment("Armure de base", EquipmentType.ARMOR, 0, 30, 0);
        Equipment shield = new Equipment("Bouclier de base", EquipmentType.SHIELD, 0, 0, 30);
        Equipment bow = new Equipment("Arc du debutant", EquipmentType.BOW, 30, 0,0);

        Slot weaponSlot = new Slot(weapon, EquipmentType.SWORD);
        Slot helmetSlot = new Slot(armor, EquipmentType.HELMET);
        Slot armorSlot = new Slot(armor, EquipmentType.ARMOR);
        Slot bowSlot = new Slot(bow,EquipmentType.BOW);
        Slot shieldSlot = new Slot(shield, EquipmentType.SHIELD);
        this.inventory.add(armorSlot);
        this.inventory.add(weaponSlot);
        this.inventory.add(helmetSlot);
        this.inventory.add(bowSlot);
        this.inventory.add(shieldSlot);

        for(int i = 0; i<INVENTORY_SIZE; i++){
            this.inventory.add(new Slot());
        }
    }

    private static ArrayList<Die> defaultDiceList() {
        Die d = new Die(0, 1, "Dé de base", new int[]{1, 2, 3, 4, 5, 6}//, new int[]{1,2,3,4,5,6}
        );
        return new ArrayList<Die>() {
            {
                add(d);
            }
        };
    }

    /*
    public int degatSubi(int degat){
        int degatSubi;
        degat=degat-this.getDef();
        degatSubi = degat - this.getShield();
        setShield(degat - degatSubi);
        setPv(getPv()-degatSubi);
        this.setAtk(getAtk()+degat);
        return degatSubi;
    }
    */

    public boolean purchaseArticle(Item a){
        if(this.getCoin() >= a.getPrice()){
            if(!this.equipItem(a)){
                this.equipItem(a, inventoryFull(a));
                this.setCoin(this.getCoin() - a.getPrice());
                System.out.println("Merci pour votre achat!");
                Game.timeOut();
                return true;
            }else{
                this.setCoin(this.getCoin() - a.getPrice());
                System.out.println("Merci pour votre achat!");
                Game.timeOut();
                return true;
            }
        }else{
            System.out.println("Vous n'avez pas assez de pièces!");
            Game.timeOut();
            return false;
        }
    }

    @Override
    public String toString() {
        return super.toString();
        /*"Player{" +
                "position=" + position +
                ", CardDeck=" + CardDeck +
                ", inventory=" + inventory +
                ", gems=" + gems +
                '}';
        */
        }

}