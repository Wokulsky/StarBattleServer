package Server;

import Server.ExceptionsPackage.CreateSocketException;
import Server.ExceptionsPackage.LostConnectionException;

import javax.net.ssl.SSLSocket;
import java.io.IOException;

/**
 * Created by Karol on 17.05.2017.
 */
public class Main {
    public static volatile boolean isLockFree = true;
    public static void main(String[] args) {
        String currentDir = System.getProperty("user.dir")+"/keyStore.p12";
        System.setProperty("javax.net.ssl.keyStore",currentDir);
        System.setProperty("javax.net.ssl.keyStorePassword","djangoiza");
        System.setProperty("javax.net.ssl.keyStoreType","PKCS12");
        System.setProperty("javax.net.ssl.trustStore",currentDir);
        System.setProperty("javax.net.ssl.trustStorePassword","djangoiza");
        System.setProperty("javax.net.ssl.trustStoreType","PKCS12");
        int connectCounter = 0 ;
            while ( true) {
                try {
                    //
                    if (isLockFree) {
                        new ServerService((SSLSocket) Server.getInstance().serverSocket.accept()).start();
                        connectCounter++;
                        System.out.println("Liczba thread: "+ connectCounter);
                    }

                } catch (CreateSocketException e) {
                    //e.printStackTrace();
                    System.out.println("Problem przy uruchomieniu ServerService");
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (LostConnectionException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

    }
}
