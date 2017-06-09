package Server.ExceptionsPackage;

import java.io.IOException;

/**
 * Created by Karol on 24.05.2017.
 */
public class WaitForPlayerException extends IOException{
    public WaitForPlayerException(){
        super("Problem z oczekiwaniem na graczy!");
    }
    public WaitForPlayerException(String message){
        super(message);
    }
}
