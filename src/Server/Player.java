package Server;

import Server.ExceptionsPackage.CloseConnectionException;
import Server.ExceptionsPackage.LostConnectionException;

import javax.net.ssl.SSLSocket;
import java.io.*;
import java.net.Socket;

/**
 * Created by Karol on 17.05.2017.
 */
public class Player {

    private  DataInputStream in;
    private DataOutputStream out;
    private SSLSocket socket;
    private boolean isLive = false;
    private int player_id;
    public Player( SSLSocket socket,int player_id) throws LostConnectionException {
        this.socket = socket;
        this.player_id = player_id;
        try {
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            System.out.println("Just connected to " + socket.getRemoteSocketAddress()+"\n"+ socket.getInetAddress());
            isLive = true;
        }catch (Exception e){
            throw new LostConnectionException();
        }

    }
    public String getMessage() throws Exception {
            try{
                String message = in.readUTF();
                System.out.println("Message from player["+player_id+"]: "+message);
                return message;
            }catch (Exception e){
                System.out.println("ERROR: get message from player["+player_id+"]");
                throw e;
            }
    }
    public boolean isConnected(){
        try {
            return socket.getInetAddress().isReachable(500);
        } catch (IOException e) {
            return false;
        }
    }
    public void sendMessage(String message) {
        try {
            System.out.println("Sending message to player[" + player_id + "]: " + message);
            out.writeUTF(message);
        }catch (Exception e){
            System.out.println("ERROR: Sending message to player[" + player_id + "]");
        }
    }
    public void close() throws CloseConnectionException {
        try {
            socket.close();
        } catch (IOException e) {
            throw new CloseConnectionException ();
        }
    }
}
