// Coding Member: Goh Shi Yi, Choo Yun Yi


package ChessPiece;

import Board.Board;
import java.awt.*;

public class Point extends Piece {

    public Point(int x, int y, Color color, boolean flipped) {
        super(x, y, color, flipped);
    }

    @Override
    public boolean isMovableTo(Board board, Piece p, int x, int y) {
        // Check if the move is only vertical and within 1 or 2 steps
        int yDistance = Math.abs(y - this.getY());
        if (yDistance > 0 && yDistance <= 2) {
          
            int direction = isFlipped() ? -1 : 1;
            
           
            if ((y - this.getY()) == yDistance * direction) {
                
                for (int i = 1; i <= yDistance; i++) {
                    if (board.isSpotOccupied(this.getX(), this.getY() + i * direction)) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    // Flip the piece when it reaches the end of the board
    public void move(int x, int y) {
        super.setPosition(x, y);
        // Check if it has reached the end and flip
        if (y == 0 || y == board.getHeight() - 1) {
            setFlipped(!isFlipped());
        }
    }
}
