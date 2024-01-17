/**
*
* @author Chay Wen Ning
* @author Melody Koh
* @author Goh Shi Yi
* @author Choo Yun Yi
*/

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
 * The class ChessGame implements Subject
 */
public class ChessGame implements Subject {
    private final int switchCounter = 4;
    private Player[] players = new Player[2];
    private ChessBoard board;
    private int playerTurn;

    private Piece selectedPiece;
    private Piece lastMovedPiece;

    private Set<Observer> observerList = new HashSet<>();

    /**
     *
     * Chess Game
     *
     * @return public
     */
    public ChessGame() {
        Player.resetPlayerCount();
        board = new ChessBoard();

        int offSetY = 4;
        boolean opposite = false;
        for (int i = 0; i < 2; i++) {
            players[i] = new Player();
            players[i].initializePieces(board, 0, offSetY, opposite);
            board.setPlayerPiece(players[i]);
            offSetY = 0;
            opposite = true;
        }

        playerTurn = 0;
    }

    // Getters
    public Player getPlayer(int index) {
        return players[index];
    }

    public ChessBoard getBoard() {
        return board;
    }

    public int getPlayerTurn() {
        return playerTurn;
    }

    public Piece getSelectedPiece() {
        return selectedPiece;
    }

    public Piece getLastMovedPiece() {
        return lastMovedPiece;
    }

    public boolean getHasPlayed() {
        return players[playerTurn].hasPlayed();
    }

    @Override
    public void addObserver(Observer o) {
        observerList.add(o);
    }

    @Override
    public void notifyObservers(Event event) {
        for (Observer o : observerList) {
            o.handleEvent(event);
        }
    }

    public int totalPlayCount() {
        int totalPlayCount = 0;
        for (Player p : players) {
            totalPlayCount += p.getPlayCount();
        }
        return totalPlayCount;
    }

    public void switchTurnAndFlipBoard() {
        playerTurn = playerTurn == 1 ? 0 : 1;
        players[playerTurn].resetHasPlayed();
        board.flip();
    }

    public void switchPiecesIfPlayCountReached() {
        if (totalPlayCount() == switchCounter) {
            switchTimeAndPlusPiece();
            notifyObservers(Event.PieceSwitch);
        }
    }

    public boolean checkPiecePlayability(int x, int y) {
        selectedPiece = board.getPieceAt(x, y);
        
        return selectedPiece != null && !players[playerTurn].hasPlayed()
                && selectedPiece.getColor().equals(players[playerTurn].getColor());
    }

    public boolean checkPieceMove(java.awt.Point source, java.awt.Point destination) {
        if (checkPiecePlayability(source.x, source.y)
                && (selectedPiece.getX() != destination.x || selectedPiece.getY() != destination.y)) {
            return selectedPiece.isMovableTo(board, selectedPiece, destination.x, destination.y);
        }

        return false;
    }

    public void playPieceMove(java.awt.Point source, java.awt.Point destination) {
        if (!checkPieceMove(source, destination))
            return;

        Piece killedPiece = board.getPieceAt(destination.x, destination.y);

        if (killedPiece != null) {
            int opponentIndex = playerTurn == 1 ? 0 : 1;
            board.removePiece(killedPiece, players[opponentIndex]);
        }

        board.setPieceAt(selectedPiece, destination.x, destination.y);

        // if the piece is a Point piece and it reached the end, flip it
        if (selectedPiece.getPieceType() == PieceType.Point
                && (selectedPiece.getY() == 0 || selectedPiece.getY() == board.getNoOfRow() - 1)) {
            selectedPiece.setFlipped(!selectedPiece.isFlipped());
        }

        notifyObservers(Event.PieceMove);
        endTurn();
    }

    public void endTurn() {
        Player winner = checkWinner();
        if (winner != null) {
            notifyObservers(Event.PlayerWin.returnArgument(winner.getIndex()));
        } else {
            players[playerTurn].incrementPlayCount();
            players[playerTurn].setHasPlayed(true);

            lastMovedPiece = selectedPiece;
            selectedPiece = null;
        }
    }

    public Player checkWinner() {
        // check if each player still have the Sun piece
        for (Player p : players) {
            Set<Piece> pieces = p.getPieces();
            boolean hasSun = false;

            for (Piece pc : pieces) {
                if (pc.getPieceType() == PieceType.Sun) {
                    hasSun = true;
                    break;
                }
            }
            if (!hasSun)
                return players[p.getIndex() == 1 ? 0 : 1];
        }

        return null;
    }

    // switch Time piece and Plus piece, and vice versa
    public void switchTimeAndPlusPiece() {
        for (Player pl : players) {
            Set<Piece> piecesToAdd = new HashSet<>();
            Set<Piece> piecesToRemove = new HashSet<>();
            Set<Piece> playerPieces = pl.getPieces();
            for (Piece pc : playerPieces) {
                PieceType pType = pc.getPieceType();
                if (pType == Piece.PieceType.Time) {
                    piecesToAdd.add(pc.cloneToPlus());
                    piecesToRemove.add(pc);

                } else if (pType == Piece.PieceType.Plus) {
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
                    case Hourglass: {
                        loadPiece = new Hourglass(Integer.parseInt(loadPieceString[0]),
                                Integer.parseInt(loadPieceString[1]),
                                colour,
                                Boolean.parseBoolean(loadPieceString[3]));
                        break;
                    }
                    case Plus: {
                        loadPiece = new Plus(Integer.parseInt(loadPieceString[0]),
                                Integer.parseInt(loadPieceString[1]),
                                colour,
                                Boolean.parseBoolean(loadPieceString[3]));
                        break;
                    }
                    case Point: {
                        loadPiece = new Point(Integer.parseInt(loadPieceString[0]),
                                Integer.parseInt(loadPieceString[1]),
                                colour,
                                Boolean.parseBoolean(loadPieceString[3]));
                        break;
                    }
                    case Sun: {
                        loadPiece = new Sun(Integer.parseInt(loadPieceString[0]),
                                Integer.parseInt(loadPieceString[1]),
                                colour,
                                Boolean.parseBoolean(loadPieceString[3]));
                        break;
                    }
                    case Time: {
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