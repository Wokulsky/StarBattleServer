package Server.ExceptionsPackage;

import java.io.IOException;

/**
 * Created by Karol on 24.05.2017.
 */
public class LostConnectionException extends Exception {
    public LostConnectionException(String message) {
        super(message);
    }
    public LostConnectionException(){
        super("Zgubiłem połączenie");
    }
}
