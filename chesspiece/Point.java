/**
*
* @author Goh Shi Yi
* @author Choo Yun Yi
*/


package chesspiece;

import java.awt.*;

import chessboard.ChessBoard;

/**
 * The {@code Point} class ; Extends {@code Piece} class.
 */
public class Point extends Piece {
    /**
     *
     * Constructs a new {@code Point} piece.
     *
     * @param x x coordinate  
     * @param y y coordinate  
     * @param color the piece color in the form of Color
     * @param flipped the orientation of the piece in the form of boolean
     */
    public Point(int x, int y, Color color, boolean flipped) {
        super(x, y, color, flipped);
    }

    /**
     *
     * Movement of the Point piece
     * 
     * @param board the {@code ChessBoard}  
     * @param p the {@code Piece}   
     * @param x x coordinate  
     * @param y y coordinate  
     * @return {@code true} if the piece is movable
     */
    @Override
    public boolean isMovableTo(ChessBoard board, Piece p, int x, int y) {
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
            if ((board.getPieceAt(x, y) != null) && (p.getColor().equals(board.getPieceAt(x, y).getColor()))) {
                return false;
            }
            return true;
        }
        
        return false;
    }
}
