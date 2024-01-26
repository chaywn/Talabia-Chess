package chessboard;

import player.Player;
import chesspiece.Piece;

import java.util.Set;

/**
 * The {@code ChessBoard} class; Represented by a 2d-array {@code Piece[][]}, the dimension of which is defined by its constants {@code NO_OF_ROW} and {@code NO_OF_COLUMN}.
 * This array is empty by default, call the {@code setPieceAt} and {@code setPlayerPiece} methods to add {@code Piece} object(s) into the array.
 * The {@code ChessBoard} class stores each {@code Piece} object in the array (perceived as a chess board) for piece tracking using coordinates.
 * When being used in a {@code ChessGame}, it has the ability to flip 180 degree by repositioning all {@code Piece} objects in the array to correspond with the opposite player's point of view.
 * 
 * @see chesspiece.Piece
 * @see chessgame.ChessGame
 * @author Chay Wen Ning
 */
public class ChessBoard {
    // Dimension of the chess board
    private final int NO_OF_ROW = 6;
    private final int NO_OF_COLUMN = 7;

    // 2d-array of pieces; Represents the dimension of the chess board
    private Piece[][] pieces = new Piece[NO_OF_ROW][NO_OF_COLUMN];

    /**
     * Constructs a new empty {@code ChessBoard} object.
     * @author Chay Wen Ning
     */
    public ChessBoard() {
    }

    /**
     * Returns the number of rows of the chess board.
     * 
     * @return the number of rows of the chess board
     * @author Chay Wen Ning
     */
    public int getNoOfRow() {
        return NO_OF_ROW;
    }

    /**
     * Returns the number of columns of the chess board.
     * 
     * @return the number of columns of the chess board
     * @author Chay Wen Ning
     */
    public int getNoOfColumn() {
        return NO_OF_COLUMN;
    }

    /**
     * Returns the {@code Piece} object located at the specified x, y coordinate on the chess board.
     * 
     * @param x the x coordinate
     * @param y the y coordinate
     * @return  the {@code Piece} object located at the specified x, y coordinates on the board
     * @author Chay Wen Ning
     * @author Goh Shi Yi
     */
    public Piece getPieceAt(int x, int y) {
        if (x < 0 || x >= NO_OF_COLUMN || y < 0 || y >= NO_OF_ROW)
            return null;

        return pieces[y][x];
    }

    /**
     * Sets the specified {@code Piece} object on the board at the specified x, y coordinate.
     * 
     * @param piece the {@code Piece} object to be set
     * @param x     the x coordinate
     * @param y     the y coordinate
     * @see #setPlayerPiece
     * @author Chay Wen Ning
     * @author Goh Shi Yi
     */
    public void setPieceAt(Piece piece, int x, int y) {
        pieces[piece.getY()][piece.getX()] = null;
        pieces[y][x] = piece;
        piece.setPosition(x, y);
    }

    /**
     * Removes the specified {@code Piece} object from the chess board. Also removes from the specified player's {@code Set<Piece>}.
     * 
     * @param piece  the {@code Piece} object to be removed
     * @param player the {@code Player} object 
     * @author Chay Wen Ning  
     * @author Goh Shi Yi
     */

        public void removePiece(Piece piece, Player player) {
        if (pieces[piece.getY()][piece.getX()] == null) {
            return;
        } else {
            player.removePiece(piece);
            pieces[piece.getY()][piece.getX()] = null;
        }
    }

    /**
     * Clears all pieces from the chess board. This method creates a new {@code Piece[][]} to replace the old 2d-array.
     * @author Chay Wen Ning
     */
    public void clearPieces() {
        pieces = new Piece[NO_OF_ROW][NO_OF_COLUMN];
    }

    /**
     * 
     * Sets all {@code Piece} objects in the specified player's {@code Set<Piece>} on the board at their x, y coordinate. 
     * 
     * @param player the {@code Player} object
     * @see #setPieceAt
     * @author Chay Wen Ning
     */
    public void setPlayerPiece(Player player) {
        Set<Piece> playerPieces = player.getPieces();
        for (Piece p : playerPieces) {
            setPieceAt(p, p.getX(), p.getY());
        }
    }

    /**
     * Flips the board to the opposite point of view.
     * 
     * @see chesspiece.Piece#isFlipped()
     * @see chesspiece.Piece#setFlipped(boolean)
     * @see chesspiece.Piece#setPosition(int, int)
     * @author Chay Wen Ning
     */
    public void flip() {
        for (int c = 0; c < NO_OF_COLUMN; c++) {
            for (int r = 0; r < NO_OF_ROW / 2; r++) {
                Piece temp = this.pieces[r][c];
                this.pieces[r][c] = this.pieces[NO_OF_ROW - 1 - r][NO_OF_COLUMN - 1 - c];
                this.pieces[NO_OF_ROW - 1 - r][NO_OF_COLUMN - 1 - c] = temp;

                if (temp != null) {
                    temp.setFlipped(!temp.isFlipped());
                    temp.setPosition(NO_OF_COLUMN - 1 - c, NO_OF_ROW - 1 - r);
                }
                if (this.pieces[r][c] != null) {
                    this.pieces[r][c].setFlipped(!this.pieces[r][c].isFlipped());
                    this.pieces[r][c].setPosition(NO_OF_COLUMN - 1 - c, NO_OF_ROW - 1 - r);
                }
            }
        }
    }
}
