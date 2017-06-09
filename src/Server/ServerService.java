package Server;

import Server.ExceptionsPackage.CreateSocketException;
import Server.ExceptionsPackage.LostConnectionException;
import Server.ExceptionsPackage.WaitForPlayerException;
import Server.GamePackage.Game;

import javax.net.ssl.SSLSocket;
import java.io.IOException;
import java.util.Random;

/**
 * Created by Karol on 09.06.2017.
 */
public class ServerService extends Thread {

    private Player[] players;
    private int PLAYER_LIMIT = 2;
    private int PLAYERS_NUM;
    private int gameCounter;
    private Server server;
    private Game game;

    public ServerService() throws CreateSocketException {
        players = new Player[PLAYER_LIMIT];
        PLAYERS_NUM = 0;
        server = Server.getInstance();
    }
    public ServerService(SSLSocket socket) throws CreateSocketException, LostConnectionException, Exception {
        Main.isLockFree = false;
        server = Server.getInstance();
        players = new Player[PLAYER_LIMIT];
        players[0] = new Player( socket ,0);
        players[0].sendMessage("connected");
        PLAYERS_NUM = 1;
    }
    @Override
    public void run() {
        try{
            waitForPlayers();
            runGame();

        }catch (WaitForPlayerException ex){
            System.out.println("WAIT PLAYER EXC");
            //ex.printStackTrace();
        } catch (IOException e) {
            System.out.println("IOEXCEPTION");
            //e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void waitForPlayers() throws Exception{
        try {
            while (PLAYERS_NUM < PLAYER_LIMIT) {
                System.out.println("Waiting for player number: " + (PLAYERS_NUM + 1));
                players[PLAYERS_NUM] = new Player((SSLSocket) server.serverSocket.accept(),PLAYERS_NUM);
                players[PLAYERS_NUM++].sendMessage("connected");
            }
            Main.isLockFree = true;
            players[0].sendMessage("Pierwszy");
            players[1].sendMessage("Drugi");
            String readyPlayerONE = players[0].getMessage();
            String readyPlayerTWO = players[1].getMessage();
            if (readyPlayerONE.equals("gotowy") && readyPlayerTWO.equals("gotowy")  ){
                sendMessageForAllPlayer(readyPlayerONE);
            }

        }catch (Exception e){
            Main.isLockFree = true;
            if (players[0].isAlive())players[0].sendMessage("error");
            if (players[1].isAlive()) players[1].sendMessage("error");

        }
    }
    private String playerShootToBoot(int player_numer) throws Exception {

        String[] playerHit = players[player_numer].getMessage().split("-");
        int x = Integer.parseInt(playerHit[0]);
        int y = Integer.parseInt(playerHit[1]);
        String messageToSend;
        if (game.checkHit(x,y)){
            if (game.isStillAlive())
                messageToSend = "hit";
            else messageToSend = "hit_win";
        }else{
            messageToSend ="miss";
        }
        players[0].sendMessage(messageToSend);
        return messageToSend;
    }
    private String playerShoot(int shooting_player) throws Exception {
        String shoot = players[shooting_player].getMessage();
        players[(shooting_player+1)%PLAYER_LIMIT].sendMessage(shoot);
        String answer = players[(shooting_player+1)%PLAYER_LIMIT].getMessage();
        players[shooting_player].sendMessage(answer);
        return answer;
    }
    private String bootShootToPlayer(int player_numer) throws Exception{
        Random random = new Random();
        int x = random.nextInt(10);
        int y = random.nextInt(10);
        String messageToSend = x+"-"+y;
        players[player_numer].sendMessage(messageToSend);
        String result = players[player_numer].getMessage();
        return result;
    }

    private void playWithPlayer() throws Exception {
        gameCounter = 0;
        while(true) {
            while (playerShoot(gameCounter % PLAYER_LIMIT).equals("hit")) {}
            gameCounter++;
        }
    }

    private void playWithBoot() throws Exception {
        game = new Game();
        while (true) {
            while (playerShootToBoot(0).equals("hit")) {
            }
            if (game.isStillAlive()) {
                while (bootShootToPlayer(0).equals("hit")) {
                }
            } else {
                System.out.println("Przegrałeś");
                try {

                } catch (Exception e) {

                }
            }
        }
    }

    private void runGame() throws Exception, InterruptedException {

        System.out.println("Game Running");
        while (true) {
            playWithPlayer();
        }
    }
    //players[1].sendMessage("Drugi");
///        String first_ready = players[0].getMessage();
    //String secound_ready = players[1].getMessage();
      /*  if ( first_ready.equals("Gotowy") /*&& secound_ready.equals("Gotowy")){
            System.out.println("ZACZYNAM!");
            while (true){
                //Modulo 2 aby co turę kontaktował się z graczem
                players[gameCounter%2].sendMessage("tura"+gameCounter);
                String gameResult = players[gameCounter%2].getMessage();
                System.out.println("Od gracza "+(gameCounter%2)+" "+gameResult);
                gameCounter++;
            }
        }*/

    private void sendMessageForAllPlayer(String message) throws Exception {
        for ( int i = 0 ; i < PLAYER_LIMIT; ++i)
            players[i].sendMessage(message);
    }
}

/*
        game = new Game();
        Random random = new Random();
        gameCounter = 0;
        while ( true ){
            while (playerShoot(gameCounter%PLAYERS_NUM).equals("hit")){}
            if (game.isStillAlive()) {
                while (bootShootToPlayer (gameCounter % PLAYERS_NUM).equals("hit")) {}
                gameCounter++;
            }else{
                System.out.println("Przegrałeś");
                try{

                }catch (Exception e){

                }
            }
 */

