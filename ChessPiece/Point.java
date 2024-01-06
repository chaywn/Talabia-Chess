// Coding Member: Goh Shi Yi, Choo Yun Yi


package Chess;

import java.awt.*;

import Board.Board;

public class Point extends Piece {
    private boolean isMovingForward;

    public Point(int x, int y, boolean isWhite) {
        super(x, y, isWhite);
        this.isMovingForward = true;
    }

    @Override
    public boolean canMove(Board board, int targetX, int targetY) {
        // Implement specific move logic for Point
    }

    @Override
    public void move(int x, int y) {
        super.move(x, y); // Call the move method of the superclass

        // Additional logic specific to Point, if any
        // For example, changing direction at the end of the board
    }
}
