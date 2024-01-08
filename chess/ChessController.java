// Coding Member: Chay Wen Ning

package chess;

import observer.Event;
import observer.Observer;

import java.awt.Image;
import java.awt.Point;


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
