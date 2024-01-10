/**
*
* @author Melody Koh
*/

package chesspiece;

import java.awt.*;

import board.Board;

public class Time extends Piece{

    public Time(int x, int y, Color color, Boolean flipped) {
        super(x, y, color, flipped);
    }

    @Override
    public boolean isMovableTo(Board board, Piece p, int x, int y) {
        Piece piece = board.getPieceAt(x, y);
        if (Math.abs(p.getX() - x) == Math.abs(p.getY() - y)) {
            for (int i = 1; i < Math.abs(p.getX() - x); i++) {
                if (p.getX() > x && p.getY() > y) {
                    if (board.getPieceAt(p.getX() - i, p.getY() - i) != null) {
                        return false;
                    }
                }else if (p.getX() < x && p.getY() > y) {
                    if (board.getPieceAt(p.getX() + i, p.getY() - i) != null) {
                        return false;
                    }
                } else if (p.getX() > x && p.getY() < y) {
                    if (board.getPieceAt(p.getX() - i, p.getY() + i) != null) {
                        return false;
                    }
                } else if (p.getX() < x && p.getY() < y) {
                    if (board.getPieceAt(p.getX() + i, p.getY() + i) != null) {
                        return false;
                    }
                }
            }
            if (piece != null) {
                if (piece.getColor() == p.getColor()) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
