package Server;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import java.io.*;
/**
 * Created by Karol on 17.05.2017.
 */

public class SocketSSL {
    private static SocketSSL instance = null;
    private static SSLServerSocket sslserversocket;

    protected SocketSSL(){
        SSLServerSocketFactory sslserversocketfactory =
                (SSLServerSocketFactory)SSLServerSocketFactory.getDefault();
        try {
            sslserversocket =
                    (SSLServerSocket)sslserversocketfactory.createServerSocket(7632);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static SSLSocket AceptSSL(){
        SSLSocket sslsocket = null;
        try {
            sslsocket = (SSLSocket)sslserversocket.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sslsocket;
    }

    public static SocketSSL getInstance()  {
        synchronized(SocketSSL.class){
            if(instance == null) {
                instance = new SocketSSL();
            }
            return instance;
        }
    }//koniec getInstance()


}//koniec klasy socketSSL