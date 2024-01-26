package chessgame;

import java.awt.*;

import chesspiece.Hourglass;
import chesspiece.Plus;
import chesspiece.Sun;
import chesspiece.Time;
import chesspiece.Point;
import chesspiece.Piece;
import chesspiece.Piece.PieceType;
import observer.Event;
import observer.Observer;
import observer.Subject;
import player.Player;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import chessboard.ChessBoard;

/**
 * The {@code ChessGame} class; Implements {@link observer.Subject Subject} class.
 * This class is part of the <a href="https://www.geeksforgeeks.org/mvc-design-pattern/">MVC design pattern</a>, and it acts as a model in the MVC relationship.
 * The {@code ChessGame} represents a round of game consisting of two {@code Player} and a {@code ChessBoard}.
 * It contains the game logic and relevant game data such as the current player turn, and the last moved piece.
 * It is called by the {@code ChessController}.
 * <p>
 * Additionally, this class is part of the <a href="https://www.geeksforgeeks.org/observer-pattern-set-1-introduction/">Observer design pattern</a>, and it acts as an {@code Subject}.
 * When being referenced to the {@code ChessController}, the {@code ChessGame} adds the former as an {@code Observer} and notifies {@code Event} to it for subsequence actions.
 * 
 * @see observer.Subject
 * @see player.Player
 * @see chessboard.ChessBoard
 * @see chessgame.ChessController
 * @see observer.Observer
 * @see observer.Event
 * @author Chay Wen Ning
 * @author Melody Koh
 * @author Goh Shi Yi
 * @author Choo Yun Yi
 */
public class ChessGame implements Subject {
    private final int SWITCH_COUNTER = 4;
    private Player[] players = new Player[2];
    private ChessBoard board;
    private int playerTurn;

    private Piece selectedPiece;
    private Piece lastMovedPiece;

    private Set<Observer> observerList = new HashSet<>();

    /**
     *
     * Constructs a new {@code ChessGame} with a {@code ChessBoard} object and two {@code Player} objects. 
     *
     * @see chessboard.ChessBoard
     * @see player.Player
     * @author Chay Wen Ning
     */
    public ChessGame() {
        Player.resetPlayerCount();
        board = new ChessBoard();

        int offSetY = 4;
        boolean opposite = false;
        for (int i = 0; i < 2; i++) {
            players[i] = new Player();
            players[i].initializePieces(0, offSetY, opposite);
            board.setPlayerPiece(players[i]);
            offSetY = 0;
            opposite = true;
        }

        playerTurn = 0;
    }

    /**
     *
     * Returns a {@code Player} object of the specified index.
     *
     * @param index  the index of a {@code Player} object
     * @return the {@code Player} object of the specified index
     * @author Chay Wen Ning
     */
    public Player getPlayer(int index) {
        return players[index];
    }

    /**
     *
     * Returns the chess game's {@code ChessBoard} object.
     *
     * @return the chess game's {@code ChessBoard} object
     * @see chessboard.ChessBoard
     * @author Chay Wen Ning
     */
    public ChessBoard getBoard() {
        return board;
    }

    /**
     *
     * Returns the current player turn.
     *
     * @return the current player turn
     * @author Chay Wen Ning
     */
    public int getPlayerTurn() {
        return playerTurn;
    }

    /**
     *
     * Returns the {@code Piece} object selected by the player.
     *
     * @return the {@code Piece} object selected by the player
     * @author Chay Wen Ning
     */
    public Piece getSelectedPiece() {
        return selectedPiece;
    }

    /**
     *
     * Returns the {@code Piece} object last moved by the player.
     *
     * @return the {@code Piece} object last moved by the player
     * @author Chay Wen Ning
     */
    public Piece getLastMovedPiece() {
        return lastMovedPiece;
    }

    /**
     *
     * Returns the current player's {@code HasPlayed} status. 
     * Returns true if the current player has moved a piece, else false.
     *
     * @return {@code true} if the current player's {@code hasPlayed == true}
     * @see player.Player#hasPlayed()
     * @author Choo Yun Yi
     */
    public boolean getHasPlayed() {
        return players[playerTurn].hasPlayed();
    }

    /**
     *
     * Adds an {@code Observer} object.
     *
     * @param o  the {@code Observer} object to be added
     * @see observer.Observer
     * @author Chay Wen Ning
     */
    @Override
    public void addObserver(Observer o) {
        observerList.add(o);
    }

    /**
     *
     * Notifies the {@code Observer} object(s) to handle the fired {@code Event}.
     *
     * @param event  the {@code Event} to be notified
     * @see #addObserver(Observer)
     * @see observer.Observer
     * @see observer.Event
     * @author Chay Wen Ning
     */
    @Override
    public void notifyObservers(Event event) {
        for (Observer o : observerList) {
            o.handleEvent(event);
        }
    }

