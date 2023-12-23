// Coding Member: Chay Wen Ning

package Chess;

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
}
