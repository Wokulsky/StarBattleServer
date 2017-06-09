package Server.GamePackage;

/**
 * Created by Karol on 04.06.2017.
 */
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Created by Karol on 03.06.2017.
 */
public class Cell extends Rectangle {
    public int x, y;
    public Ship ship = null;
    public boolean wasShot = false;

    private Board board;

    public Cell(int x, int y, Board board) {
        super(30, 30);
        this.x = x;
        this.y = y;
        this.board = board;
        setFill(Color.LIGHTGRAY);
        setStroke(Color.BLACK);
    }

    public boolean shoot() {

        if (!wasShot){
            wasShot = true;
            setFill(Color.BLACK);
            setStroke(Color.BLACK);
            if (ship != null) {
                ship.hit();
                setFill(Color.RED);
                if (!ship.isAlive()) {
                    board.ships--;
                }
                return true;
            }
        }
        return false;
    }
    public boolean hit(){
        wasShot = true;
        setFill(Color.RED);
        if (ship != null){
            ship.hit();
            if (!ship.isAlive()) {
                board.ships--;
            }
        }
        return true;
    }
    public boolean miss(){
        setFill(Color.BLACK);
        return false;
    }
    public void setColor(Color color){
        setFill(color);
    }
}