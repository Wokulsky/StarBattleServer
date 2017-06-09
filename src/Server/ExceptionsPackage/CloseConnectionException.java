package Server.ExceptionsPackage;

import java.io.IOException;

/**
 * Created by Karol on 24.05.2017.
 */
public class CloseConnectionException extends Exception {
    public CloseConnectionException(){
        super("Nie mogę zamnkąć połączenia!");
    }
}
