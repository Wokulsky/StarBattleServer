package Server;

import Server.ExceptionsPackage.CreateSocketException;
import Server.ExceptionsPackage.WaitForPlayerException;
import Server.GamePackage.Game;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.Random;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
/**
 * Created by Karol on 17.05.2017.
 */
public class Server {

    private int HOST_ADRESS = 9090;
    private SSLServerSocketFactory serverFactory;
    public SSLServerSocket serverSocket = null;
    private static Server instance = null;

    public static Server getInstance() throws CreateSocketException {
        if (instance == null) instance = new Server();
        return instance;
    }

    private Server() throws CreateSocketException {
        try {
            serverFactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
            serverSocket = (SSLServerSocket) serverFactory.createServerSocket(HOST_ADRESS);
        } catch (IOException e) {
            System.out.println("ERROR: przy tworzeniu socketu");
            throw new CreateSocketException();
        }
    }
}
