package Server;

import javax.net.ssl.SSLSocket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.SocketException;

/**
 * Created by Karol on 17.05.2017.
 */
public class SupportSSL extends Thread{
    private SSLSocket sslsocket;
    public SupportSSL(SSLSocket sslsocket) {
        super("SupportSSL");
        this.sslsocket = sslsocket;
    }
    @Override
    public void run() {
        while (true) {
            try {

                BufferedReader br = new BufferedReader(new InputStreamReader(sslsocket.getInputStream()));
                PrintWriter pw = new PrintWriter(sslsocket.getOutputStream());
                String data = br.readLine();
                if (!(data == null))
                    try {
                        System.out.println("Wiadomosc od kilenta: "+data);
                        pw.flush();
                    } catch (Exception e) {

                    }

            } catch (SocketException ioe) {return;
            } catch (IOException ioe) {return;}
        }//koniec while'a
    }//koniec run'a
}
