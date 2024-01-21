package chessgame;

import observer.Event;
import observer.Observer;

import java.awt.Image;
import java.awt.Point;
import java.io.File;

/**
 * The {@code ChessController} class; Implements the {@code Observer} class.
 * This class is part of the <a href="https://www.geeksforgeeks.org/mvc-design-pattern/">MVC design pattern</a>, and it acts as a controller in the MVC relationship.
 * This controller acts as the communicator between the {@code ChessGameContainer}, {@code ChessGame}, and {@code ChessView}.
 * When constructing a {@code ChessController} object, it requires reference to the model and view, to be able to call them directly through itself.
 * <p>
 * Additionally, this class is part of the <a href="https://www.geeksforgeeks.org/observer-pattern-set-1-introduction/">Observer design pattern</a>, and it acts as an {@code Observer}.
 * When given reference to the model and view, it appends itself as an {@code Observer} to each of them, and handles any {@code Event} notified by the latter.
 * 
 * @see observer.Observer
 * @see main.ChessGameContainer
 * @see chessgame.ChessGame
 * @see chessgame.ChessView
 * @see observer.Event
 * @author Chay Wen Ning
 * @author Goh Shi Yi
 * @author Choo Yun Yi
 */
public class ChessController implements Observer {
    ChessGame chessModel;
    ChessView chessView;

    /**
     *
     * Constructs a new {@code ChessController} object that acts as a controller and observer, 
     * and register itself with a {@code ChessGame} object that acts as the model, 
     * and a {@code ChessView} object that acts as the view.
     * <p>
     * This constructor will also add the constructed controller as an {@code Observer} object to the registered model and view.
     *
     * @param chessModel the {@code ChessGame} object that acts as the model
     * @param chessView  the {@code ChessView} object that acts as the view
     * 
     * @see chessgame.ChessGame
     * @see chessgame.ChessView
     * @see observer.Observer
     * @see observer.Subject#addObserver(Observer)
     * @author Chay Wen Ning
     */
    public ChessController(ChessGame chessModel, ChessView chessView) {

        this.chessModel = chessModel;
        this.chessView = chessView;

        chessModel.addObserver(this);
        chessView.addObserver(this);
    }

    /**
     *
     * Sets a {@code ChessGame} object as the chess model.
     *
     * @param chessModel the {@code ChessGame} object that acts as the model
     * @see chessgame.ChessGame
     * @author Chay Wen Ning
     */
    public void setModel(ChessGame chessModel) {

        this.chessModel = chessModel;
    }

    /**
     *
     * Calls the chess view's {@code loadPieceIcons} method.
     * 
     * @see chessgame.ChessView#loadPieceIcons(chessboard.ChessBoard)
     * @author Chay Wen Ning
     */
    public void viewLoadPieceIcons() {

        chessView.loadPieceIcons(chessModel.getBoard());
    }

    /**
     *
     * Calls the chess view's {@code addPieceIconResizer} method.
     *
     * @see chessgame.ChessView#addPieceIconResizer(chessboard.ChessBoard)
     * @author Chay Wen Ning
     */
    public void viewAddPieceIconResizer() {

        chessView.addPieceIconResizer(chessModel.getBoard());
    }

    /**
     *
     * Calls the chess view's {@code displayPlayerTurn} method to display the current player turn.
     * This method calls the chess model's {@code getPlayerTurn} method to retrieve the current player turn.
     *
     * @see chessgame.ChessView#displayPlayerTurn(int)
     * @see chessgame.ChessGame#getPlayerTurn()
     * @author Chay Wen Ning
     */
    public void displayCurrentPlayerTurn() {

        chessView.displayPlayerTurn(chessModel.getPlayerTurn());
    }

    /**
     *
     * Calls the chess view's {@code displayPlayerStatus} method to display the current player's {@code hasPlayed} status.
     * This method calls the {@code currentPlayerHasPlayed} method to retrieve the current player's {@code hasPlayed} status.
     * 
     * @see chessgame.ChessView#displayPlayerTurn(int)
     * @see #currentPlayerHasPlayed()
     * @author Chay Wen Ning
     */
    public void displayCurrentPlayerStatus() {
        chessView.displayPlayerStatus(currentPlayerHasPlayed());
    }

