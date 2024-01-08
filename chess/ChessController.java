// Coding Member: Chay Wen Ning, Goh Shi Yi

package chess;

import observer.Event;
import observer.Observer;

import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;

import chesspiece.Piece;

public class ChessController implements Observer{
    Chess chessModel;
    ChessView chessView;

    public ChessController(Chess chessModel, ChessView chessView) {
        this.chessModel = chessModel;
        this.chessView = chessView;

        chessModel.addObserver(this);
        chessView.addObserver(this);
    }

    public void setModel(Chess chessModel) {
        this.chessModel = chessModel;
    }

    public void viewLoadPieceIcons() {
        chessView.loadPieceIcons(chessModel.getBoard());
    }

    public void viewAddPieceIconResizer() {
        chessView.addPieceIconResizer(chessModel.getBoard());
    }
    
    public void viewUpdatePlayerTurn() {
        chessView.updatePlayerTurnLabel(chessModel.getPlayerTurn());
    }

    public void viewUpdatePlayerStatus() {
        chessView.updatePlayerStatusLabel(chessModel.getPlayer(chessModel.getPlayerTurn()).hasPlayed());
    }


    public boolean currentPlayerHasPlayed() {
        return chessModel.getPlayer(chessModel.getPlayerTurn()).hasPlayed();
    }

    public Image getSelectedPieceImage(Point point) {
        // Convert relative coordinate to board coordinate
        int x =  point.x / chessView.getGridSize();
        int y =  point.y / chessView.getGridSize();

        Piece selectedPiece = chessModel.getBoard().getPieceAt(x, y);

        if (selectedPiece != null && selectedPiece.getColor() == chessModel.getPlayer(chessModel.getPlayerTurn()).getColor()) {
            return chessView.getPieceImage(selectedPiece);
        }
        else {
            return null;
        }       
    }   

    public void switchPlayerTurn() {
        chessModel.switchTurn();

        viewUpdatePlayerTurn();
        viewUpdatePlayerStatus();
        chessView.updatePieceIcons(chessModel.getBoard());
        chessView.highlightLastMovedPiece(chessModel.getBoard(), chessModel.getLastMovedPiece());
        chessView.setSwitchButtonEnabled(false);

        chessModel.checkPlayCountToSwitch();
    }

    public boolean checkPieceMove(Point source, Point destination) {
        // Convert coordinate to board coordinate
        Point boardSource = new Point(source.x / chessView.getGridSize(), source.y / chessView.getGridSize());
        Point boardDestination = new Point(destination.x / chessView.getGridSize(), destination.y / chessView.getGridSize());

        return chessModel.checkPieceMove(boardSource, boardDestination);
    }

    public boolean checkPiecePlayability(Point piecePoint) {
        // Convert coordinate to board coordinate
        int boardX = piecePoint.x / chessView.getGridSize();
        int boardY = piecePoint.y / chessView.getGridSize();

        return chessModel.checkPiecePlayability(boardX, boardY);
    }

    // Get the piece's coordinate and send it to the chess model to check and play
    public void relayPiecePointToPlay(Point source, Point destination) {
        Point boardSource = new Point(source.x / chessView.getGridSize(), source.y / chessView.getGridSize());
        Point boardDestination = new Point(destination.x / chessView.getGridSize(), destination.y / chessView.getGridSize());

        chessModel.playPieceMove(boardSource, boardDestination);
    }

    public void newGame() {
        setModel(new Chess());
        chessModel.addObserver(this);

        chessView.updatePieceIcons(chessModel.getBoard());
        chessView.addPieceIconResizer(chessModel.getBoard());
        chessView.updatePlayerTurnLabel(chessModel.getPlayerTurn());
        chessView.updatePlayerStatusLabel(false);
        chessView.highlightLastMovedPiece(chessModel.getBoard(), null);
    }

    public void saveGame(File file) {
        try (FileWriter writer = new FileWriter(file + ".txt")) {
            writer.write("Player Turn: " + chessModel.getPlayerTurn() + "\n");
            writer.write("Total Play Count: "+chessModel.totalPlayCount() + "\n");
            for (int x = 0; x < chessModel.getBoard().getNoOfColumn(); x++) {
                for (int y = 0; y < chessModel.getBoard().getNoOfRow(); y++) {
                    Piece piece = chessModel.getBoard().getPieceAt(x, y);
                    if (piece != null) {
                        writer.write("Piece at (" + x + ", " + y+ ")" + ", Piece Type: " + piece.getPieceType()
                                + ", Piece Color: " + piece.getColor() + "\n");
                    }
                }
            }
            JOptionPane.showMessageDialog(null, "Game saved successfully!");
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: Game saved unsuccessfully.");
        }
    }

    @Override
    public void handleEvent(Event event) {
        switch(event) {
            case PieceMove: {
                chessView.updatePieceIcons(chessModel.getBoard());
                chessView.updatePlayerStatusLabel(true);
                chessView.setSwitchButtonEnabled(true);
                break;
            }
            case PieceSwitch: {
                chessView.notifyPieceSwitch();
                chessView.updatePieceIcons(chessModel.getBoard());
                break;
            }
            case PlayerWin: {
                int winnerIndex = (int) Event.PlayerWin.getArgument();
                chessView.promptNewGame(winnerIndex);
                break;
            }
            case NewGame: {
                newGame();
                break;
            }
            default:
                break;
        }

        
    }

    
}
