/**
*
* @author Melody Koh
*/

package chesspiece;

import java.awt.*;

import chessboard.ChessBoard;
/**
 * The {@code Time} class ; Extends {@code Piece} class.
 */
public class Time extends Piece {
    /**
     *
     * Constructs a new {@code Time} piece.
     *
     * @param x x coordinate  
     * @param y y coordinate  
     * @param color the piece color in the form of Color
     * @param flipped the orientation of the piece in the form of boolean
     */
    public Time(int x, int y, Color color, Boolean flipped) {
        super(x, y, color, flipped);
    }

    /**
     *
     * Movement of the Time piece
     * 
     * @param board the {@code ChessBoard} 
     * @param p     the {@code Piece} 
     * @param x     x coordinate  
     * @param y     y coordinate  
     * @return {@code true} if the piece is movable
     */
    @Override
    public boolean isMovableTo(ChessBoard board, Piece p, int x, int y) {
        Piece piece = board.getPieceAt(x, y);
        if (Math.abs(p.getX() - x) == Math.abs(p.getY() - y)) {
            for (int i = 1; i < Math.abs(p.getX() - x); i++) {
                if (p.getX() > x && p.getY() > y) {
                    if (board.getPieceAt(p.getX() - i, p.getY() - i) != null) {
                        return false;
                    }
                } else if (p.getX() < x && p.getY() > y) {
                    if (board.getPieceAt(p.getX() + i, p.getY() - i) != null) {
                        return false;
                    }
                } else if (p.getX() > x && p.getY() < y) {
                    if (board.getPieceAt(p.getX() - i, p.getY() + i) != null) {
                        return false;
                    }
                } else if (p.getX() < x && p.getY() < y) {
                    if (board.getPieceAt(p.getX() + i, p.getY() + i) != null) {
                        return false;
                    }
                }
            }
            if (piece != null) {
                if (piece.getColor().equals(p.getColor())) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
