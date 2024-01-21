package chesspiece;

import java.awt.*;

import chessboard.ChessBoard;

/**
 * The {@code Hourglass} class; Extends {@code Piece} abstract class.
 * Every {@code Hourglass} piece has a x, y coordinate, a {@code PieceType}, and a {@code flipped} boolean to represent the orientation of the {@code Hourglass} piece.
 * 
 * @see chesspiece.Piece
 * @see chesspiece.Piece.PieceType
 * @author Mohamed Kamal Eldin
 */
public class Hourglass extends Piece {

    /**
     *
     * Constructs a new {@code Hourglass} piece. This method calls the super constructor method {@code Piece}.
     *
     * @param x       the x coordinate
     * @param y       the y coordinate 
     * @param color   the {@code Color} of the {@code Hourglass} piece
     * @param flipped the orientation of the {@code Hourglass} piece, set {@code true} to show the piece flipped 180 degree
     * @see chesspiece.Piece#Piece(int, int, Color, Boolean)
     * @author Mohamed Kamal Eldin
     */
    public Hourglass(int x, int y, Color color, Boolean flipped) {
        super(x, y, color, flipped);
    }

    /**
     *
     * Validates a {@code Hourglass} piece movement to the specified x, y coordinates.
     * Returns {@code true} if the {@code Hourglass} piece is movable to the specified x, y coordinates.
     * 
     * @param board the {@code ChessBoard} object
     * @param x the x coordinate to move to
     * @param y the y coordinate to move to
     * @return {@code true} if the {@code Hourglass} piece is movable to the specified x, y coordinates
     * @author Mohamed Kamal Eldin
     */
    @Override
    public boolean isMovableTo(ChessBoard board, int x, int y) {
        int xDistance = Math.abs(x - getX());
        int yDistance = Math.abs(y - getY());

        if ((board.getPieceAt(x, y) != null) && (getColor().equals(board.getPieceAt(x, y).getColor()))) {
            return false;
        }

        // Check for 3x2 L-shape move
        return ((xDistance == 1 && yDistance == 2) || (xDistance == 2 && yDistance == 1));
    }
}
