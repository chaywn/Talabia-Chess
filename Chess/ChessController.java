// Coding Member: Chay Wen Ning, Melody Koh

package Chess;

import Board.Board;
import Player.Player;

public class ChessController {
    Chess chessModel;
    ChessView chessView;

    public ChessController(Chess chessModel, ChessView chessView) {
        this.chessModel = chessModel;
        this.chessView = chessView;

        // chessView.loadPieceImages();
    }

    public void setModel(Chess chessModel) {
        this.chessModel = chessModel;
    }

    public Board getModelBoard() {
        return chessModel.getBoard();
    }

    public Player getModelPlayer(int playerTurn) {
        return chessModel.getPlayer(playerTurn);
    }

    public int getModelPlayerTurn() {
        return chessModel.getPlayerTurn();
    }

    public void viewLoadPieceIcons() {
        chessView.loadPieceIcons(chessModel.getBoard());
    }

    public void viewUpdatePieceIcons() {
        chessView.updatePieceIcons(chessModel.getBoard());
    }
    
    public void modelSwitchTurn() {
        chessModel.switchTurn();
        chessView.updatePlayerTurnLabel(chessModel.getPlayerTurn());
        chessView.updatePieceIcons(chessModel.getBoard());
    }

    public void modelSwitchTimePlusPiece() {
        chessModel.switchTimePlusPiece();
        chessView.updatePieceIcons(chessModel.getBoard());
    }
}