    /**
     *
     * Returns the current player's {@code hasPlayed} status.
     * This method calls the chess model's {@code getPlayerTurn} method to retrieve the current player turn.
     *
     * @return {@code true} if the current player's {@code hasPlayed == true}
     * @see chessgame.ChessGame#getPlayerTurn()
     * @see player.Player#hasPlayed()
     * @author Chay Wen Ning
     */
    public boolean currentPlayerHasPlayed() {
        return chessModel.getPlayer(chessModel.getPlayerTurn()).hasPlayed();
    }

    /**
     *
     * Returns the {@code Image} of the selected chess piece.
     *
     * @return the {@code Image} of the selected chess piece.
     * @see chessgame.ChessView#getPieceImage(chesspiece.Piece)
     * @see chessgame.ChessGame#getSelectedPiece()
     * @author Chay Wen Ning
     */
    public Image getSelectedPieceImage() {

        return chessView.getPieceImage(chessModel.getSelectedPiece());
    }

    /**
     *
     * Calls the chess model to switch player turn and flip the board.
     * Then calls the chess view to update the chess game container. 
     * This method also calls the chess model's {@code switchPiecesIfPlayCountReached} method to check piece switch condition.
     *
     * @see chessgame.ChessGame#switchTurnAndFlipBoard()
     * @see chessgame.ChessGame#switchPiecesIfPlayCountReached()
     * @author Chay Wen Ning
     */
    public void switchTurnAndUpdateContainer() {

        chessModel.switchTurnAndFlipBoard();

        displayCurrentPlayerTurn();
        displayCurrentPlayerStatus();
        chessView.updatePieceIcons(chessModel.getBoard());
        chessView.highlightLastMovedPiece(chessModel.getLastMovedPiece());
        chessView.updateSwitchButton(false);

        chessModel.switchPiecesIfPlayCountReached();
    }

    /**
     *
     * Converts the source and destination {@code Point} of a chess piece from the container screen to board-relative and use them to 
     * check the piece's move. This method calls the chess model's {@code checkPieceMove} method.
     *
     * @param source      the source {@code Point} 
     * @param destination the destination {@code Point} 
     * @return {@code true} if the piece move is playable
     * @see chessgame.ChessGame#checkPieceMove(Point, Point)
     * @author Chay Wen Ning
     * @author Melody Koh Si Jie
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
     * Converts the {@code Point} of a chess piece from the container screen to board-relative and use its x, y coordinate to 
     * check the piece's playability. This method calls the chess model's {@code checkPiecePlayability} method.
     *
     * @param piecePoint the {@code Point} of a chess piece ({@code java.awt.Point})
     * @return {@code true} if the piece is playable
     * @see chessgame.ChessGame#checkPiecePlayability(int, int)
     * @author Chay Wen Ning
     * @author Melody Koh Si Jie
     */
    public boolean convertCoordinateAndCheckPiecePlayability(java.awt.Point piecePoint) {

        // Convert coordinate to board coordinate
        int boardX = piecePoint.x / chessView.getGridSize();
        int boardY = piecePoint.y / chessView.getGridSize();

        return chessModel.checkPiecePlayability(boardX, boardY);
    }

    /**
     *
     * Converts the source and destination {@code Point} of a chess piece from the container screen to board-relative and use them to 
     * attempt the piece move. This method calls the chess model's {@code playPieceMove} method.
     *
     * @param source      the source {@code Point} 
     * @param destination the destination {@code Point} 
     * @see chessgame.ChessGame#playPieceMove(Point, Point)
     * @author Chay Wen Ning
     * @author Melody Koh Si Jie
     */
    public void convertCoordinateAndAttemptPlay(Point source, Point destination) {
        Point boardSource = new Point(source.x / chessView.getGridSize(), source.y / chessView.getGridSize());
        Point boardDestination = new Point(destination.x / chessView.getGridSize(),
                destination.y / chessView.getGridSize());

        chessModel.playPieceMove(boardSource, boardDestination);
    }

