// Coding Member: Chay Wen Ning

package chess;
import java.util.HashSet;

import board.Board;
import chesspiece.Piece;
import chesspiece.Piece.PieceType;
import player.Player;

public class Chess {
    private final int switchCounter = 4;
    private Player[] players = new Player[2];
    private Board board;
    private int playerTurn;

    private Piece selectedPiece;
    private Piece lastMovedPiece;

    public Chess() {
        Player.resetPlayerCount();
        board = new Board();

        int offSetY = 4;
        boolean opposite = false;
        for (int i = 0; i < 2; i++) {
            players[i] = new Player();
            players[i].initializePieces(board, 0, offSetY, opposite);
            board.setPlayerPiece(players[i]);
            offSetY = 0;
            opposite = true;
        }

        playerTurn = 0; 
    }

    // Getters
    public Player getPlayer(int index) { return players[index]; }
    public Board getBoard() { return board; }
    public int getPlayerTurn() { return playerTurn; }
    public Piece getLastMovedPiece() { return lastMovedPiece; }

    public int totalPlayCount() {
        int totalPlayCount = 0;
        for (Player p: players) {
            totalPlayCount += p.getPlayCount();
        }
        return totalPlayCount;
    }
    
    public void switchTurn() {
        players[playerTurn].incrementPlayCount();
        players[playerTurn].resetHasPlayed();
        playerTurn = playerTurn == 1 ? 0 : 1;
        
        board.flip();
    }

    public void flipBoard() {
        board.flip();
    }

    public boolean checkPlayCountAndSwitch() {
        if (totalPlayCount() == switchCounter) {
            switchTimePlusPiece();
            for (Player p: players) {
                p.resetPlayCount();
            }
            return true;
        }
        return false;
    }

    public boolean checkPieceMove(int[] source, int[] destination) {
        selectedPiece = board.getPieceAt(source[0], source[1]);

        if (selectedPiece != null && !players[playerTurn].hasPlayed() && selectedPiece.getColor() == players[playerTurn].getColor() && (selectedPiece.getX() != destination[0] || selectedPiece.getY() != destination[1])) {
            return selectedPiece.isMovableTo(board, selectedPiece, destination[0], destination[1]);
        }

        return false;
    }

    public void playPieceMove(int x, int y) {
        Piece killedPiece = board.getPieceAt(x, y);

        if (killedPiece != null) {         
            int opponentIndex = playerTurn == 1 ? 0 : 1;
            board.removePiece(killedPiece, players[opponentIndex]);
        }
        
        board.setPieceAt(selectedPiece, x, y);
        // if the piece is a Point piece and it reached the end, flip it
        if (selectedPiece.getPieceType() == PieceType.Point && (y == 0 || y == board.getNoOfRow() - 1)) {
            selectedPiece.setFlipped(!selectedPiece.isFlipped());            
        }

        players[playerTurn].setHasPlayed(true);
        lastMovedPiece = selectedPiece;
        selectedPiece = null;
    }

    public Player checkWinner() {
        for (Player p: players) {
            HashSet<Piece> pieces = p.getPieces();
            boolean hasSun = false;
            for (Piece pc: pieces) {
                if (pc.getPieceType() == PieceType.Sun) {
                    hasSun = true;
                    break;
                }
            }
            if (!hasSun) 
                return players[p.getIndex() == 1 ? 0 : 1];
        }

        return null;
    }

    // switch Time piece and Plus piece, and vice versa
    public void switchTimePlusPiece() {
        for (Player pl: players) {
            HashSet<Piece> piecesToAdd = new HashSet<>();
            HashSet<Piece> piecesToRemove = new HashSet<>();
            HashSet<Piece> playerPieces = pl.getPieces();
            for (Piece pc: playerPieces) {
                if (pc.getPieceType() == Piece.PieceType.Time) {
                    piecesToAdd.add(pc.toPlus());
                    piecesToRemove.add(pc);

                } else if (pc.getPieceType() == Piece.PieceType.Plus) {
                    piecesToAdd.add(pc.toTime());
                    piecesToRemove.add(pc);
                }
            }
            for (Piece pc: piecesToRemove) {
                pl.removePiece(pc);
            }
            for (Piece pc: piecesToAdd) {
                pl.addPiece(pc);
                board.setPieceAt(pc, pc.getX(), pc.getY());
            }
        }
    }
}
