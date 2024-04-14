import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    static Game g;
    static void NameOFGame() {
        try {
            File output = new File("doc" + Game.SEPARATOR + "output(2).txt");
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
        Scanner sc = new Scanner(System.in);
        System.out.println("Appuyez sur entrée pour commencer :");
        sc.nextLine();

        for (int i = 0 ; i<= 20 ; i++){
            System.out.println("\n");
        }
        
    }

    static void menu() {
        System.out.println("Bienvenue sur le Dice Dungeon");
        System.out.println("Veuillez choisir une option :");
        System.out.println("1. Nouvelle partie");
        System.out.println("2. Charger une partie");
        System.out.println("3. Quitter");

        
        int choice = Game.choiceAndPrint(1, 3);

        if (choice != 1 && choice != 2 && choice != 3) {
            System.out.println("error");
        } else {
            //System.out.println("Vous avez choisi le choix  " + choice);
            g = new Game(new Player("Player"));
            g.getBoard().generateBoard();
            Game.cleanEcran();
        }
      
    }
         
/*
    static void lobby(){
        Game.cleanEcran();
    }
*/
    public static void main(String[] args) throws FileNotFoundException {
        NameOFGame();

        menu();
        
        
        for (int i = 0 ; i<= 30 ; i++)System.out.println("\n");

        //cas ou le joueur perd
        Player player = new Player("Hero");
        while (!player.gameOver()) {
            //code des tours de jeu à rajouter ici
            //System.out.println(g.getPlayer().getInventory());
            g.loadResources("rsc");
            g.nextTurn();

            Game.cleanEcran();
            
        }
        if (player.gameOver()) {
            menu();
        }

    
    }
}