package chesspiece;

import java.awt.*;

import chessboard.ChessBoard;

/**
 * The {@code Sun} class; Extends {@code Piece} abstract class.
 * Every {@code Sun} piece has a x, y coordinate, a {@code PieceType}, and a {@code flipped} boolean to represent the orientation of the {@code Sun} piece.
 * 
 * @see chesspiece.Piece
 * @see chesspiece.Piece.PieceType
 * 
 * @author Goh Shi Yi
 * @author Choo Yun Yi
 */
public class Sun extends Piece {
    /**
     *
     * Constructs a new {@code Sun} piece. This method calls the super constructor method {@code Piece}.
     *
     * @param x       the x coordinate
     * @param y       the y coordinate 
     * @param color   the {@code Color} of the {@code Sun} piece
     * @param flipped the orientation of the {@code Sun} piece, set {@code true} to show the piece flipped 180 degree
     * @see chesspiece.Piece#Piece(int, int, Color, Boolean)
     * @author Goh Shi Yi
     * @author Choo Yun Yi
     */
    public Sun(int x, int y, Color color, Boolean flipped) {
        super(x, y, color, flipped);
    }

    /**
     *
     * Validates a {@code Sun} piece movement to the specified x, y coordinates.
     * Returns {@code true} if the {@code Sun} piece is movable to the specified x, y coordinates.
     * This method is to be overwritten and implemented by subclasses.
     * 
     * @param board the {@code ChessBoard} object
     * @param x the x coordinate to move to  
     * @param y the y coordinate to move to
     * @return {@code true} if the {@code Sun} piece is movable to the specified x, y coordinates
     * @author Goh Shi Yi
     * @author Choo Yun Yi
     */
    @Override
    public boolean isMovableTo(ChessBoard board, int x, int y) {
        if ((Math.abs(getX() - x) == 1 && (Math.abs(getY() - y) == 0 || Math.abs(getY() - y) == 1))
                || Math.abs(getY() - y) == 1 && (Math.abs(getX() - x) == 0 || Math.abs(getX() - x) == 1)) { // can move only one step in any direction
            if ((board.getPieceAt(x, y) != null) && (getColor().equals(board.getPieceAt(x, y).getColor()))) {
                return false;
            }
            return true;
        }
        return false;
    }
}
