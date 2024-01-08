// Coding Member: Chay Wen Ning, Goh Shi Yi

package chess;

import board.Board;
import chesspiece.Piece;
import chesspiece.Piece.PieceType;
import observer.Event;
import observer.Observer;
import observer.Subject;
import player.Player;

import java.awt.Point;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Chess implements Subject {
    private final int switchCounter = 4;
    private Player[] players = new Player[2];
    private Board board;
    private int playerTurn;

    private Piece selectedPiece;
    private Piece lastMovedPiece;

    private Set<Observer> observerList = new HashSet<>();

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
    public Player getPlayer(int index) {
        return players[index];
    }

    public Board getBoard() {
        return board;
    }

    public int getPlayerTurn() {
        return playerTurn;
    }

    public Piece getSelectedPiece() {
        return selectedPiece;
    }

    public Piece getLastMovedPiece() {
        return lastMovedPiece;
    }

    @Override
    public void addObserver(Observer o) {
        observerList.add(o);
    }

    @Override
    public void notifyObservers(Event event) {
        for (Observer o : observerList) {
            o.handleEvent(event);
        }
    }

    public int totalPlayCount() {
        int totalPlayCount = 0;
        for (Player p : players) {
            totalPlayCount += p.getPlayCount();
        }
        return totalPlayCount;
    }

    public void switchTurn() {
        playerTurn = playerTurn == 1 ? 0 : 1;
        players[playerTurn].resetHasPlayed();
        board.flip();
    }

    public void checkPlayCountToSwitch() {
        if (totalPlayCount() == switchCounter) {
            switchTimePlusPiece();
            for (Player p : players) {
                p.resetPlayCount();
            }
            notifyObservers(Event.PieceSwitch);
        }
    }

    public boolean checkPiecePlayability(int x, int y) {
        selectedPiece = board.getPieceAt(x, y);

        return selectedPiece != null && !players[playerTurn].hasPlayed()
                && selectedPiece.getColor() == players[playerTurn].getColor();
    }

    public boolean checkPieceMove(Point source, Point destination) {
        if (checkPiecePlayability(source.x, source.y)
                && (selectedPiece.getX() != destination.x || selectedPiece.getY() != destination.y)) {
            return selectedPiece.isMovableTo(board, selectedPiece, destination.x, destination.y);
        }

        return false;
    }

    public void playPieceMove(Point source, Point destination) {
        if (!checkPieceMove(source, destination))
            return;

        Piece killedPiece = board.getPieceAt(destination.x, destination.y);

        if (killedPiece != null) {
            int opponentIndex = playerTurn == 1 ? 0 : 1;
            board.removePiece(killedPiece, players[opponentIndex]);
        }

        board.setPieceAt(selectedPiece, destination.x, destination.y);

        // if the piece is a Point piece and it reached the end, flip it
        if (selectedPiece.getPieceType() == PieceType.Point
                && (selectedPiece.getY() == 0 || selectedPiece.getY() == board.getNoOfRow() - 1)) {
            selectedPiece.setFlipped(!selectedPiece.isFlipped());
        }

        notifyObservers(Event.PieceMove);
        endTurn();
    }

    public void endTurn() {
        Player winner = checkWinner();
        if (winner != null) {
            notifyObservers(Event.PlayerWin.returnArgument(winner.getIndex()));
        } else {
            players[playerTurn].incrementPlayCount();
            players[playerTurn].setHasPlayed(true);

            lastMovedPiece = selectedPiece;
            selectedPiece = null;
        }
    }

    public Player checkWinner() {
        // check if each player still have the Sun piece
        for (Player p : players) {
            Set<Piece> pieces = p.getPieces();
            boolean hasSun = false;

            for (Piece pc : pieces) {
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
        for (Player pl : players) {
            Set<Piece> piecesToAdd = new HashSet<>();
            Set<Piece> piecesToRemove = new HashSet<>();
            Set<Piece> playerPieces = pl.getPieces();
            for (Piece pc : playerPieces) {
                if (pc.getPieceType() == Piece.PieceType.Time) {
                    piecesToAdd.add(pc.toPlus());
                    piecesToRemove.add(pc);

                } else if (pc.getPieceType() == Piece.PieceType.Plus) {
                    piecesToAdd.add(pc.toTime());
                    piecesToRemove.add(pc);
                }
            }
            for (Piece pc : piecesToRemove) {
                pl.removePiece(pc);
            }
            for (Piece pc : piecesToAdd) {
                pl.addPiece(pc);
                board.setPieceAt(pc, pc.getX(), pc.getY());
            }
        }
    }

    public boolean saveGame(File file) {
        try (FileWriter writer = new FileWriter(file + ".txt")) {
            writer.write("Player Turn: " + getPlayerTurn() + "\n");
            writer.write("Player 1 Play Count: " + getPlayer(0).getPlayCount() + "\n");
            writer.write("Player 2 Play Count: " + getPlayer(1).getPlayCount() + "\n");
            writer.write("Last Moved Piece At(" + getLastMovedPiece().getX() + ", " + getLastMovedPiece().getY() + ")"
                    + ", Piece Type:" + getLastMovedPiece().getPieceType() + ", Piece Flipped: " + getLastMovedPiece().isFlipped() + ", Piece Color: "
                    + getLastMovedPiece().getColor() + "\n");
            for (int x = 0; x < getBoard().getNoOfColumn(); x++) {
                for (int y = 0; y < getBoard().getNoOfRow(); y++) {
                    Piece piece = getBoard().getPieceAt(x, y);
                    if (piece != null) {
                        writer.write("Piece at (" + x + ", " + y + ")" + ", Piece Type: " + piece.getPieceType()
                                + ", Piece Flipped: " + piece.isFlipped() + ", Piece Color: " + piece.getColor()  + "\n");
                    }
                }
            }
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }

}
