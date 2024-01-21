package chesspiece;

import java.awt.*;

import chessboard.ChessBoard;

/**
 * The {@code Plus} class ; Extends {@code Piece} abstract class.
 * Every {@code Plus} piece has a x, y coordinate, a {@code PieceType}, and a {@code flipped} boolean to represent the orientation of the {@code Plus} piece.
 * 
 * @see chesspiece.Piece
 * @see chesspiece.Piece.PieceType
 * @author Goh Shi Yi
 */
public class Plus extends Piece {
    /**
     *
     * Constructs a new {@code Plus} piece. This method calls the super constructor method {@code Piece}.
     *
     * @param x       the x coordinate
     * @param y       the y coordinate 
     * @param color   the {@code Color} of the {@code Plus} piece
     * @param flipped the orientation of the {@code Plus} piece, set {@code true} to show the piece flipped 180 degree
     * @see chesspiece.Piece#Piece(int, int, Color, Boolean)
     * @author Goh Shi Yi
     */
    public Plus(int x, int y, Color color, Boolean flipped) {
        super(x, y, color, flipped);
    }

    /**
     *
     * Validates a {@code Plus} piece movement to the specified x, y coordinates.
     * Returns {@code true} if the {@code Plus} piece is movable to the specified x, y coordinates.
     * 
     * @param board the {@code ChessBoard} object
     * @param x the x coordinate to move to
     * @param y the y coordinate to move to
     * @return {@code true} if the {@code Plus} piece is movable to the specified x, y coordinates
     * @author Goh Shi Yi
     */
    @Override
    public boolean isMovableTo(ChessBoard board, int x, int y) {
        if (getX() == x || getY() == y) { // can move vertically and horizontally
            if ((board.getPieceAt(x, y) != null) && (getColor().equals(board.getPieceAt(x, y).getColor()))) {
                return false;
            } else if (getX() == x) {
                for (int i = Math.min(getY(), y) + 1; i < Math.max(getY(), y); i++) {
                    if (board.getPieceAt(x, i) != null) {
                        return false;
                    }
                }
            } else if (getY() == y) {
                for (int i = Math.min(getX(), x) + 1; i < Math.max(getX(), x); i++) {
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
