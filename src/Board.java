import java.util.ArrayList;
import java.util.Random;

public class Board{
    private ArrayList<SquareType> squares;
    public final static String RESETCOLOR = "\u001B[0m";

    public Board(){
        squares = new ArrayList<SquareType>();
    }


    public ArrayList<SquareType> getSquares() {
        return this.squares;
    }

    public String getColor(int indexCase){
        return squares.get(indexCase).getColor();
    }

    public void generateBoard(){
        Random alea = new Random();
        squares.clear();
        squares.add(SquareType.START);
        for(int i = 1 ; i < 17 ; i++){
            double temp = alea.nextDouble();
            attribution(temp);
        }
        double temp = alea.nextDouble();
        if(temp < SquareType.BOSS.getRarity()) squares.add(SquareType.BOSS);
        else attribution(temp);
    }    

    public void attribution(double temp){
        if(temp < SquareType.SHOP.getRarity())squares.add(SquareType.SHOP);
        else if(temp < SquareType.LOOT.getRarity())squares.add(SquareType.LOOT);
        else if(temp < SquareType.MONSTER.getRarity())squares.add(SquareType.MONSTER);
        else if(temp < SquareType.BACKWARDS.getRarity())squares.add(SquareType.BACKWARDS);
        else if(temp < SquareType.EVENT.getRarity())squares.add(SquareType.EVENT);
        else if(temp < SquareType.SHUFFLE.getRarity())squares.add(SquareType.SHUFFLE);
    }

}