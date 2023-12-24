// Coding Member: Goh Shi Yi, Choo Yun Yi

package ChessPiece;

import java.awt.*;

import Board.Board;

public class Point extends Piece {

    public Point(int x, int y, Color color, Boolean flipped) {
        super(x, y, color, flipped);
    }

    @Override
    public boolean isMovableTo(Board board, Piece p, int x, int y) {
        // still not done (If it reaches the end of the board, it turns around and starts heading back the other way.)
        if (p.getX() == x && p.getY() > y && (Math.abs(p.getY() - y) == 1 || Math.abs(p.getY() - y) == 2)) {
            for (int i = Math.min(p.getY(), y) + 1; i < Math.max(p.getY(), y); i++) {
                if (board.getPieceAt(x, i) != null) {
                    return false;
                }
            }
            if ((board.getPieceAt(x, y) != null) && (p.getColor() == board.getPieceAt(x, y).getColor())) {
                return false;
            }
            return true;
        }
        return false;
    }
}