    /**
     *
     * Calculates and returns the total number of rounds played by each player.
     *   
     * @return the total number of rounds played by each player 
     * @see player.Player#getPlayCount()
     * @author Melody Koh Si Jie
     * @author Goh Shi Yi
     */
    public int totalPlayCount() {
        int totalPlayCount = 0;
        for (Player p : players) {
            totalPlayCount += p.getPlayCount();
        }
        return totalPlayCount;
    }
    
    /**
     *
     * Switches the current player turn and reset their {@code hasPlayed} status.
     * After that, flip the chess board.
     * 
     * @see player.Player#resetHasPlayed()
     * @see chessboard.ChessBoard#flip()
     * @author Chay Wen Ning
     * @author Goh Shi Yi
     */
    public void switchTurnAndFlipBoard() {
        playerTurn = playerTurn == 1 ? 0 : 1;
        players[playerTurn].resetHasPlayed();
        board.flip();
    }

    /**
     *
     * Checks if the total play count has reached the switch counter.
     * If so, switch {@code Time} and {@code Plus} pieces and notify observers with {@code Event.PIECESWTICH}.
     * @author Chay Wen Ning
     * @author Melody Koh Si Jie
     * 
     * @see chesspiece.Piece#cloneToPlus()
     * @see chesspiece.Piece#cloneToTime()
     * @see observer.Event#PIECESWTICH
     */
    public void switchPiecesIfPlayCountReached() {
        if (totalPlayCount() == SWITCH_COUNTER) {
            switchTimeAndPlusPiece();
            notifyObservers(Event.PIECESWITCH);
        }
    }

    /**
     *
     * Gets the {@code Piece} object at the specified x, y coordinate on the chess board and check if the piece is playable. 
     * Returns {@code true} if the piece is playable. 
     * If there is no piece at the specified x, y coordinate or the piece is not playable, returns {@code false}.
     * 
     * @param x the x coordinate  
     * @param y the y coordinate  
     * @return {@code true} if the {@code Piece} object located at the specified x, y coordinate is playable
     * @author Melody Koh Si Jie
     */
    public boolean checkPiecePlayability(int x, int y) {
        selectedPiece = board.getPieceAt(x, y);
        
        return selectedPiece != null && !players[playerTurn].hasPlayed()
                && selectedPiece.getColor().equals(players[playerTurn].getColor());
    }

    /**
     *
     * Checks if the {@code Piece} object at the specified source {@code Point} can be moved to the specified destination {@code Point}.
     * Returns {@code true} if the piece can be moved from the specified source to the specified destination. 
     * If there is no piece object at the specified source or the piece cannot be moved to the specified destination, returns {@code false}.
     * 
     * @param source the source {@code Point}
     * @param destination the destination {@code Point}
     * @return {@code true} if the {@code Piece} object at the specified source can be moved to the specified destination.
     * @see #checkPiecePlayability(int, int)
     * @see chesspiece.Piece#isMovableTo(ChessBoard, Piece, int, int)
     * @author Chay Wen Ning
     */
    public boolean checkPieceMove(java.awt.Point source, java.awt.Point destination) {
        if (checkPiecePlayability(source.x, source.y)
                && (selectedPiece.getX() != destination.x || selectedPiece.getY() != destination.y)) {
            return selectedPiece.isMovableTo(board, destination.x, destination.y);
        }

        return false;
    }

    /**
     *
     * Plays a piece move by removing any {@code Piece} object located at the specified destination {@code Point}, then 
     * move the selected {@code Piece} object at the specified source to the specified destination.
     * Returns if no piece is at the specified source or the piece cannot be moved to the specified destination.
     * This methods calls {@code checkPieceMove()} to validate the mentioned scenario. 
     * <p>
     * After playing the piece move, notify the observers of {@code Event.PIECEMOVE}.
     * If the selected piece is a {@code Point} piece and it reaches the end of the board, flip the piece.
     * 
     * @param source the source {@code Point} ({@code java.awt.Point})
     * @param destination the destination {@code Point} ({@code java.awt.Point})
     * @see #checkPieceMove(java.awt.Point, java.awt.Point)
     * @see observer.Event#PIECEMOVE
     * @author Chay Wen Ning
     * @author Melody Koh Si Jie
     */
    public void playPieceMove(java.awt.Point source, java.awt.Point destination) {
        if (!checkPieceMove(source, destination))
            return;

        Piece pieceToCapture = board.getPieceAt(destination.x, destination.y);

        if (pieceToCapture != null) {
            int opponentIndex = playerTurn == 1 ? 0 : 1;
            board.removePiece(pieceToCapture, players[opponentIndex]);
        }

        board.setPieceAt(selectedPiece, destination.x, destination.y);

        // if the piece is a Point piece and it reached the end, flip it
        if (selectedPiece.getPieceType() == PieceType.POINT
                && (selectedPiece.getY() == 0 || selectedPiece.getY() == board.getNoOfRow() - 1)) {
            selectedPiece.setFlipped(!selectedPiece.isFlipped());
        }

        notifyObservers(Event.PIECEMOVE);
        endTurn();
    }

