import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class CSVArticle {

    private static final String CARDS_HEADER = "NAME,PRICE,DESC,TYPE,PROBA,CONSUMABLE,ATK,PV,DEF,SHIELD";
    private static final String EQUIPMENTS_HEADER = "NAME,PRICE,PROBA,ATK,PV,DEF,TYPE";
    private static final String ITEMS_HEADER = "NAME,PRICE,PROBA,CONSUMABLE,ATK,PV,DEF";

    public static ArrayList<Equipment> loadEquipments(String path) throws FileNotFoundException, InvalidFileFormatException {
        Scanner sc = loadCSV(path);
        ArrayList<Equipment> equipments = new ArrayList<Equipment>();
        if(!sc.hasNextLine()) return equipments;
        String header = sc.nextLine();
        if(!header.equals(EQUIPMENTS_HEADER)) throw new InvalidFileFormatException();
        while(sc.hasNextLine()){
            String name = sc.next();
            int price = sc.nextInt();
            double proba = Double.parseDouble(sc.next());
            int atk = sc.nextInt();
            int pv = sc.nextInt();
            int def = sc.nextInt();
            EquipmentType type = EquipmentType.valueOf(sc.next());
            equipments.add(new Equipment(price, proba, name, type, atk, pv, def));
            sc.nextLine();
        }
        return equipments;
    }

    public static ArrayList<Item> loadItems(String path) throws FileNotFoundException, InvalidFileFormatException {
        Scanner sc = loadCSV(path);
        ArrayList<Item> items = new ArrayList<Item>();
        if(!sc.hasNextLine()) return items;
        String header = sc.nextLine();
        if(!header.equals(ITEMS_HEADER)) throw new InvalidFileFormatException();
        while(sc.hasNextLine()){
            String name = sc.next();
            int price = sc.nextInt();
            double proba = Double.parseDouble(sc.next());
            boolean consumable = Boolean.parseBoolean(sc.next());
            int atk = sc.nextInt();
            int pv = sc.nextInt();
            int def = sc.nextInt();
            items.add(new Item(price, proba, consumable, name, atk, pv, def));
            sc.nextLine();
        }
        return items;
    }

    public static ArrayList<Card> loadCards(String path) throws FileNotFoundException, InvalidFileFormatException {
        Scanner sc = loadCSV(path);
        ArrayList<Card> cards = new ArrayList<Card>();
        if(!sc.hasNextLine()) return cards;
        String header = sc.nextLine();
        if(!header.equals(CARDS_HEADER)) throw new InvalidFileFormatException();
        while(sc.hasNextLine()){
            String name = sc.next();
            int price = sc.nextInt();
            String desc = sc.next();
            String elType = sc.next();
            Element type = null;
            if(!elType.isEmpty()){
                type = Element.valueOf(elType);
            }
            double proba = Double.parseDouble(sc.next());
            boolean consumable = Boolean.parseBoolean(sc.next());
            int atk = sc.nextInt();
            int pv = sc.nextInt();
            int def = sc.nextInt();
            int shield = sc.nextInt();
            cards.add(new Card(price, proba, consumable, name, atk, pv, def, shield, desc, type));
            sc.nextLine();
        }
        return cards;
    }


    private static Scanner loadCSV(String path) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(path));
        sc.useDelimiter(",");
        return sc;
    }


}
