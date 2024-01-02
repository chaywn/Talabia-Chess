// Coding Member: Chay Wen Ning

package Chess;

import ChessPiece.Piece;
import Player.Player;

import java.awt.Image;
import java.util.Arrays;

public class ChessController {
    Chess chessModel;
    ChessView chessView;

    public ChessController(Chess chessModel, ChessView chessView) {
        this.chessModel = chessModel;
        this.chessView = chessView;
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

    public Image getSelectedPieceImage(int relX, int relY) {
        // Convert relative coordinate to board coordinate
        int x = relX / chessView.getGridSize();
        int y = relY / chessView.getGridSize();

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
        chessView.updatePlayerTurnLabel(chessModel.getPlayerTurn());
        chessView.updatePlayerStatusLabel(chessModel.getPlayer(chessModel.getPlayerTurn()).hasPlayed());
        chessView.updatePieceIcons(chessModel.getBoard());
        chessView.highlightLastMovedPiece(chessModel.getBoard(), chessModel.getLastMovedPiece());
        chessView.setSwitchButtonEnabled(false);

        if (chessModel.checkPlayCountAndSwitch()) {       
            chessView.notifyPieceSwitch();
            chessView.updatePieceIcons(chessModel.getBoard());
        }
    }

    public boolean checkPiecePlayability(int[] source, int[] destination) {
        int[] sourceCopy = Arrays.copyOf(source, source.length);
        int[] destinationCopy = Arrays.copyOf(destination, destination.length);

        // Convert relative coordinate to board coordinate
        for (int i = 0; i < source.length; i++) {
            sourceCopy[i] = sourceCopy[i] / chessView.getGridSize();
            destinationCopy[i] = destinationCopy[i] / chessView.getGridSize();
        }

        return chessModel.checkPieceMove(sourceCopy, destinationCopy);
    }

    // Get the piece's coordinate and send it to the chess model to check/play
    public void relayPiecePositionToPlay(int[][] sourceDestination) {
        int[] source = sourceDestination[0];
        int[] destination = sourceDestination[1];
    
        if (checkPiecePlayability(source, destination)) {
            chessModel.playPieceMove(destination[0] / chessView.getGridSize(), destination[1] / chessView.getGridSize());

            chessView.updatePieceIcons(chessModel.getBoard());
            chessView.updatePlayerStatusLabel(true);
            chessView.setSwitchButtonEnabled(true);

            Player winner = chessModel.checkWinner();

            if (winner != null && chessView.promptNewGame(winner.getIndex())) {
                newGame();
            }
        }
    }

    public void newGame() {
        setModel(new Chess());
        chessView.updatePieceIcons(chessModel.getBoard());
        chessView.addPieceIconResizer(chessModel.getBoard());
        chessView.updatePlayerTurnLabel(chessModel.getPlayerTurn());
        chessView.updatePlayerStatusLabel(false);
        chessView.highlightLastMovedPiece(chessModel.getBoard(), null);
    }
}
