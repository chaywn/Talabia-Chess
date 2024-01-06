// Coding Member: Chay Wen Ning

package Chess;

import ChessPiece.Piece;
import Player.Player;

import java.awt.Image;
import java.awt.Point;

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

    public boolean checkPiecePlayability(Point sourcePoint, Point destinationPoint) {
        int[] source = new int[2];
        int[] destination = new int[2];

        // Convert relative coordinate to board coordinate
        source[0] = sourcePoint.x / chessView.getGridSize();
        source[1] = sourcePoint.y / chessView.getGridSize();
        destination[0] = destinationPoint.x / chessView.getGridSize();
        destination[1] = destinationPoint.y / chessView.getGridSize();

        return chessModel.checkPieceMove(source, destination);
    }

    public boolean checkPiecePlayability(Point piecePoint) {
        int[] pos = new int[2];

        // Convert relative coordinate to board coordinate
        pos[0] = piecePoint.x / chessView.getGridSize();
        pos[1] = piecePoint.y / chessView.getGridSize();

        Piece p = chessModel.getBoard().getPieceAt(pos[0], pos[1]);

        return p != null && p.getColor() == chessModel.getPlayer(chessModel.getPlayerTurn()).getColor();
    }

    // Get the piece's coordinate and send it to the chess model to check/play
    public void relayPiecePositionToPlay(Point[] sourceDestination) {
        Point sourcePoint = sourceDestination[0];
        Point destinationPoint = sourceDestination[1];
    
        if (checkPiecePlayability(sourcePoint, destinationPoint)) {
            chessModel.playPieceMove( (destinationPoint.x / chessView.getGridSize()),  (destinationPoint.y / chessView.getGridSize()));

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
