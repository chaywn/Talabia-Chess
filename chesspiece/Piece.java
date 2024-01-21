package chesspiece;

import java.awt.*;

import chessboard.ChessBoard;

/**
 * The abstract class of {@code Piece}; Represents a chess piece in a game.
 * Every {@code Piece} object has a x, y coordinate, a {@code PieceType}, and a {@code flipped} boolean to represent the orientation of the {@code Piece}.
 * 
 * @author Chay Wen Ning
 */
public abstract class Piece {
    private PieceType type;
    private Color color;
    private boolean flipped; // check if the piece is flipped (to face the other side)

    /**
     * The {@code PieceType} Enum; Represents a piece type of a {@code Piece}.
     * 
     * @author Chay Wen Ning
     */
    public static enum PieceType {
        HOURGLASS,
        PLUS,
        POINT,
        SUN,
        TIME;
    }

    // x, y position on the board
    private int x;
    private int y;

    /**
     *
     * Constructs a new {@code Piece} object.
     *
     * @param x       the x coordinate
     * @param y       the y coordinate 
     * @param color   the {@code Color} of the {@code Piece}
     * @param flipped the orientation of the {@code Piece}, set {@code true} to show the {@code Piece} flipped 180 degree
     * @author Chay Wen Ning
     */
    Piece(int x, int y, Color color, Boolean flipped) {
        this.type = PieceType.valueOf(this.getClass().getSimpleName().toUpperCase());
        this.color = color;
        this.x = x;
        this.y = y;
        this.flipped = flipped;
    }

    /**
     *
     * Returns the {@code PieceType} of the {@code Piece}.
     * 
     * @return the {@code PieceType} of the {@code Piece}
     * @see chesspiece.Piece.PieceType
     * @author Chay Wen Ning
     */
    public PieceType getPieceType() {
        return type;
    }

    /**
     *
     * Returns the {@code Color} of the {@code Piece}.
     * 
     * @return the {@code Color} of the {@code Piece}
     * @author Chay Wen Ning
     */
    public Color getColor() {
        return color;
    }

    /**
     *
     * Returns the {@code flipped} status of the {@code Piece}.
     * Returns {@code true} if the {@code Piece} is flipped.
     * 
     * @return {@code true} if the {@code Piece} is flipped
     * @author Chay Wen Ning
     */
    public boolean isFlipped() {
        return flipped;
    }

    /**
     *
     * Returns the x coordinate of the {@code Piece}.
     * 
     * @return the x coordinate of the {@code Piece}
     * @author Chay Wen Ning
     */
    public int getX() {
        return x;
    }

    /**
     *
     * Returns the y coordinate of the {@code Piece}.
     * 
     * @return the y coordinate of the {@code Piece}
     * @author Chay Wen Ning
     */
    public int getY() {
        return y;
    }

    /**
     *
     * Sets the {@code PieceType} of the {@code Piece}.
     * 
     * @param type the {@code PieceType} to set
     * @author Chay Wen Ning
     */
    public void setPieceType(PieceType type) {
        this.type = type;
    }

    /**
     *
     * Sets the {@code Color} of the {@code Piece}.
     * 
     * @param color the {@code Color} to set
     * @author Chay Wen Ning
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     *
     * Sets the {@code flipped} status of the {@code Piece}.
     * 
     * @param flipped the {@code flipped} status to set.
     * @author Chay Wen Ning
     */
    public void setFlipped(boolean flipped) {
        this.flipped = flipped;
    }

    /**
     *
     * Sets the x coordinate of the {@code Piece}.
     * 
     * @param x x coordinate to set
     * @see #setPosition(int, int)
     * @author Chay Wen Ning
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     *
     * Sets the y coordinate of the {@code Piece}.
     * 
     * @param y y coordinate to set
     * @see #setPosition(int, int)
     * @author Chay Wen Ning
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     *
     * Sets the x, y position of the {@code Piece}.
     * 
     * @param x x coordinate to set
     * @param y y coordinate to set
     * @see #setX(int)
     * @see #setY(int)
     * @author Chay Wen Ning
     */
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     *
     * Validates a {@code Piece} movement to the specified x, y coordinates.
     * Returns {@code true} if the {@code Piece} is movable to the specified x, y coordinates.
     * This method is to be overwritten and implemented by subclasses.
     * 
     * @param board the {@code ChessBoard} object
     * @param x the x coordinate to move to  
     * @param y the y coordinate to move to
     * @return {@code true} if the {@code Piece} is movable to the specified x, y coordinates
     * @author Chay Wen Ning
     */
    public abstract boolean isMovableTo(ChessBoard board, int x, int y);

    /**
     *
     * Clones the {@code Piece} to a {@code Plus} piece. Returns the {@code Plus} piece that contains copied data from the {@code Piece}.
     * 
     * @return the cloned {@code Plus} piece
     * @see #cloneToTime()
     * @author Chay Wen Ning
     */
    public Piece cloneToPlus() {
        return new Plus(x, y, color, flipped);
    }

    /**
     *
     * Clones the {@code Piece} to a {@code Time} piece. Returns the {@code Time} piece that contains copied data from the {@code Piece}.
     * 
     * @return the cloned {@code Time} piece
     * @see #cloneToPlus()
     * @author Chay Wen Ning
     */
    public Piece cloneToTime() {
        return new Time(x, y, color, flipped);
    }
}
