/**
*
* @author Chay Wen Ning
* @author Melody Koh
*/

package chesspiece;

import java.awt.*;

import chessboard.ChessBoard;

public abstract class Piece {
    private PieceType type;
    private Color color;
    private boolean flipped; // check if the piece is flipped (to face the other side)

    // Using Enum to represent piece type constants, instead of using String which cannot perform compile-time checking
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

    Piece(int x, int y, Color color, Boolean flipped) {
        this.type = PieceType.valueOf(this.getClass().getSimpleName());
        this.color = color;
        this.x = x;
        this.y = y;
        this.flipped = flipped;
    }

    // Getters, Setters
    public PieceType getPieceType() { return type; }
    public Color getColor() { return color; }
    public boolean isFlipped() { return flipped; }
    public int getX() { return x; }
    public int getY() { return y; }
    public void setPieaceType(PieceType type) { this.type = type;}
    public void setColor(Color color) { this.color = color; }
    public void setFlipped(boolean flipped) { this.flipped = flipped; }
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    
    public void setPosition(int x, int y) { 
        this.x = x;
        this.y = y;
    }

    public abstract boolean isMovableTo(ChessBoard board, Piece p, int x, int y);

    public Piece cloneToPlus() {
        return new Plus(x, y, color, flipped);
    }

    public Piece cloneToTime() {
        return new Time(x, y, color, flipped);
    }
}
