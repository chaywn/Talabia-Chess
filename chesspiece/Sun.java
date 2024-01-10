/**
*
* @author Goh Shi Yi
* @author Choo Yun Yi
*/
package chesspiece;

import java.awt.*;

import board.Board;

public class Sun extends Piece {

    public Sun(int x, int y, Color color, Boolean flipped) {
        super(x, y, color, flipped);
    }

    @Override
    public boolean isMovableTo(Board board, Piece p, int x, int y) {
        if ((Math.abs(p.getX() - x) == 1 && (Math.abs(p.getY() - y) == 0 || Math.abs(p.getY() - y) == 1) )|| Math.abs(p.getY() - y) == 1 && (Math.abs(p.getX() - x) == 0 || Math.abs(p.getX() - x) == 1)) { //can move only one step in any direction
            if ((board.getPieceAt(x, y) != null) && (p.getColor() == board.getPieceAt(x, y).getColor())) {
                return false;
            }
            return true;
        }
        return false;
    }
}
