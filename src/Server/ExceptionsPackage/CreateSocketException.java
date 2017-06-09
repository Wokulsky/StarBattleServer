package Server.ExceptionsPackage;

/**
 * Created by Karol on 24.05.2017.
 */
public class CreateSocketException extends Exception {
    public CreateSocketException(String message){
        super(message);
    }
    public CreateSocketException(){
        super("Błąd ze alokacją socket'u!");
    }
}
