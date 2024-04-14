
public enum SquareType {
    SHOP("\u001B[32m", 0.2), LOOT("\u001B[33m", 0.3), MONSTER("\033[38;5;208m", 0.7), BOSS("\u001B[31m", 0.5), BACKWARDS("\u001B[35m", 0.8), EVENT("\u001B[34m", 0.9), START("\u001B[37m", 1), SHUFFLE("\u001B[30m", 1);
    String color;
    double rarity;

    private SquareType(String color, double rarity){
        this.color = color;
        this.rarity = rarity;
    }

    public String getColor(){
        return this.color;
    }
    
    public double getRarity(){
        return rarity;
    }
}
