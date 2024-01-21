package chesspiece;

import java.awt.*;

import chessboard.ChessBoard;

/**
 * The {@code Point} class; Extends {@code Piece} abstract class.
 * Every {@code Point} piece has a x, y coordinate, a {@code PieceType}, and a {@code flipped} boolean to represent the orientation of the {@code Point} piece.
 * 
 * @see chesspiece.Piece
 * @see chesspiece.Piece.PieceType
 * @author Goh Shi Yi
 * @author Choo Yun Yi
 * @author Mohamed Kamal Eldin
 */
public class Point extends Piece {
    /**
     *
     * Constructs a new {@code Point} piece. This method calls the super constructor method {@code Piece}.
     *
     * @param x       the x coordinate
     * @param y       the y coordinate 
     * @param color   the {@code Color} of the {@code Point} piece
     * @param flipped the orientation of the {@code Point} piece, set {@code true} to show the piece flipped 180 degree
     * @see chesspiece.Piece#Piece(int, int, Color, Boolean)
     * @author Goh Shi Yi
     * @author Choo Yun Yi
     */
    public Point(int x, int y, Color color, boolean flipped) {
        super(x, y, color, flipped);
    }

    /**
     *
     * Validates a {@code Point} piece movement to the specified x, y coordinates.
     * Returns {@code true} if the {@code Point} piece is movable to the specified x, y coordinates.
     * This method is to be overwritten and implemented by subclasses.
     * 
     * @param board the {@code ChessBoard} object
     * @param x the x coordinate to move to  
     * @param y the y coordinate to move to
     * @return {@code true} if the {@code Point} piece is movable to the specified x, y coordinates
     * @author Goh Shi Yi
     * @author Choo Yun Yi
     * @author Mohamed Kamal Eldin
     */
    @Override
    public boolean isMovableTo(ChessBoard board, int x, int y) {
        // Check if the move is only vertical and within 1 or 2 steps
        int yDistance = Math.abs(y - getY());
        if (x == getX() && yDistance > 0 && yDistance <= 2) {
          
            int direction = isFlipped() ? 1 : -1;
            
            if ((y - getY()) / yDistance != direction) {
                return false;
            }
        
            for (int i = 1; i < yDistance; i++) {
                if ((board.getPieceAt(x, getY() + i * direction) != null)) {
                    return false;
                }
            }
            if ((board.getPieceAt(x, y) != null) && (getColor().equals(board.getPieceAt(x, y).getColor()))) {
                return false;
            }
            return true;
        }
        
        return false;
    }
}
