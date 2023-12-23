// Coding Member:

package ChessPiece;

import java.awt.*;

import Board.Board;

public class Sun extends Piece{

    public Sun(int x, int y, Color color, Boolean flipped) {
        super(x, y, color, flipped);
    }

    @Override
    public boolean isMovableTo(Board board, Piece p, int x, int y) {
        // testing purpose
        if ((board.getPieceAt(x, y) != null) && (p.getColor() == board.getPieceAt(x, y).getColor())) {
            return false;
        }
        return true;
    }
}
