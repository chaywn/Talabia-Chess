/**
*
* @author Chay Wen Ning
* @author Melody Koh
* @author Goh Shi Yi
*/
package chess;

import observer.Event;
import observer.Observer;

import java.awt.Image;
import java.awt.Point;
import java.io.File;


 /**
 * The class ChessController implements Observer
 */ 
public class ChessController implements Observer{
    Chess chessModel;
    ChessView chessView;


/** 
 *
 * Chess controller
 *
 * @param chessModel  the chess model. 
 * @param chessView  the chess view. 
 * @return public
 */
    public ChessController(Chess chessModel, ChessView chessView) { 

        this.chessModel = chessModel;
        this.chessView = chessView;

        chessModel.addObserver(this);
        chessView.addObserver(this);
    }


/** 
 *
 * Sets the model
 *
 * @param chessModel  the chess model. 
 */
    public void setModel(Chess chessModel) { 

        this.chessModel = chessModel;
    }


/** 
 *
 * View load piece icons
 *
 */
    public void viewLoadPieceIcons() { 

        chessView.loadPieceIcons(chessModel.getBoard());
    }


/** 
 *
 * View add piece icon resizer
 *
 */
    public void viewAddPieceIconResizer() { 

        chessView.addPieceIconResizer(chessModel.getBoard());
    }
    

/** 
 *
 * View update player turn
 *
 */
    public void viewUpdatePlayerTurn() { 

        chessView.updatePlayerTurnLabel(chessModel.getPlayerTurn());
    }


/** 
 *
 * View update player status
 *
 */
    public void viewUpdatePlayerStatus() { 

        chessView.updatePlayerStatusLabel(chessModel.getPlayer(chessModel.getPlayerTurn()).hasPlayed());
    }


/** 
 *
 * Current player has played
 *
 * @return boolean
 */
    public boolean currentPlayerHasPlayed() { 

        return chessModel.getPlayer(chessModel.getPlayerTurn()).hasPlayed();
    }


/** 
 *
 * Gets the selected piece image
 *
 * @return the selected piece image
 */
    public Image getSelectedPieceImage() { 

        return chessView.getPieceImage(chessModel.getSelectedPiece());
    }   


/** 
 *
 * Switch player turn
 *
 */
    public void switchPlayerTurn() { 

        chessModel.switchTurn();

        viewUpdatePlayerTurn();
        viewUpdatePlayerStatus();
        chessView.updatePieceIcons(chessModel.getBoard());
        chessView.highlightLastMovedPiece(chessModel.getBoard(), chessModel.getLastMovedPiece());
        chessView.setSwitchButtonEnabled(false);

        chessModel.checkPlayCountToSwitch();
    }


/** 
 *
 * Check piece move
 *
 * @param source  the source. 
 * @param destination  the destination. 
 * @return boolean
 */
    public boolean checkPieceMove(Point source, Point destination) { 

        // Convert coordinate to board coordinate
        Point boardSource = new Point(source.x / chessView.getGridSize(), source.y / chessView.getGridSize());
        Point boardDestination = new Point(destination.x / chessView.getGridSize(), destination.y / chessView.getGridSize());

        return chessModel.checkPieceMove(boardSource, boardDestination);
    }


/** 
 *
 * Check piece playability
 *
 * @param piecePoint  the piece point. 
 * @return boolean
 */
    public boolean checkPiecePlayability(Point piecePoint) { 

        // Convert coordinate to board coordinate
        int boardX = piecePoint.x / chessView.getGridSize();
        int boardY = piecePoint.y / chessView.getGridSize();

        return chessModel.checkPiecePlayability(boardX, boardY);
    }

    // Get the piece's coordinate and send it to the chess model to check and play

/** 
 *
 * Relay piece point to play
 *
 * @param source  the source. 
 * @param destination  the destination. 
 */
    public void relayPiecePointToPlay(Point source, Point destination) { 

        Point boardSource = new Point(source.x / chessView.getGridSize(), source.y / chessView.getGridSize());
        Point boardDestination = new Point(destination.x / chessView.getGridSize(), destination.y / chessView.getGridSize());

        chessModel.playPieceMove(boardSource, boardDestination);
    }


/** 
 *
 * New game
 *
 */
    public void newGame() { 

        setModel(new Chess());
        chessModel.addObserver(this);

        chessView.updatePieceIcons(chessModel.getBoard());
        chessView.addPieceIconResizer(chessModel.getBoard());
        chessView.updatePlayerTurnLabel(chessModel.getPlayerTurn());
        chessView.updatePlayerStatusLabel(false);
        chessView.highlightLastMovedPiece(chessModel.getBoard(), null);
    }


/** 
 *
 * Calls saveGame() from chess model and notifies the result to chess view
 *
 * @param file  the file to save game data to
 */
    public void saveGameData(File file) { 

        chessView.notifySave(chessModel.saveGame(file));
     }


/** 
 * Calls loadGame() from chess model and notifies the result to chess view
 *
 * @param file  the file to load game data from
 */
    public void loadGameData(File file) { 

        // chessView.notifyLoad(chessModel.loadGame(file));
    }

    @Override

/** 
 *
 * Handles event notified by the ChessController's subjects
 *
 * @param event  the event to be handled 
 * @see observer.Event
 */
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
