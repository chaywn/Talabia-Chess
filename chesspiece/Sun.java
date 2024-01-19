/**
*
* @author Goh Shi Yi
* @author Choo Yun Yi
*/
package chesspiece;

import java.awt.*;

import chessboard.ChessBoard;

/**
 * The {@code Sun} class ; Extends {@code Piece} class.
 */
public class Sun extends Piece {
    /**
     *
     * Constructs a new {@code Sun} piece.
     *
     * @param x x coordinate  
     * @param y y coordinate  
     * @param color the piece color in the form of Color
     * @param flipped the orientation of the piece in the form of boolean
     */
    public Sun(int x, int y, Color color, Boolean flipped) {
        super(x, y, color, flipped);
    }

    /**
     *
     * Movement of the Sun piece
     * 
     * @param board the {@code ChessBoard}  
     * @param p     the {@code Piece}  
     * @param x     x coordinate  
     * @param y     y coordinate  
     * @return {@code true} if the piece is movable
     */
    @Override
    public boolean isMovableTo(ChessBoard board, Piece p, int x, int y) {
        if ((Math.abs(p.getX() - x) == 1 && (Math.abs(p.getY() - y) == 0 || Math.abs(p.getY() - y) == 1))
                || Math.abs(p.getY() - y) == 1 && (Math.abs(p.getX() - x) == 0 || Math.abs(p.getX() - x) == 1)) { // can move only one step in any direction
            if ((board.getPieceAt(x, y) != null) && (p.getColor().equals(board.getPieceAt(x, y).getColor()))) {
                return false;
            }
            return true;
        }
        return false;
    }
}
