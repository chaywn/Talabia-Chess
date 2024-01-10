/**
*
* @author Goh Shi Yi
* @author Choo Yun Yi
*/


package chesspiece;

import java.awt.*;

import board.Board;

public class Point extends Piece {

    public Point(int x, int y, Color color, boolean flipped) {
        super(x, y, color, flipped);
    }

    @Override
    public boolean isMovableTo(Board board, Piece p, int x, int y) {
        // Check if the move is only vertical and within 1 or 2 steps
        int yDistance = Math.abs(y - this.getY());
        if (x == this.getX() && yDistance > 0 && yDistance <= 2) {
          
            int direction = isFlipped() ? 1 : -1;
            
            if ((y - this.getY()) / yDistance != direction) {
                return false;
            }
        
            for (int i = 1; i < yDistance; i++) {
                if ((board.getPieceAt(x, p.getY() + i * direction) != null)) {
                    return false;
                }
            }
            if ((board.getPieceAt(x, y) != null) && (p.getColor() == board.getPieceAt(x, y).getColor())) {
                return false;
            }
            return true;
        }
        
        return false;
    }
}
