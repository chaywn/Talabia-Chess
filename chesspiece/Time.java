package chesspiece;

import java.awt.*;

import chessboard.ChessBoard;

/**
 * The {@code Time} class; Extends {@code Piece} abstract class.
 * Every {@code Time} piece has a x, y coordinate, a {@code PieceType}, and a {@code flipped} boolean to represent the orientation of the {@code Time} piece.
 * 
 * @see chesspiece.Piece
 * @see chesspiece.Piece.PieceType
 * @author Melody Koh Si Jie
 */
public class Time extends Piece {
    /**
     *
     * Constructs a new {@code Time} piece. This method calls the super constructor method {@code Piece}.
     *
     * @param x       the x coordinate
     * @param y       the y coordinate 
     * @param color   the {@code Color} of the {@code Time} piece
     * @param flipped the orientation of the {@code Time} piece, set {@code true} to show the piece flipped 180 degree
     * @see chesspiece.Piece#Piece(int, int, Color, Boolean)
     * @author Melody Koh Si Jie
     */
    public Time(int x, int y, Color color, Boolean flipped) {
        super(x, y, color, flipped);
    }

    /**
     *
     * Validates a {@code Time} piece movement to the specified x, y coordinates.
     * Returns {@code true} if the {@code Time} piece is movable to the specified x, y coordinates.
     * This method is to be overwritten and implemented by subclasses.
     * 
     * @param board the {@code ChessBoard} object
     * @param x the x coordinate to move to  
     * @param y the y coordinate to move to
     * @return {@code true} if the {@code Time} piece is movable to the specified x, y coordinates
     * @author Melody Koh Si Jie
     */
    @Override
    public boolean isMovableTo(ChessBoard board, int x, int y) {
        Piece piece = board.getPieceAt(x, y);
        if (Math.abs(getX() - x) == Math.abs(getY() - y)) {
            for (int i = 1; i < Math.abs(getX() - x); i++) {
                if (getX() > x && getY() > y) {
                    if (board.getPieceAt(getX() - i, getY() - i) != null) {
                        return false;
                    }
                } else if (getX() < x && getY() > y) {
                    if (board.getPieceAt(getX() + i, getY() - i) != null) {
                        return false;
                    }
                } else if (getX() > x && getY() < y) {
                    if (board.getPieceAt(getX() - i, getY() + i) != null) {
                        return false;
                    }
                } else if (getX() < x && getY() < y) {
                    if (board.getPieceAt(getX() + i, getY() + i) != null) {
                        return false;
                    }
                }
            }
            if (piece != null) {
                if (piece.getColor().equals(getColor())) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
