/**
*
* @author Chay Wen Ning
*/

package player;
import board.Board;
import chesspiece.*;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

public class Player {
    private static Color[] colors= {Color.YELLOW, Color.BLUE};
    private static int playerCount = 0;

    private int index;
    private Color color;
    private Set<Piece> pieces = new HashSet<>();

    public int playCount;
    private boolean hasPlayed;

    public Player() {
        this.index = playerCount++;
        this.color = colors[index];
        playCount = 0;
        hasPlayed = false;
    }

    // Getters and Setters
    public Color getColor() { return color; }
    public int getIndex() { return index; }
    public Set<Piece> getPieces() { return pieces; }
    public void setColor(Color color) { this.color = color; }
    public int getPlayCount() { return playCount; }
    public void setIndex(int index) { this.index = index; }
    public boolean hasPlayed() { return hasPlayed; }
    public void setHasPlayed(boolean hasPlayed) { this.hasPlayed = hasPlayed; }
    
    public void incrementPlayCount() {
        playCount++;
    }

    public void resetPlayCount() { 
        playCount = 0; 
    }

    public void resetHasPlayed() {
        hasPlayed = false;
    }

    public void addPiece(Piece piece) {
        pieces.add(piece);
    }

    public void removePiece(Piece piece) {
        if (pieces.contains(piece)) {
            pieces.remove(piece);
        }
    }

    public void clearPieces() {
        pieces.clear();
    }

    public void initializePieces(Board board, int offsetX, int offsetY, boolean opposite) {
        int offsetY2 = opposite == true ? 1 : 0;

        // Initialize Point pieces
        for (int i = 0; i < 7; i++) {
            addPiece(new Point(offsetX + i, offsetY + offsetY2, color, opposite));
        }

        offsetY2 = offsetY2 == 1 ? 0 : 1;

        // Initialize Plus pieces
        addPiece(new Plus(offsetX, offsetY + offsetY2, color, opposite));
        addPiece(new Plus(offsetX + 6, offsetY + offsetY2, color, opposite));

        // Initialize Hourglass pieces
        addPiece(new Hourglass(offsetX + 1, offsetY + offsetY2, color, opposite));
        addPiece(new Hourglass(offsetX + 5, offsetY + offsetY2, color, opposite));

        // Initialize Time pieces
        addPiece(new Time(offsetX + 2, offsetY + offsetY2, color, opposite));
        addPiece(new Time(offsetX + 4, offsetY + offsetY2, color, opposite));

        // Initialize Sun piece
        addPiece(new Sun(offsetX + 3, offsetY + offsetY2, color, opposite));

        // Update pieces onto the board
        board.setPlayerPiece(this);
    } 

    public static void resetPlayerCount() {
        playerCount = 0;
    }
}
