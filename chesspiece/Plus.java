// Coding Member: Goh Shi Yi

package chesspiece;

import java.awt.*;

import board.Board;

public class Plus extends Piece {

    public Plus(int x, int y, Color color, Boolean flipped) {
        super(x, y, color, flipped);
    }

    @Override
    public boolean isMovableTo(Board board, Piece p, int x, int y) {
        if (p.getX() == x || p.getY() == y) { // can move vertically and horizontally
            if ((board.getPieceAt(x, y) != null) && (p.getColor() == board.getPieceAt(x, y).getColor())) {
                return false;
            } else if (p.getX() == x) {
                for (int i = Math.min(p.getY(), y) + 1; i < Math.max(p.getY(), y); i++) {
                    if (board.getPieceAt(x, i) != null) {
                        return false;
                    }
                }
            } else if (p.getY() == y) {
                for (int i = Math.min(p.getX(), x) + 1; i < Math.max(p.getX(), x); i++) {
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
