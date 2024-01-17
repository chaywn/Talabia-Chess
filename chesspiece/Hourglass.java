/**
*
* @author 
*/

package chesspiece;

import java.awt.*;

import chessboard.ChessBoard;

public class Hourglass extends Piece {

    /**
     *
     * Hourglass
     *
     * @param x 
     * @param y  
     * @param color  
     * @param flipped  
     * @return public
     */
    public Hourglass(int x, int y, Color color, Boolean flipped) {
        super(x, y, color, flipped);
    }

    @Override
    public boolean isMovableTo(ChessBoard board, Piece p, int x, int y) {
        int xDistance = Math.abs(x - p.getX());
        int yDistance = Math.abs(y - p.getY());

        if ((board.getPieceAt(x, y) != null) && (p.getColor().equals(board.getPieceAt(x, y).getColor()))) {
            return false;
        }

        // Check for 3x2 L-shape move
        return ((xDistance == 1 && yDistance == 2) || (xDistance == 2 && yDistance == 1));
    }
}
