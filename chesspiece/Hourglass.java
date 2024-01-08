// Coding Member: 

package chesspiece;

import java.awt.*;

import board.Board;

public class Hourglass extends Piece{
    
    public Hourglass(int x, int y, Color color, Boolean flipped) {
        super(x, y, color, flipped);
    }

    @Override
    public boolean isMovableTo(Board board, Piece p, int x, int y) {
        int xDistance = Math.abs(x - p.getX());
        int yDistance = Math.abs(y - p.getY());

        if ((board.getPieceAt(x, y) != null) && (p.getColor() == board.getPieceAt(x, y).getColor())) {
            return false;
        }

        // Check for 3x2 L-shape move
        return ((xDistance == 1 && yDistance == 2) || (xDistance == 2 && yDistance == 1));
    }
}
