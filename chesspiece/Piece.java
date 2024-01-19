/**
*
* @author Chay Wen Ning
* @author Melody Koh
*/

package chesspiece;

import java.awt.*;

import chessboard.ChessBoard;

/**
 * The abstract class {@code Piece}
 */
public abstract class Piece {
    private PieceType type;
    private Color color;
    private boolean flipped; // check if the piece is flipped (to face the other side)

    // Using Enum to represent piece type constants, instead of using String which
    // cannot perform compile-time checking
    public static enum PieceType {
        Hourglass,
        Plus,
        Point,
        Sun,
        Time;
    }

    // x, y position on the board
    private int x;
    private int y;

    /**
     *
     * Constructs a new chess {@code Piece}.
     *
     * @param x       x coordinate  
     * @param y       y coordinate  
     * @param color   the piece color in the form of Color
     * @param flipped the orientation of the piece in the form of boolean
     */
    Piece(int x, int y, Color color, Boolean flipped) {
        this.type = PieceType.valueOf(this.getClass().getSimpleName());
        this.color = color;
        this.x = x;
        this.y = y;
        this.flipped = flipped;
    }

    /**
     *
     * Get the piece type
     * 
     * @return the piece type
     */
    public PieceType getPieceType() {
        return type;
    }

    /**
     *
     * Get the piece color
     * 
     * @return the piece color
     */
    public Color getColor() {
        return color;
    }

    /**
     *
     * Check whether the chess board is flipped
     * 
     * @return {@code true} if the piece is flipped
     */
    public boolean isFlipped() {
        return flipped;
    }

    /**
     *
     * Get the x coordinate
     * 
     * @return the x coordinate
     */
    public int getX() {
        return x;
    }

    /**
     *
     * Get the y coordinate
     * 
     * @return the y coordinate
     */
    public int getY() {
        return y;
    }

    /**
     *
     * Set the piece type
     * 
     * @param type the piece type  Type
     */
    public void setPieceType(PieceType type) {
        this.type = type;
    }

    /**
     *
     * Set the piece color
     * 
     * @param color the piece color in the form of Color
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     *
     * Set the orientation of the chess board
     * 
     * @param flipped the orientation of the chess board in the form of boolean
     */
    public void setFlipped(boolean flipped) {
        this.flipped = flipped;
    }

    /**
     *
     * Set the x coordinate
     * 
     * @param x x coordinate  
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     *
     * Set the y coordinate
     * 
     * @param y y coordinate  
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     *
     * Set the position
     * 
     * @param x x coordinate  
     * @param y y coordinate  
     */
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     *
     * Movement
     * 
     * @param board the {@code ChessBoard} 
     * @param p the {@code Piece}  
     * @param x x coordinate  
     * @param y y coordinate  
     */
    public abstract boolean isMovableTo(ChessBoard board, Piece p, int x, int y);

    /**
     *
     * clone to the Plus piece
     * 
     * @return the cloned Plus piece
     */
    public Piece cloneToPlus() {
        return new Plus(x, y, color, flipped);
    }

    /**
     *
     * clone to the Time piece
     * 
     * @return the cloned Time piece
     */
    public Piece cloneToTime() {
        return new Time(x, y, color, flipped);
    }
}
