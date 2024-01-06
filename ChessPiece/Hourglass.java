// Coding Member: 

package ChessPiece; // Assuming the 'Chess' package

import java.awt.Image;

import Board.Board;

public class Hourglass extends Piece {

    public Hourglass(int x, int y, boolean isWhite, Image image) {
        super(x, y, isWhite, image);
    }

    @Override
    public boolean canMove(Board board, int targetX, int targetY) {
        int xDistance = Math.abs(targetX - this.getX());
        int yDistance = Math.abs(targetY - this.getY());

        // Check for 3x2 L-shape move
        return (xDistance == 2 && yDistance == 3) || (xDistance == 3 && yDistance == 2);
    }


