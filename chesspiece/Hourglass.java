/**
*
* @author 
*/

package chesspiece;

import java.awt.*;

import chessboard.ChessBoard;

/**
 * The {@code Hourglass} class ; Extends {@code Piece} class.
 */
public class Hourglass extends Piece {

    /**
     *
     * Constructs a new {@code Hourglass} piece.
     *
     * @param x       x coordinate
     * @param y       y coordinate
     * @param color   the piece color in the form of Color
     * @param flipped in the form of boolean the orientation of the piece
     */
    public Hourglass(int x, int y, Color color, Boolean flipped) {
        super(x, y, color, flipped);
    }

    /**
     *
     * Movement of the Hourglass piece
     * 
     * @param board the {@code ChessBoard}
     * @param p     the {@co{@code ChessBoard}
     *              * @param x x coordinae
     * @param x     x coordinate
     * @param y     y coordinate
     * @return {@code true} if the piece is movable
     */
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