    /**
     * 
     * Checks if a player has won the game and notifies the observers of {@code Event.PLAYERWIN} if so.
     * Else if no player has won, increment the current player's play count and set their {@code hasPlayed} to {@code true}.
     * 
     * @see #checkWinner()
     * @see observer.Event#PLAYERWIN
     * @author Chay Wen Ning
     */
    public void endTurn() {
        Player winner = checkWinner();
        if (winner != null) {
            notifyObservers(Event.PLAYERWIN.returnArgument(winner.getIndex()));
        } else {
            players[playerTurn].incrementPlayCount();
            players[playerTurn].setHasPlayed(true);

            lastMovedPiece = selectedPiece;
            selectedPiece = null;
        }
    }

    /**
     * 
     * Determines whether each player has their {@code Sun} piece. 
     * If a player has no {@code Sun} piece, returns the other player as the winner.
     * If there is no winner, return {@code null}.
     * 
     * @return the winner {@code Player} object, {@code null} if there is no winner
     * @author Chay Wen Ning
     * @author Goh Shi Yi
     */
    public Player checkWinner() {
        // check if each player still have the Sun piece
        for (Player p : players) {
            Set<Piece> pieces = p.getPieces();
            boolean hasSun = false;

            for (Piece pc : pieces) {
                if (pc.getPieceType() == PieceType.SUN) {
                    hasSun = true;
                    break;
                }
            }
            if (!hasSun)
                return players[p.getIndex() == 1 ? 0 : 1];
        }

        return null;
    }

