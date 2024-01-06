// Coding Member: Goh Shi Yi, Choo Yun Yi


package Chess;

import java.awt.*;

import Board.Board;

public class Point extends Piece {
    private boolean isMovingForward;
    
    public Point(int x, int y, boolean isWhite, Image image) {
        super(x, y, isWhite, image);
        this.isMovingForward = true; // Initially set to true
    }

    @Override
    public boolean canMove(Board board, int targetX, int targetY) {
        // ... implementation of canMove ...
    }

    // ... other methods ...
}
