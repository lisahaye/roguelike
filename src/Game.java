import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Game implements Serializable {
    private Player player;
    private Board board;
    private int turn;
    private int tableTurn;
    ArrayList<Item> items;
    ArrayList<Equipment> equipments;
    ArrayList<Card> cards;
    public final static String SEPARATOR = File.separator;
    
    public Game(Player player) {
        this.player = player;
        this.board = new Board();
        this.turn = 1;
    }

    public Player getPlayer() {
        return this.player;
    }

    public Board getBoard() {
        return this.board;
    }

    public int getTurn() {
        return this.turn;
    }

    public int getTableTurn(){
        return this.tableTurn;
    }

    public void loadResources(String folderPath) {
        try {
            this.equipments = CSVArticle.loadEquipments(folderPath + SEPARATOR + "equipments.csv");
        }catch(Exception e){
            System.out.println("Erreur lors du chargement des équipements, la liste sera vide.");
        }
        try{
            this.items = CSVArticle.loadItems(folderPath + Game.SEPARATOR + "items.csv");
        }catch(Exception e){
            System.out.println("Erreur lors du chargement des items, la liste sera vide.");
        }
        try {
            this.cards = CSVArticle.loadCards(folderPath + Game.SEPARATOR + "cards.csv");
        }catch(Exception e){
            System.out.println("Erreur lors du chargement des cartes, la liste sera vide.");
        }
    }

    public static void timeOut(){
        try{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Appuyez sur une touche pour continuer.");
        br.readLine();
        }catch(Exception e){}
    }

    public static String readStringNotNull(){
        String s = null;
        try{
            do{
                s = readString();
            }while(s == null);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return s;
    }


    public static int readInt(){
        boolean isOkay = false;
        int i = -1;
        do{
        try
        {
            i = Integer.parseInt(readStringNotNull());
            isOkay = true;
        } catch (NumberFormatException ex)
        {
            isOkay = false;
        }
        }while(isOkay == false);
        return i;
    }

    public static String readString() throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        return br.readLine();
    }

/*
    public void nextBoardTurn() {
        System.out.println("Tour " + this.tableTurn + " :");
        printBoard();

        this.tableTurn++;
        Die die = player.selectDie();
        int roll = die.roll();
    }
*/
    public int roll(){
        return player.selectDie().roll();
    }

    public void nextTurn() {
        cleanEcran();
        System.out.println("Tour " + this.turn + " :");
        printBoard();
        
        this.turn++;
        //System.out.println(player.getInventory());

        int roll = roll();
        if (player.getPosition() + roll > board.getSquares().size()-1 && board.getSquares().get(board.getSquares().size()-1) == SquareType.BOSS) {
            this.bossFight();
        } 
        else if (player.getPosition() + roll > board.getSquares().size()) {
            player.setCoin(player.getCoin() + 20);
            Die d = newDie(this.turn);
            if (player.dice.size() < Character.DICEDECKSIZE) {
                player.dice.add(d);
            }
            else {
                System.out.println("Veuillez choisir un dé à remplacer");
                for (int i = 0 ; i < Character.DICEDECKSIZE ; i++) {
                    System.out.println(i + ". " + player.getDieDeck().get(i).toString());
                }
                System.out.println(Character.DICEDECKSIZE + ". ne pas remplacer de dé");
                int choice = choiceAndPrint(0, Character.DICEDECKSIZE);
                if (choice != Character.DICEDECKSIZE) {
                    player.dice.remove(choice);
                    player.dice.add(d);
                }
            }
        }
        
        else {
            player.setPosition((player.getPosition() + roll) % 18);
            cleanEcran();
            printBoard();
            System.out.println("Vous avez fait un " + roll);
            SquareType current = this.getPlayerSquare();
            switch (current) {
                case MONSTER:
                    doMonster();
                    break;
                case LOOT:
                    doLoot();
                    break;
                case SHOP:
                    System.out.println("Vous êtes tombé sur un magasin");
                    doShop();
                    break;
                case BACKWARDS:
                    doBackwards();
                    break;
                case BOSS:
                    this.bossFight();
                    timeOut();
                    break;
                case EVENT:
                    System.out.println("Vous êtes tombé sur un évènement aléatoire!");
                    timeOut();
                    switch((1+(int)(Math.random()*4))){
                        case 1:
                            doShop();
                            break;
                        case 2:
                            doLoot();
                            break;
                        case 3:
                            doMonster();
                            break;
                        case 4:
                            doShuffle();
                            break;
                    }
                    break;
                case SHUFFLE:
                    doShuffle();
                    break;
                case START:
                    System.out.println("Vous êtes attéri sur la case départ");
                    timeOut();
                    break;
                default:
                    System.out.println("Vous êtes tombé sur une case inconnue");
                    timeOut();
                    break;
            }
        }

    }

    public static int choiceAndPrint(int root, int bound){
        System.out.println("Veuillez entrer un nombre entre " + root + " et " + bound);
        int i = readInt();
        while(i < root || i > bound){
            System.out.println("Veuillez entrer un nombre entre " + root + " et " + bound);
            i = readInt();
        }return i;
    }

    private void doBackwards() {
        System.out.println("Vous êtes tombé sur une case retour en arrière");
        timeOut();
        int random = 1 + (int) (Math.random() * 3);
        if(player.getPosition() - random < 0) player.setPosition(0);
        else player.setPosition(player.getPosition() - random);
        System.out.println("Votre personnage a reculé de " + random + " cases");
        printBoard();
        nextTurn();
    }

    private void doMonster(){
        System.out.println("Vous êtes tombé sur un monstre!");
        timeOut();
        Monster m = new Monster(player.getLevel());
        timeOut();
        fight(m);
    }

    private void doLoot(){
        System.out.println("Vous êtes tombé sur un trésor");
        timeOut();
        Item loot = loot();
        System.out.println("Vous avez trouvé l'objet suivant: "+ loot.getName());
        player.equipItem(loot);
        timeOut();
    }

    private void doShuffle(){
        System.out.println("Vous êtes tombé sur une case reset");
        timeOut();
        this.board.generateBoard();
        printBoard();
        nextTurn();
    }

    private void doShop(){
        timeOut();
        shopMenu();
        timeOut();
    }


    public SquareType getPlayerSquare(){
        return this.board.getSquares().get(this.player.getPosition());
    }

    public static void cleanEcran(){
        
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }
            else {
                System.out.print("\033\143");
            }
        } catch (IOException | InterruptedException ex) {}
    }
    

    public void printBoard() {
        for (int i = 0; i < 6; i++) {
            System.out.print(board.getColor(i) + " _____ " + Board.RESETCOLOR);
        }
        System.out.println();

        for (int i = 0; i < 3; i++) {
            if (i == 1) {
                for (int j = 0; j < 6; j++) {
                    String temp = "|  ";
                    if (player.getPosition() == j)
                        temp += "X";
                    else
                        temp += " ";
                    temp += "  |";
                    System.out.print(board.getColor(j) + temp + Board.RESETCOLOR);
                }
            } else if (i == 0) {
                for (int j = 0; j < 6; j++) {
                    System.out.print(board.getColor(j) + "|     |" + Board.RESETCOLOR);
                }
            } else {
                for (int j = 0; j < 6; j++) {
                    System.out.print(board.getColor(j) + "|_____|" + Board.RESETCOLOR);
                }
            }
            System.out.println();
        }
        for(int i = 0 ; i < 3 ; i++){
            System.out.print(board.getColor(17-i) + " _____ " + Board.RESETCOLOR);
            
            for(int j = 0 ; j < 4 ; j++){ System.out.print("       ");}
            
            System.out.print(board.getColor(6+i) + " _____ " + Board.RESETCOLOR);
            System.out.println();
            for(int j = 0 ; j < 3 ; j++){
                if(j == 1){
                    String temp = "|  ";
                    temp += player.getPosition() == 17-i? "X" : " ";
                    temp += "  |"; 
                    System.out.print(board.getColor(17-i) + temp + Board.RESETCOLOR);
                    
                    for(int l = 0 ; l < 4 ; l++){ System.out.print(Board.RESETCOLOR + "       ");}

                    temp = "|  ";
                    temp += player.getPosition() == 6+i ? "X" : " ";
                    temp += "  |"; 
                    System.out.print(board.getColor(6+i) + temp + Board.RESETCOLOR);
                    
                }
                else if(j == 0){
                    System.out.print(board.getColor(17-i) + "|     |" + Board.RESETCOLOR);
                    
                    for(int l = 0 ; l < 4 ; l++){ System.out.print("       ");}

                    System.out.print(board.getColor(6+i) + "|     |" + Board.RESETCOLOR);

                }
                else if(j == 2){
                    System.out.print(board.getColor(17-i) + "|_____|" + Board.RESETCOLOR);
                    
                    for(int l = 0 ; l < 4 ; l++){ System.out.print("       ");}

                    System.out.print(board.getColor(6+i) + "|_____|" + Board.RESETCOLOR);

                }System.out.println();
            }
        }

        for(int i = 0 ; i < 6 ; i++){
            System.out.print(board.getColor(14-i) + " _____ " + Board.RESETCOLOR);
        }
        System.out.println();
        for (int i = 0; i < 3; i++) {
            if (i == 1) {
                for (int j = 0; j < 6; j++) {
                    String temp = "|  ";
                    if(player.getPosition() == 14-j)temp += "X";
                    else temp += " ";
                    temp += "  |"; 
                    System.out.print(board.getColor(14-j) + temp + Board.RESETCOLOR);
                }
            }
            else if(i == 0){
                for(int j = 0 ; j < 6 ; j++){
                    System.out.print(board.getColor(14-j) + "|     |" + Board.RESETCOLOR);
                }
            }
            else if(i == 2){
                for(int j = 0 ; j < 6 ; j++){
                    System.out.print(board.getColor(14-j) + "|_____|" + Board.RESETCOLOR);
                }
            }System.out.println();
        }

        System.out.println();
        
        SquareType[] lst = SquareType.values();
        for(int i = 0 ; i < 8 ; i=i+2){
            System.out.print(lst[i].getColor() + lst[i].name() + Board.RESETCOLOR);
            System.out.print("            \t");
            System.out.println(lst[i+1].getColor() + lst[i+1].name() + Board.RESETCOLOR);
        }

        System.out.println();

    }

    public void printMonster(){
        try {
            File output = new File("doc/monstre1.txt");
            FileReader FileReader = new FileReader(output);
            BufferedReader buffer = new BufferedReader(FileReader);

            String line;
            while ((line = buffer.readLine()) != null) {
                System.out.println(line);
            }
            buffer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printMonster(String monstre){
        try {
            File output = new File("doc/" + monstre + ".txt");
            FileReader FileReader = new FileReader(output);
            BufferedReader buffer = new BufferedReader(FileReader);

            String line;
            while ((line = buffer.readLine()) != null) {
                System.out.println(line);
            }
            buffer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /*
    public void doAttack(Monster m){
        Die die = player.selectDie();
        int faces = die.rollatk();
        
        
        int degat = die.degat(player.getAtk(), m.getAtk(), faces);
        if (degat < 0) {
            player.degatSubi(abs(degat));
        } else {
            m.degatSubit(abs(degat));
        }
        System.out.println("PV Player : " + player.getPv());
        System.out.println("PV Monster : " + m.getPv());
        timeOut();
    }
    */

    public void doAttack(Character c1, Character c2){
        Die die = c1.selectDie();
        int degat = die.rollatk();
        c2.setPv(c2.getPv()-degat);
        if(c2.getPv()<0) c2.setPv(0);
        System.out.println(c1.getName() + " a infligé " + degat + " à " + c2.getName());
        // IL FAUT RAJOUTER LES EQUIPEMENTS ET BOOSTS
    }

    public void fightMenu(Character c){
        System.out.println(player.toString() + "\n");
        
        if(c instanceof Monster)System.out.println(((Monster)c).toString() + "\n");
        else if(c instanceof Boss)System.out.println(((Boss)c).toString() + "\n");
        
        System.out.println("1 : Lancer le dé");
        System.out.println("2 : Carte  ");
        System.out.println("3 : Objet   ");
        System.out.println("4 : Soudoyer  ");
    }


    public void fight(){
        Random r = new Random();
        int iMonstre = r.nextInt(1,3);
        try {
            File output = new File("doc/monstre" + iMonstre + ".txt");
            FileReader FileReader = new FileReader(output);
            BufferedReader buffer = new BufferedReader(FileReader);

            String line;
            while ((line = buffer.readLine()) != null) {
                System.out.println(line);
            }
            buffer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Monster m = new Monster(turn);
        this.fight(m);
    }

    
    
    public void playerTurn(Character m){    
        int choice = choiceAndPrint(1, 4);
        switch (choice) {
            case 1 -> {
                doAttack(player,m);
            }
            case 2 -> {
                if (player.getCardDeck().isEmpty()) {
                    System.out.println("Vous n'avez pas de carte à utiliser.");
                    break;
                }
                useCard(m);
            }
            case 3 -> {
                ArrayList<Slot> newInventory = new ArrayList<>();
                int choix;
                Slot item;

                for(Slot s : player.getInventory()){
                    if(!(s.getItem() instanceof Equipment) && s.getItem() != null){
                        System.out.println(s.getItem().getName());
                    }
                }
                System.out.println("Faites votre choix:");
                choix = readInt();
                item = player.getInventory().get(choix);
                if(!(item.getItem() instanceof Equipment)) {
                    newInventory.addAll(player.getInventory());
                    newInventory.remove(choix);
                    player.setInventory(newInventory);
                    player.setDef(player.getDef() + item.getItem().getAtk());
                    player.setPv(player.getPv() + item.getItem().getPv());
                    player.setShield(player.getDef() + item.getItem().getDef());
                    m.setPv(m.getPv() - item.getItem().getPv());
                    doAttack(player,m);
                }
            }
            case 4 -> {
                if(m instanceof Monster){
                    Monster mo = (Monster)m;
                    if (player.getCoin() >= mo.getFleeCost()) {
                        player.setCoin(player.getCoin() - mo.getFleeCost());
                    }
                
                } else {
                    System.out.println("tu n'as pas assez de coins, recule!");
                    timeOut();
                }
                System.out.println("Tu as décidé de prendre la fuite!");
                timeOut();
                player.setPosition(player.getPosition() - 1);
                nextTurn();
            }
        }
            
    }

    private void useCard(Character m) {
        Card ca = player.selectCard();
        player.setDef(player.getDef() + ca.getDef());
        player.setPv(player.getPv() + ca.getPv());
        player.setShield(player.getShield() + ca.getShield());
        int adv = Element.compare(ca.getAttackType(), m.getTypes().get(0));
        m.setPv(m.getPv() - (ca.getAtk() + adv * 10));
    }


    public void monsterTurn(Character m){
        doAttack(m, player);
    }

    public boolean fight(Character c) {
        cleanEcran();
        printMonster();
        System.out.println("Combat avec: " + c.getName());
        
        timeOut();

        int tourniquet = 0;
        while(player.getPv() > 0 && c.getPv() > 0){
            cleanEcran();
            printMonster();
            if(tourniquet == 0){
                fightMenu(c);
                playerTurn(c);
                timeOut();
            }else{
                monsterTurn(c);
                timeOut();
            }
            tourniquet = ++tourniquet % 2;
        }

        cleanEcran();
        
        if(player.isDead()) {
            System.out.println("Vous avez perdu :(");
            timeOut();
            return false;
        }
        
        c.messageVictoire();
        
        player.lvlUpCheck();
        timeOut();
        return true;
    }



    public void bossFight() {
        try {
            File output = new File("doc/boss.txt");
            FileReader FileReader = new FileReader(output);
            BufferedReader buffer = new BufferedReader(FileReader);

            String line;
            while ((line = buffer.readLine()) != null) {
                System.out.println(line);
            }
            buffer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Vous êtes tombé sur le boss!");
        Boss b = new Boss(player.getLevel());
        this.fight(b);
    }

    public Item loot(){
        double result = Math.random();
        ArrayList<Item> randList = new ArrayList<Item>();
        randList.addAll(items);
        randList.addAll(equipments);
        randList.addAll(cards);
        Collections.shuffle(randList);
        for(Item a : randList){
            if(result <= a.getProba()){
                return a;
            }
        }
        return null;
    }

    public void shopMenu(){
        cleanEcran();
        shopAscii();
        System.out.println();
        
        ArrayList<Item> articleList = getShopList();
        shopDialogue();
        int choice = 0;
        while(choice != 4){
            choice = choiceAndPrint(1, 4);
            if(choice == 1)shopItems((Item)articleList.get(0));
            if(choice == 2)shopEquips((Equipment)articleList.get(1));
            if(choice == 3)shopCards((Card)articleList.get(2));
            cleanEcran();
            shopAscii();
            shopDialogue();
        }

    }

    public static void shopDialogue(){
        System.out.println("Bienvenue au shop du plateau");
        System.out.println("1. Regarder le consommable à vendre");
        System.out.println("2. Regarder l'equipement à vendre");
        System.out.println("3. Regarder la carte à vendre");
        System.out.println("4. Sortir du magasin");
    }

    public void shopItems(Item i){
        cleanEcran();
        shopAscii();
        System.out.println("Voici le meilleur item de notre boutique!");

        System.out.println(i);

        System.out.println("1. Acheter l'item");
        System.out.println("2. Retour");
        int choice = choiceAndPrint(1,2);
        if(choice == 1) player.purchaseArticle(i);
        
    }

    public void shopEquips(Equipment e){
        cleanEcran();
        shopAscii();
        System.out.println("Voici l'équipement le plus fort de notre boutique!");
        
        System.out.println(e);
        
        System.out.println("1. Acheter l'équipement");
        System.out.println("2. Retour");
        int choice = choiceAndPrint(1,2);
        if(choice == 1) player.purchaseArticle(e);
    }

    public void shopCards(Card c){
        cleanEcran();
        shopAscii();
        System.out.println("Voici la carte la plus forte de notre boutique!");

        System.err.println(c);

        System.out.println("1. Acheter la carte");
        System.out.println("2. Retour");
        int choice = choiceAndPrint(1, 2);
        if(choice == 1) player.purchaseArticle(c);
    }



    static void shopAscii() {
        try {
            File output = new File("doc/Shop.txt");
            FileReader FileReader = new FileReader(output);
            BufferedReader buffer = new BufferedReader(FileReader);

            String line;
            while ((line = buffer.readLine()) != null) {
                System.out.println(line);
            }
            buffer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public ArrayList<Item> getShopList(){
        ArrayList<Item> shopList = new ArrayList<Item>();
        Random alea = new Random();
        double randItem = (alea.nextDouble());
        double randEquip = (alea.nextDouble());
        double randCard = (alea.nextDouble());
        Collections.shuffle(items);
        Collections.shuffle(equipments);
        Collections.shuffle(cards);
        double proba = 0;
        for(Item i : items){
            proba += i.getProba();
            if(randItem <= proba){
                shopList.add(i);
                break;
            }
        }
        proba = 0;
        for(Equipment e : equipments){
            proba += e.getProba();
            if(randEquip >= e.getProba()){
                shopList.add(e);
                break;
            }
        }

        proba = 0;
        for(Card c : cards){
            proba += c.getProba();
            if(randCard <= c.getProba()){
                shopList.add(c);
                break;
            }
        }
        return shopList;
    }

    public Die newDie(int nbTours) {
        Die d = new Die(0, 0, nbTours + "", null);
        int nbFaces = (int) ((Math.random()) * 10);
        int[] faces = new int[nbFaces];
        int totalFace = 0;
        for (int i = 0 ; i < nbFaces ; i ++) {
            faces[i] = (int) (Math.random() * nbTours);
            totalFace = faces[i] + totalFace;
        }
        //d.setDiceAtk(faces);
        d.setFaces(faces);
        d.setPrice(2 + totalFace/2);
        d.setProba(totalFace % 10);
        return d;
    }

}

