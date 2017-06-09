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
    public SSLSocket socket;
    private boolean isAlive = false;
    private int player_id;

    public Player( SSLSocket socket,int player_id) throws LostConnectionException {
        this.socket = socket;
        this.player_id = player_id;
        try {
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            System.out.println("Just connected to " + socket.getRemoteSocketAddress()+"\n"+ socket.getInetAddress());
            isAlive = true;
        }catch (Exception e){
            isAlive = false;
            System.out.println("ERROR: przy tworzeniu Playera");
            throw new LostConnectionException();
        }

    }
    public boolean isAlive(){
        return isAlive;
    }
    public String getMessage() throws Exception {
            try{
                String message = in.readUTF();
                System.out.println("Message from player["+player_id+"]: "+message);
                return message;
            }catch (Exception e){
                System.out.println("ERROR: get message from player["+player_id+"]");
                isAlive = false;
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
    public void sendMessage(String message) throws Exception {
        try {
            out.writeUTF(message);
            System.out.println("Sending message to player[" + player_id + "]: " + message);
        }catch (Exception e){
            System.out.println("ERROR: Sending message to player[" + player_id + "]");
            isAlive = false;
            throw e;
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
