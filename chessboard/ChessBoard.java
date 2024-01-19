/**
* @author Chay Wen Ning
* @author Melody Koh
* @author Goh Shi Yi
*/

package chessboard;

import player.Player;
import chesspiece.Piece;

import java.util.Set;

/**
 * The {@code ChessBoard} class
 */
public class ChessBoard {
    private final int noOfRow = 6;
    private final int noOfColumn = 7;

    private Piece[][] pieces = new Piece[noOfRow][noOfColumn];

    /**
     * Constructs a new {@code ChessBoard}.
     * 
     */
    public ChessBoard() {
    }

    /**
     * Get the number of rows.
     * 
     * @return the number of rows
     */
    public int getNoOfRow() {
        return noOfRow;
    }

    /**
     * Get the number of columns.
     * 
     * @return the number of columns
     */
    public int getNoOfColumn() {
        return noOfColumn;
    }

    /**
     * Get the specified piece located at the specified x,y coordinate on the board.
     * 
     * @param x x coordinate 
     * @param y y coordinate
     * @return the pieces located at the specified x,y coordinates
     */
    public Piece getPieceAt(int x, int y) {
        if (x < 0 || x >= noOfColumn || y < 0 || y >= noOfRow)
            return null;

        return pieces[y][x];
    }

    /**
     * Set the specified piece on the board at the specified x, y coordinate.
     * 
     * @param piece the {@code Piece}  
     * @param x     x coordinate  
     * @param y     y coordinate 
     */
    public void setPieceAt(Piece piece, int x, int y) {
        pieces[piece.getY()][piece.getX()] = null;
        pieces[y][x] = piece;
        piece.setPosition(x, y);
    }

    /**
     * Remove the specified piece from the board.
     * 
     * @param piece  the {@code Piece} 
     * @param player the {@code Player}     
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
     * Clear the pieces from the board.
     */
    public void clearPieces() {
        pieces = new Piece[noOfRow][noOfColumn];
    }

    /**
     * Set the specified player's piece set on the board according to the pieces' x,
     * y coordinate.
     * 
     * @param player the {@code Player} 
     */
    public void setPlayerPiece(Player player) {
        Set<Piece> playerPieces = player.getPieces();
        for (Piece p : playerPieces) {
            setPieceAt(p, p.getX(), p.getY());
        }
    }

    /**
     * Flip the board to the opposite point of view.
     */
    public void flip() {
        for (int c = 0; c < noOfColumn; c++) {
            for (int r = 0; r < noOfRow / 2; r++) {
                Piece temp = this.pieces[r][c];
                this.pieces[r][c] = this.pieces[noOfRow - 1 - r][noOfColumn - 1 - c];
                this.pieces[noOfRow - 1 - r][noOfColumn - 1 - c] = temp;

                if (temp != null) {
                    temp.setFlipped(!temp.isFlipped());
                    temp.setPosition(noOfColumn - 1 - c, noOfRow - 1 - r);
                }
                if (this.pieces[r][c] != null) {
                    this.pieces[r][c].setFlipped(!this.pieces[r][c].isFlipped());
                    this.pieces[r][c].setPosition(noOfColumn - 1 - c, noOfRow - 1 - r);
                }
            }
        }
    }
}
