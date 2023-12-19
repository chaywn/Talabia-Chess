// Coding Member: Chay Wen Ning


package Board;
import ChessPiece.Piece;
import Player.Player;

import java.util.HashSet;

public class Board {
    private final int noOfRow = 6;
    private final int noOfColumn = 7;

    private Piece[][] pieces = new Piece[noOfRow][noOfColumn];

    public Board() {}

    // getters
    public int getNoOfRow() { return noOfRow; }
    public int getNoOfColumn() { return noOfColumn; }

    public Piece getPieceAt(int x, int y) {
        if (pieces[y][x] == null) 
            return null;

        return pieces[y][x];
    }

    // Set the specified piece on the board at the specified x, y coordinate
    public void setPieceAt(Piece piece, int x, int y) {
        pieces[y][x] = piece;
    }

    // Set the specified player's piece set on the board according to the pieces' x, y coordinate
    public void setPlayerPiece(Player player) {
        HashSet<Piece> playerPieces = player.getPieces();
        for (Piece p: playerPieces) {
            setPieceAt(p, p.getX(), p.getY());
        }
    }

    // switch Time piece and Plus piece, and vice versa
    public void switchTimePlusPiece() {
        // TODO
    }

    // flip the board to see from the other side
    public void flip() {
        for (int c = 0; c < noOfColumn; c++) {
            for (int r = 0; r < noOfRow / 2; r++) {
                Piece temp = this.pieces[r][c];
                this.pieces[r][c] = this.pieces[noOfRow - 1 - r][noOfColumn - 1 - c];
                this.pieces[noOfRow - 1 - r][noOfColumn -1 - c] = temp;
                
                if (temp != null) {
                    temp.setFlipped(!temp.isFlipped());
                }
                if (this.pieces[r][c] != null) {
                    this.pieces[r][c].setFlipped(!this.pieces[r][c].isFlipped());
                }
            }
        }
    }
}
