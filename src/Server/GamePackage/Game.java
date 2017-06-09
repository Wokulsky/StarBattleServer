package Server.GamePackage;

import javafx.util.Pair;

import java.util.Random;

/**
 * Created by Karol on 04.06.2017.
 */
public class Game {
    private Board board;
    public Game(){
        board = new Board();
        int type = 5;
        while (type > 0) {
            Random random = new Random();
            int x = random.nextInt(10);
            int y = random.nextInt(10);
            if (board.placeShip(new Ship(type, Math.random() < 0.5), x, y)) {
                type--;
            }
        }
    }
    public boolean checkHit(int x, int y){
        Cell cell = board.getCell(x,y);
        return cell.shoot();
    }
    public Pair<Integer,Integer> shootPlayer(){
        Random random = new Random();
        int x = random.nextInt(10);
        int y = random.nextInt(10);
        return new Pair<>(x,y);
    }
    public boolean isStillAlive(){
        if (board.ships == 0) return false;
        return true;
    }
}
