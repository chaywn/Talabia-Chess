/**
*
* @author Goh Shi Yi
*/

package chesspiece;

import java.awt.*;

import chessboard.ChessBoard;

/**
 * The {@code Plus} class ; Extends {@code Piece} class.
 */
public class Plus extends Piece {
    /**
     *
     * Constructs a new {@code Plus} piece.
     *
     * @param x x coordinate  
     * @param y y coordinate  
     * @param color the piece color in the form of Color
     * @param flipped the orientation of the piece in the form of boolean
     */
    public Plus(int x, int y, Color color, Boolean flipped) {
        super(x, y, color, flipped);
    }

    /**
     *
     * Movement of the Plus piece
     * 
     * @param board the {@code ChessBoard} 
     * @param p     the {@code Piece}  
     * @param x     x coordinate  
     * @param y     y coordinate  
     * @return {@code true} if the piece is movable
     */
    @Override
    public boolean isMovableTo(ChessBoard board, Piece p, int x, int y) {
        if (p.getX() == x || p.getY() == y) { // can move vertically and horizontally
            if ((board.getPieceAt(x, y) != null) && (p.getColor().equals(board.getPieceAt(x, y).getColor()))) {
                return false;
            } else if (p.getX() == x) {
                for (int i = Math.min(p.getY(), y) + 1; i < Math.max(p.getY(), y); i++) {
                    if (board.getPieceAt(x, i) != null) {
                        return false;
                    }
                }
            } else if (p.getY() == y) {
                for (int i = Math.min(p.getX(), x) + 1; i < Math.max(p.getX(), x); i++) {
                    if (board.getPieceAt(i, y) != null) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }
}
