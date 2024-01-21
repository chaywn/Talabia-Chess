package player;

import chesspiece.*;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

/**
 * The {@code Player} class; Represents a player in the chess game.
 * The {@code Player} class contains a {@code static int playerCount} that stores the number of existing {@code Player} object.
 * Every time a {@code Player} object is constructed, the player count increments by one, and is automatically assigned to the index of the {@code Player} object.
 * Based on the index of the player, each {@code Player} object is assigned to a {@code Color}, which is default {@code Color.YELLOW} for the first player (of index 0), 
 * and {@code Color.BLUE} for the second player (of index 1).
 * Each {@code Player} object also contains a {@code Set<Piece>}, in which {@code Piece} objects are initialized by calling {@code initializePieces}.
 * 
 * @author Chay Wen Ning
 * @author Melody Koh Si Jie
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
     * Constructs a new {@code Player} object; The index of which is based on the number of existing players.
     * Based on the index, assign a {@code Color} to the {@code Player}, which is default {@code Color.YELLOW} for the first {@code Player} (of index 0), 
     * and {@code Color.BLUE} for the second {@code Player} (of index 1).
     * 
     * @author Chay Wen Ning
     */
    public Player() {
        this.index = playerCount++;
        this.color = colors[index];
        playCount = 0;
        hasPlayed = false;
    }

    /**
     *
     * Returns the {@code Color} object of the {@code Player}. 
     * The color returned is default {@code Color.YELLOW} for the first {@code Player} (of index 0), 
     * and {@code Color.BLUE} for the second {@code Player} (of index 1).
     * 
     * @return the {@code Color} object of the {@code Player}, {@code Color.YELLOW} for {@code Player} of index 0, 
     * {@code Color.BLUE} for {@code Player} of index 1
     * @author Chay Wen Ning
     */
    public Color getColor() {
        return color;
    }

    /**
     *
     * Returns the index of the {@code Player}.
     * 
     * @return the index of the {@code Player}
     * @author Chay Wen Ning
     */
    public int getIndex() {
        return index;
    }

    /**
     *
     * Returns the {@code Set<Piece>} of the {@code Player}.
     * 
     * @return the {@code Set<Piece>} of the {@code Player}
     * @author Chay Wen Ning
     */
    public Set<Piece> getPieces() {
        return pieces;
    }

   /**
     *
     * Sets the {@code Color} of the {@code Player}.
     * 
     * @param color the {@code Color} to set the {@code Player} to
     * @author Chay Wen Ning
     */
    public void setColor(Color color) {
        this.color = color;
    }

   /**
     *
     * Returns the play count of the {@code Player}.
     * 
     * @return the play count of the {@code Player}
     * @see #setPlayCount(int)
     * @see #incrementPlayCount()
     * @see #resetPlayCount()
     * @author Chay Wen Ning
     */
    public int getPlayCount() {
        return playCount;
    }

   /**
     *
     * Sets the play count of the {@code Player}.
     * 
     * @param playCount the play count to set the {@code Player} to
     * @author Chay Wen Ning
     */
    public void setPlayCount(int playCount) {
        this.playCount = playCount;
    }

   /**
     *
     * Sets the index of the {@code Player}
     * 
     * @param index the {@code Player} index to set
     * @author Chay Wen Ning
     */
    public void setIndex(int index) {
        this.index = index;
    }

   /**
     *
     * Returns the {@code hasPlayed} status of the {@code Player}.
     * Returns {@code true} if the {@code Player} has played a move.
     * 
     * @return {@code true} if the {@code Player} has played a move
     * @see #setHasPlayed(boolean)
     * @author Chay Wen Ning
     */
    public boolean hasPlayed() {
        return hasPlayed;
    }

   /**
     *
     * Sets the {@code hasPlayed} status of the {@code Player}.
     * 
     * @param hasPlayed the {@code hasPlayed} status of the {@code Player} to set
     * @see #hasPlayed()
     * @author Chay Wen Ning
     */
    public void setHasPlayed(boolean hasPlayed) {
        this.hasPlayed = hasPlayed;
    }

   /**
     * Increments the play count of {@code Player} by one.
     * 
     * @see #setPlayCount(int)
     * @see #resetPlayCount()
     * @author Melody Koh Si Jie
     */
    public void incrementPlayCount() {
        playCount++;
    }

   /**
     * Resets the play count of {@code Player} to 0.
     * 
     * @see #setPlayCount(int)
     * @author Melody Koh Si Jie
     */
    public void resetPlayCount() {
        playCount = 0;
    }

   /**
     * Resets the {@code hasPlayed} status of the {@code Player}.
     * 
     * @see #setHasPlayed(boolean)
     * @author Chay Wen Ning
     */
    public void resetHasPlayed() {
        hasPlayed = false;
    }

   /**
     * Adds a {@code Piece} object to the {@code Set<Piece>} of the {@code Player}.
     * 
     * @param piece the {@code Piece} object to be added
     * @author Chay Wen Ning
     */
    public void addPiece(Piece piece) {
        pieces.add(piece);
    }

   /**
     * Removes a {@code Piece} object to the {@code Set<Piece>} of the {@code Player}.
     * 
     * @param piece the {@code Piece} object to be removed
     * @see #clearPieces()
     * @author Chay Wen Ning
     */
    public void removePiece(Piece piece) { 
        pieces.remove(piece);
    }

   /**
     * Clears all {@code Piece} objects in the {@code Set<Piece>} of the {@code Player}.
     * 
     * @see #removePiece(Piece)
     * @author Chay Wen Ning
     */
    public void clearPieces() {
        pieces.clear();
    }

   /**
     * Initializes the starting {@code Piece} objects for the {@code Player},
     * consisting of 7 {@code Point} pieces, 2 {@code Plus} pieces, 2 {@code Hourglass} pieces, 2 {@code Time} pieces, and 1 {@code Sun} piece.
     * 
     * @param offsetX x-coordinate offset  
     * @param offsetY y-coordinate offset  
     * @param opposite the orientation of the pieces, {@code true} if the pieces are flipped 180 degree
     * @author Chay Wen Ning
     */
    public void initializePieces(int offsetX, int offsetY, boolean opposite) {
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
    }

   /**
     * Resets the player count of the {@code Player}.
     * 
     * @see #setPlayCount(int)
     * @author Chay Wen Ning
     */
    public static void resetPlayerCount() {
        playerCount = 0;
    }
}
