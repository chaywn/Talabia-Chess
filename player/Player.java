/**
*
* @author Chay Wen Ning
*/

package player;

import chesspiece.*;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

import chessboard.ChessBoard;

/**
 * The {@code Player} class; Represents a player in the chess game.
 */
public class Player {
    private static Color[] colors = { Color.YELLOW, Color.BLUE };
    private static int playerCount = 0;

    private int index;
    private Color color;
    private Set<Piece> pieces = new HashSet<>();

    private int playCount;
    private boolean hasPlayed;

    /**
     * Constructs a new {@code Player}; The index of which is based on the number of existing players.
     */
    public Player() {
        this.index = playerCount++;
        this.color = colors[index];
        playCount = 0;
        hasPlayed = false;
    }

    /**
     *
     * Get the piece {@code Color}.
     * 
     * @return the piece {@code Color}
     */
    public Color getColor() {
        return color;
    }

    /**
     *
     * Get the index of player.
     * 
     * @return the player index
     */
    public int getIndex() {
        return index;
    }

    /**
     *
     * Get the chess pieces
     * 
     * @return the chess pieces 
     */
    public Set<Piece> getPieces() {
        return pieces;
    }

   /**
     *
     * Set the piece color
     * 
     * @param color the piece color in the form of Color
     */
    public void setColor(Color color) {
        this.color = color;
    }

   /**
     *
     * Get the play count
     * 
     * @return the play count
     */
    public int getPlayCount() {
        return playCount;
    }

   /**
     *
     * Set the the play count
     * 
     * @param playCount the play count  
     */
    public void setPlayCount(int playCount) {
        this.playCount = playCount;
    }

   /**
     *
     * Set the index of player
     * 
     * @param index the player index  
     */
    public void setIndex(int index) {
        this.index = index;
    }

   /**
     *
     * Check whether the player has played
     * 
     * @return {@code true} if player played a piece move
     */
    public boolean hasPlayed() {
        return hasPlayed;
    }

   /**
     *
     * Set the status of player
     * 
     * @param hasPlayed whether the player has moved a piece in the form of boolean
     */
    public void setHasPlayed(boolean hasPlayed) {
        this.hasPlayed = hasPlayed;
    }

   /**
     * Increase the play count by one
     */
    public void incrementPlayCount() {
        playCount++;
    }

   /**
     * Reset play count
     */
    public void resetPlayCount() {
        playCount = 0;
    }

   /**
     * Reset the status of player
     */
    public void resetHasPlayed() {
        hasPlayed = false;
    }

   /**
     * Add a chess piece to the collection
     * 
     * @param piece the {@code Piece} 
     */
    public void addPiece(Piece piece) {
        pieces.add(piece);
    }

   /**
     * Remove a chess piece to the collection
     * 
     * @param piece the {@code Piece}  
     */
    public void removePiece(Piece piece) { 
        pieces.remove(piece);
    }

   /**
     * Clear the pieces
     */
    public void clearPieces() {
        pieces.clear();
    }

   /**
     * Initialize the pieces located at the specified x,y coordinates on the board.
     * 
     * @param board the {@code ChessBoard}  
     * @param offsetX x-coordinate offset  
     * @param offsetY y-coordinate offset  
     * @param opposite the orientation of the piece in the form of boolean
     */
    public void initializePieces(ChessBoard board, int offsetX, int offsetY, boolean opposite) {
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

   /**
     * Reset the player count
     */
    public static void resetPlayerCount() {
        playerCount = 0;
    }
}
