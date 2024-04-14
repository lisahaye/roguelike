public class InvalidFileFormatException extends Exception{
    public InvalidFileFormatException(String message){
        super(message);
    }

    public InvalidFileFormatException(){
        super("Invalid file header!");
    }
}
