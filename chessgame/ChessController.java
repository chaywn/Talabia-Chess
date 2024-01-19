/**
*
* @author Chay Wen Ning
* @author Melody Koh
* @author Goh Shi Yi
* @author Choo Yun Yi
*/
package chessgame;

import observer.Event;
import observer.Observer;

import java.awt.Image;
import java.awt.Point;
import java.io.File;

/**
 * The {@code ChessController} class ; Implements {@code Observer} class.
 */
public class ChessController implements Observer {
    ChessGame chessModel;
    ChessView chessView;

    /**
     *
     * Constructs a new {@code ChessController}.
     *
     * @param chessModel the {@code ChessGame} 
     * @param chessView  the {@code ChessView} 
     */
    public ChessController(ChessGame chessModel, ChessView chessView) {

        this.chessModel = chessModel;
        this.chessView = chessView;

        chessModel.addObserver(this);
        chessView.addObserver(this);
    }

    /**
     *
     * Set the chess model.
     *
     * @param chessModel the {@code ChessGame} 
     */
    public void setModel(ChessGame chessModel) {

        this.chessModel = chessModel;
    }

    /**
     *
     * Calls the chess view to load piece icons.
     *
     */
    public void viewLoadPieceIcons() {

        chessView.loadPieceIcons(chessModel.getBoard());
    }

    /**
     *
     * Calls the chess view to add piece icon resizer.
     *
     */
    public void viewAddPieceIconResizer() {

        chessView.addPieceIconResizer(chessModel.getBoard());
    }

    /**
     *
     * Calls the chess view to display the current player turn.
     *
     */
    public void displayCurrentPlayerTurn() {

        chessView.displayPlayerTurn(chessModel.getPlayerTurn());
    }

    /**
     *
     * Calls the chess view to display the current player's {@code hasPlayed} status.
     *
     */
    public void displayCurrentPlayerStatus() {

        chessView.displayPlayerStatus(chessModel.getPlayer(chessModel.getPlayerTurn()).hasPlayed());
    }

    /**
     *
     * Return the current player's {@code hasPlayed} status.
     *
     * @return {@code true} if current player has played a piece move
     */
    public boolean currentPlayerHasPlayed() {
        return chessModel.getPlayer(chessModel.getPlayerTurn()).hasPlayed();
    }

    /**
     *
     * Get and return the selected piece image from the chess view.
     *
     * @return the selected piece image
     */
    public Image getSelectedPieceImage() {

        return chessView.getPieceImage(chessModel.getSelectedPiece());
    }

    /**
     *
     * Switch and update player turn. Update piece icons and highlight the piece last moved by
     * the player. Disable the switch turn button.
     *
     */
    public void switchTurnAndUpdateContainer() {

        chessModel.switchTurnAndFlipBoard();

        displayCurrentPlayerTurn();
        displayCurrentPlayerStatus();
        chessView.updatePieceIcons(chessModel.getBoard());
        chessView.highlightLastMovedPiece(chessModel.getBoard(), chessModel.getLastMovedPiece());
        chessView.updateSwitchButton(false);

        chessModel.switchPiecesIfPlayCountReached();
    }

    /**
     *
     * Convert coordinates from the Container to the board coordinates and use the coordinates to 
     * check piece move.
     *
     * @param source      the source {@code Point} 
     * @param destination the destination {@code Point} 
     * @return {@code true} if the piece is playable
     */
    public boolean convertCoordinateAndCheckPieceMove(Point source, Point destination) {

        // Convert coordinate to board coordinate
        Point boardSource = new Point(source.x / chessView.getGridSize(), source.y / chessView.getGridSize());
        Point boardDestination = new Point(destination.x / chessView.getGridSize(),
                destination.y / chessView.getGridSize());

        return chessModel.checkPieceMove(boardSource, boardDestination);
    }

    /**
     *
     * Convert coordinates from the Container to the board coordinates and use the coordinates to 
     * check piece playability.
     *
     * @param piecePoint the piece point ({@code java.awt.Point})
     * @return {@code true} if the piece is playable
     */
    public boolean ConvertCoordinateAndCheckPiecePlayability(java.awt.Point piecePoint) {

        // Convert coordinate to board coordinate
        int boardX = piecePoint.x / chessView.getGridSize();
        int boardY = piecePoint.y / chessView.getGridSize();

        return chessModel.checkPiecePlayability(boardX, boardY);
    }

    /**
     *
     * Relay piece point to play.
     *
     * @param source      the source {@code Point} 
     * @param destination the destination {@code Point} 
     */
    public void ConvertCoordinateAndAttemptPlay(Point source, Point destination) {
        Point boardSource = new Point(source.x / chessView.getGridSize(), source.y / chessView.getGridSize());
        Point boardDestination = new Point(destination.x / chessView.getGridSize(),
                destination.y / chessView.getGridSize());

        chessModel.playPieceMove(boardSource, boardDestination);
    }

    /**
     *
     * New game.
     *
     */
    public void newGame() {

        setModel(new ChessGame());
        chessModel.addObserver(this);

        chessView.updatePieceIcons(chessModel.getBoard());
        chessView.addPieceIconResizer(chessModel.getBoard());
        chessView.displayPlayerTurn(chessModel.getPlayerTurn());
        chessView.displayPlayerStatus(false);
        chessView.highlightLastMovedPiece(chessModel.getBoard(), null);
    }

    /**
     *
     * Calls saveGame() from chess model and notifies the result to chess view.
     *
     * @param file the file to save game data to
     */
    public void saveGameData(File file) {

        chessView.displaySaveResult(chessModel.writeGameDataToFile(file));
    }

    /**
     * Calls loadGame() from chess model and notifies the result to chess view.
     *
     * @param file the file to load game data from
     * @return {code true} if the game load successfully
     */
    public boolean loadGameData(File file) {
        boolean result = chessModel.loadGameDataFromFile(file);
        chessView.displayLoadResult(result);

        if (result) {
            chessView.updatePieceIcons(chessModel.getBoard());
            chessView.highlightLastMovedPiece(chessModel.getBoard(), chessModel.getLastMovedPiece());
            displayCurrentPlayerTurn();
            displayCurrentPlayerStatus();
            chessView.updateSwitchButton(chessModel.getHasPlayed());

            return true;
        }
        return false;
    }

    @Override
    public void handleEvent(Event event) {
        switch (event) {
            case PieceMove: {
                handlePieceMoveEvent();
                break;
            }
            case PieceSwitch: {
                handlePieceSwitchEvent();
                break;
            }
            case PlayerWin: {
                int winnerIndex = (int) Event.PlayerWin.getArgument();
                handlePlayerWinEvent(winnerIndex);
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

    /**
     *
     * 
     * When {@code Event.PieceSwitch} is called, calls the chess view to notify the piece switch event and update the piece icons.
     *
     */
    public void handlePieceSwitchEvent() {
        chessView.notifyPieceSwitch();
        chessView.updatePieceIcons(chessModel.getBoard());
    }

    /**
     *
     * When {@code Event.PieceMove} event is called, calls the chess view to notify the piece move event and update the piece icons.
     *
     */
    public void handlePieceMoveEvent() {
        chessView.updatePieceIcons(chessModel.getBoard());
        chessView.displayPlayerStatus(true);
        chessView.updateSwitchButton(true);
    }

    /**
     *
     * When {@code Event.PlayerWin} event is called, calls the chess view to notify the player win event and ask
     * if the player wants to start a new game and show the winner.
     *
     */
    public void handlePlayerWinEvent(int winnerIndex) {
        chessView.promptNewGameConfirmation(winnerIndex);
    }
}
