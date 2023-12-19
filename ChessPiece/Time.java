// Coding Member:

package ChessPiece;

import java.awt.*;

import Board.Board;

public class Time extends Piece{

    public Time(int x, int y, Color color, Boolean flipped) {
        super(x, y, color, flipped);
    }

    @Override
    public boolean isMovableTo(Board board, int x, int y) {
        // TODO Auto-generated method stub
        return false;
    }
}
