// Coding Member: 

<<<<<<< HEAD
package ChessPiece; // Assuming the 'Chess' package
=======
package ChessPiece;
>>>>>>> parent of d40ab93 (Update Hourglass.java)

import java.awt.*;

import Board.Board;
<<<<<<< HEAD

public class Hourglass extends Piece {
=======
>>>>>>> parent of d40ab93 (Update Hourglass.java)

public class Hourglass extends Piece{
    
    public Hourglass(int x, int y, Color color, Boolean flipped) {
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
