import java.util.Arrays;

public class Die extends Article {
    private int[] faces;
    
    //private int [] diceAtk;
  

    public Die(int price, double proba, String name,int[] faces//, 
    //int [] diceAtk
    ) {
        super(price, proba, false, name);
        this.faces = faces;
        //this.diceAtk=diceAtk;
       
    }

    public int[] getFaces() {
        return faces;
    }
    

    public void setFaces(int[] faces) {
        this.faces = faces;
    }

  

    public int roll(){
        return faces[(int)(Math.random() * faces.length)];
    }
   
    
    public String toString() {
        String txt = getName() + " : ";
        txt += Arrays.toString(faces);
        /*for(int i = 0 ; i < faces.length ; i++){
            txt += faces[i] + " ";
        }*/
        return txt;
    }

    /*
    public int rollatk() {
        return diceAtk[(int)(Math.random() * diceAtk.length)];
    }
    */
    public int rollatk(){
        return roll();
    }
  
    public int degat(int atk, int atk2, int face) {
        int degatMonster; 
        int degatplayer;
        if (face < 0){
            face=face*-1;
            degatMonster=face*atk2;
            degatMonster=degatMonster*-1;
            System.out.println("le monstre a infligé "+degatMonster+" dégâts!");
            return degatMonster;
        }
        degatplayer=face*atk;
        System.out.println("Vous avez infligé "+degatplayer+" dégâts!");
        return degatplayer;
        
    }

   /*
    public void setDiceAtk(int[] diceAtk) {
        this.diceAtk = diceAtk;
    }

    public int[] getDiceAtk() {
        return diceAtk;
    }
    */
}