    /**
     *
     * Switches the {@code Time} pieces to {@code Plus} piece and {@code Plus} pieces to {@code Time} piece.
     * 
     * @see chesspiece.Piece#cloneToPlus()
     * @see chesspiece.Piece#cloneToTime()
     * @author Chay Wen Ning
     * @author Melody Koh Si Jie
     */
    public void switchTimeAndPlusPiece() {
        for (Player pl : players) {
            Set<Piece> piecesToAdd = new HashSet<>();
            Set<Piece> piecesToRemove = new HashSet<>();
            Set<Piece> playerPieces = pl.getPieces();
            for (Piece pc : playerPieces) {
                PieceType pType = pc.getPieceType();
                if (pType == Piece.PieceType.TIME) {
                    piecesToAdd.add(pc.cloneToPlus());
                    piecesToRemove.add(pc);

                } else if (pType == Piece.PieceType.PLUS) {
                    piecesToAdd.add(pc.cloneToTime());
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
            pl.resetPlayCount();
        }
    }

    /**
     *
     * Writes the data of the current game to the specified {@link java.io.File File} object.
     * Returns {@code true} if no exception occurs.
     * 
     * @param file the {@code File} object to write data to
     * @return {@code true} if no exception occurs
     * @see chessgame.ChessController#saveGameData(File)
     * @see #loadGameDataFromFile(File)
     * @author Goh Shi Yi
     */
    public boolean writeGameDataToFile(File file) {
        try (FileWriter writer = new FileWriter(file + ".txt")) {
            writer.write(getPlayerTurn() + "\n"); // 0
            writer.write(getPlayer(0).getPlayCount() + "\n"); // 1
            writer.write(getPlayer(1).getPlayCount() + "\n"); // 2
            writer.write(getLastMovedPiece().getX() + "," // 3
                    + getLastMovedPiece().getY() + "," // 3
                    + getLastMovedPiece().getPieceType() + "," // 3
                    + getLastMovedPiece().isFlipped() + "\n" // 3
                    + getLastMovedPiece().getColor() + "\n"); // 4
            for (int x = 0; x < getBoard().getNoOfColumn(); x++) {
                for (int y = 0; y < getBoard().getNoOfRow(); y++) {
                    Piece piece = getBoard().getPieceAt(x, y);
                    if (piece != null) {
                        writer.write(x + ","
                                + y + ","
                                + piece.getPieceType() + ","
                                + piece.isFlipped() + "\n"
                                + piece.getColor() + "\n");
                    }
                }
            }
            writer.write(getPlayer(getPlayerTurn()).hasPlayed() + "\n");
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * 
     * Generates and returns the corresponding {@code Color} object 
     * from the specified {@code String} object that contains RGB color code.
     *      
     * @param string the {@code String} object that contains RGB color code
     * @return the corresponding {@code Color} object 
     * @author Choo Yun Yi
     */
    public Color toColor(String string) {
        String[] colorString = string.split(",");
       
        String redString, greenString, blueString;
        int red = 0, green = 0, blue = 0;                      //initialize 
        Pattern pattern = Pattern.compile("\\d+");      //a sequence of digits 0-9

         // red
        Matcher matcher = pattern.matcher(colorString[0]);  //get digits from red
        while (matcher.find()) {                            //finds the matching pattern, if true:
            redString = matcher.group();                    // returns the matching string
            red = Integer.parseInt(redString);
        }

        // green
        matcher = pattern.matcher(colorString[1]);
        while (matcher.find()) {
            greenString = matcher.group();
            green = Integer.parseInt(greenString);
        }
        
        // blue
        matcher = pattern.matcher(colorString[2]);
        while (matcher.find()) {
            blueString = matcher.group();
            blue = Integer.parseInt(blueString);
        }

        Color colour = new Color(red, green, blue);

        return colour;
    }

    /**
     * 
     * Read and load the game data of the specified {@link java.io.File File} object.
     * Returns {@code true} if no exception occurs.
     * 
     * @param fileName the {@code File} object to read data from
     * @return {@code true} if no exception occurs
     * @see chessgame.ChessController#loadGameData(File)
     * @see #writeGameDataToFile(File)
     * @author Choo Yun Yi
     */
    public boolean loadGameDataFromFile(File fileName) {
        StringBuilder fileContent = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            int totalLine = 0;
            while ((line = reader.readLine()) != null) {
                fileContent.append(line).append("\n");
                totalLine++;
            }

            String fileString = fileContent.toString();
            String[] fileLine = fileString.split("\n");

            // playerTurn
            playerTurn = Integer.parseInt(fileLine[0]);

            //playerHasPlayed
            players[playerTurn].setHasPlayed(Boolean.parseBoolean(fileLine[totalLine-1]));

            // player play count
            for (int i = 0; i < 2; i++) {
                players[i].setPlayCount(Integer.parseInt(fileLine[i+1]));
                players[i].clearPieces();
            }
            
            // clear board
            board.clearPieces();

            // load pieces
            for (int min = 3; min < totalLine-1; min++) {
                Piece loadPiece = null;
                Color colour = toColor(fileLine[min+1]);
                int playerIndex = colour.equals(Color.YELLOW) ? 0 : 1;
                String[] loadPieceString = fileLine[min].split(",");
                
                switch (PieceType.valueOf(loadPieceString[2])) {
                    case HOURGLASS: {
                        loadPiece = new Hourglass(Integer.parseInt(loadPieceString[0]),
                                Integer.parseInt(loadPieceString[1]),
                                colour,
                                Boolean.parseBoolean(loadPieceString[3]));
                        break;
                    }
                    case PLUS: {
                        loadPiece = new Plus(Integer.parseInt(loadPieceString[0]),
                                Integer.parseInt(loadPieceString[1]),
                                colour,
                                Boolean.parseBoolean(loadPieceString[3]));
                        break;
                    }
                    case POINT: {
                        loadPiece = new Point(Integer.parseInt(loadPieceString[0]),
                                Integer.parseInt(loadPieceString[1]),
                                colour,
                                Boolean.parseBoolean(loadPieceString[3]));
                        break;
                    }
                    case SUN: {
                        loadPiece = new Sun(Integer.parseInt(loadPieceString[0]),
                                Integer.parseInt(loadPieceString[1]),
                                colour,
                                Boolean.parseBoolean(loadPieceString[3]));
                        break;
                    }
                    case TIME: {
                        loadPiece = new Time(Integer.parseInt(loadPieceString[0]),
                                Integer.parseInt(loadPieceString[1]),
                                colour,
                                Boolean.parseBoolean(loadPieceString[3]));
                        break;
                    }
                    
                }

                getPlayer(playerIndex).addPiece(loadPiece);

                
                if (min == 3) {
                    lastMovedPiece = loadPiece;
                }
                min++;
            }
            
            for (int i = 0; i < 2; i++) {
                board.setPlayerPiece(players[i]);
            }

        } catch (IOException e) {
            System.out.println("File does not exist.");
            return false;
        }
        
        return true;
    }
}