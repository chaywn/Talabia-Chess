// Coding Member:

package ChessPiece;

import java.awt.*;

public class Point extends Piece{

    public Point(int x, int y, Color color, Boolean flipped) {
        super(x, y, color, flipped);
    }

    @Override
    public boolean isMovableTo(int x, int y) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void moveTo(int x, int y) {
        // TODO Auto-generated method stub

    }
}