    /**
     *
     * Starts a new chess game. This method sets the chess model with a new {@code ChessGame} object, 
     * and add itself as an {@code Observer} object to the chess model. 
     * It then calls the chess view to update the chess game container.
     *
     * @see chessgame.ChessGame
     * @see observer.Subject#addObserver(Observer)
     * @author Chay Wen Ning
     */
    public void newGame() {

        setModel(new ChessGame());
        chessModel.addObserver(this);

        chessView.updatePieceIcons(chessModel.getBoard());
        chessView.addPieceIconResizer(chessModel.getBoard());
        chessView.displayPlayerTurn(chessModel.getPlayerTurn());
        chessView.displayPlayerStatus(false);
        chessView.highlightLastMovedPiece(null);
    }

    /**
     *
     * Calls the chess model's {@code writeGameDataToFile} method to save the game and notifies the save result to chess view to display.
     *
     * @param file the {@code File} object to save game data to
     * @see chessgame.ChessGame#writeGameDataToFile(File)
     * @see chessgame.ChessView#displaySaveResult(boolean)
     * @see #loadGameData(File)
     * @author Goh Shi Yi
     */
    public void saveGameData(File file) {

        chessView.displaySaveResult(chessModel.writeGameDataToFile(file));
    }

    /**
     * Calls the chess model's {@code loadGameDataFromFile} method to load the game and notifies the load result to chess view to display.
     *
     * @param file the {@code File} object to load game data from
     * @return {@code true} if the game loaded successfully
     * @see chessgame.ChessGame#loadGameDataFromFile(File)
     * @see chessgame.ChessView#displayLoadResult(boolean)
     * @see #saveGameData(File)
     * @author Choo Yun Yi
     */
    public boolean loadGameData(File file) {
        boolean result = chessModel.loadGameDataFromFile(file);
        chessView.displayLoadResult(result);

        if (result) {
            chessView.updatePieceIcons(chessModel.getBoard());
            chessView.highlightLastMovedPiece(chessModel.getLastMovedPiece());
            displayCurrentPlayerTurn();
            displayCurrentPlayerStatus();
            chessView.updateSwitchButton(chessModel.getHasPlayed());

            return true;
        }
        return false;
    }

    
    
    /** 
     * 
     * Handles {@code Event} notified by the {@code Subject} object.
     * @param event the notified {@code Event}
     * @see observer.Event 
     * @author Chay Wen Ning
     */
    @Override
    public void handleEvent(Event event) {
        switch (event) {
            case PIECEMOVE: {
                handlePieceMoveEvent();
                break;
            }
            case PIECESWITCH: {
                handlePieceSwitchEvent();
                break;
            }
            case PLAYERWIN: {
                int winnerIndex = (int) Event.PLAYERWIN.getArgument();
                handlePlayerWinEvent(winnerIndex);
                break;
            }
            case NEWGAME: {
                newGame();
                break;
            }
            default:
                break;
        }
    }

    /**
     *
     * Handles the notified {@code Event.PIECESWITCH}. 
     * This method calls the chess view to notify the piece switch event and update the piece icons. 
     *
     * @see #handleEvent(Event)
     * @see chessgame.ChessView#notifyPieceSwitch()
     * @see observer.Event#PIECESWITCH
     */
    public void handlePieceSwitchEvent() {
        chessView.notifyPieceSwitch();
        chessView.updatePieceIcons(chessModel.getBoard());
    }

    /**
     *
     * Handles the notified {@code Event.PIECEMOVE}. 
     * This method calls the chess view to update the chess game container. 
     *
     * @see #handleEvent(Event)
     * @see observer.Event#PIECEMOVE
     */
    public void handlePieceMoveEvent() {
        chessView.updatePieceIcons(chessModel.getBoard());
        chessView.displayPlayerStatus(true);
        chessView.updateSwitchButton(true);
    }

    /**
     *
     * Handles the notified {@code Event.PLAYERWIN}. 
     * This method calls the chess view to prompt player for a new game.
     *
     * @param winnerIndex the index of the winner {@code Player}
     * @see #handleEvent(Event)
     * @see chessgame.ChessView#promptNewGameConfirmation(int)
     * @see observer.Event#PLAYERWIN
     */
    public void handlePlayerWinEvent(int winnerIndex) {
        chessView.promptNewGameConfirmation(winnerIndex);
    }
}
