package chessgame;

import main.ChessGameContainer;
import chessboard.ChessBoard;
import chesspiece.Piece;
import chesspiece.Piece.PieceType;
import observer.Event;
import observer.Observer;
import observer.Subject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * The {@code ChessView} class; Implements {@code Subject} class.
 * This class is part of the <a href="https://www.geeksforgeeks.org/mvc-design-pattern/">MVC design pattern</a>, and it acts as a view in the MVC relationship.
 * The {@code ChessView} presents relevant game data of the {@code ChessGame} in the {@code ChessGameContainer}, such as the chess pieces on the chess board, 
 * the current player turn, and the last moved piece.
 * It is called by the {@code ChessController}.
 * <p>
 * Additionally, this class is part of the <a href="https://www.geeksforgeeks.org/observer-pattern-set-1-introduction/">Observer design pattern</a>, and it acts as an {@code Subject}.
 * When being referenced to the {@code ChessController}, the {@code ChessView} adds the former as an {@code Observer} and notifies {@code Event} to it for subsequence actions.
 * 
 * @see observer.Subject
 * @see chessgame.ChessController
 * @see chessgame.ChessGame
 * @see main.ChessGameContainer
 * @see observer.Observer
 * @see observer.Event
 * @author Chay Wen Ning
 * @author Melody Koh Si Jie
 * @author Goh Shi Yi
 * @author Choo Yun Yi
 */
public class ChessView implements Subject {
    private ChessGameContainer frame;
    private int gridSize;
    private JLabel lastHighlightedGrid;
    private Color lastHighlightedGridColor;

    Set<Observer> observerList = new HashSet<>();


    /**
     *
     * The {@code PieceImageType} Enum; Represents the types of piece images.
     * Each {@code PieceImageType} contains a {@code HashMap<Color, Image>COLOR_PATH_MAP} that maps piece {@code Image} to their corresponding {@code Color} type.
     * The types of {@code Color} are {@code Color.YELLOW} and {@code Color.BLUE}.
     *
     * @see chesspiece.Piece.PieceType
     * @author Chay Wen Ning
     */
    private enum PieceImageType {
        HOURGLASS("Hourglass.png"),
        PLUS("Plus.png"),
        POINTDOWN("Point(down).png"),
        POINTUP("Point(up).png"),
        SUN("Sun.png"),
        TIME("Time.png");

        private final HashMap<Color, Image> COLOR_PATH_MAP = new HashMap<>();

        /**
         *
         * Constructs a new {@code PieceImageType} and maps its piece {@code Image} of {@code Color.YELLOW} and {@code Color.BLUE} to their corresponding color.
         *
         * @param imageName the filename of the {@code Image}
         * @author Chay Wen Ning
         */
        private PieceImageType(String imageName) {
            String yellowPiecePath = "/Icons/YellowPieces/" + imageName;
            String bluePiecePath = "/Icons/BluePieces/" + imageName;

            COLOR_PATH_MAP.put(Color.YELLOW, new ImageIcon(this.getClass().getResource(yellowPiecePath)).getImage());
            COLOR_PATH_MAP.put(Color.BLUE, new ImageIcon(this.getClass().getResource(bluePiecePath)).getImage());
        }

        /**
         *
         * Returns the {@code Image} of the {@code PieceImageType} with the specified {@code Color}. 
         * If the specified {@code Color} is not {@code Color.YELLOW} or {@code Color.BLUE}, returns {@code null}.
         *
         * @param color the {@code Color} to return the {@code Image} with
         * @returns the {@code Image} of the {@code PieceImageType} with the specified {@code Color}, {@code null} if the specified {@code Color} is not {@code Color.YELLOW} or {@code Color.BLUE}
         * @author Chay Wen Ning
         */
        public final Image getImage(Color color) {
            return COLOR_PATH_MAP.get(color);
        }
    }

    /**
     *
     * Constructs a new {@code ChessView} object, and gets a reference of the {@code ChessGameContainer} object.
     *
     * @param frame the {@code ChessGameContainer} object 
     * @author Chay Wen Ning
     */
    public ChessView(ChessGameContainer frame) {
        this.frame = frame;
        gridSize = frame.GRID_SIZE;
    }

    /**
     * Returns the grid size of the chess piece.
     * 
     * @return the grid size of the chess piece
     * @author Chay Wen Ning
     */
    public int getGridSize() {
        return gridSize;
    }

    /**
     *
     * Adds an {@code Observer} object.
     *
     * @param o     the {@code Observer} object to be added
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
     * Displays the specified player's turn.
     * 
     * @param playerTurn the player's turn (in index form)
     * @author Chay Wen Ning
     */
    public void displayPlayerTurn(int playerTurn) {
        frame.getPlayerTurnLabel().setText("Player's Turn: " + (playerTurn + 1));
    }

    /**
     * Displays a player's status.
     * 
     * @param hasPlayed the player's {@code hasPlayed} status
     * @author Chay Wen Ning
     */
    public void displayPlayerStatus(boolean hasPlayed) {
        frame.getPlayerStatusLabel().setText("Has played: " + hasPlayed);
    }

    /**
     * Update the switch button in the referenced {@code ChessGameContainer} to the specified enabled state. 
     * If the enabled state is {@code true}, the switch button will be enabled - {@code setEnabled(true)}.
     * Otherwise if the enabled state is {@code false}, the switch button will be disabled - {@code setEnabled(false)}.
     * 
     * @param enabled the enabled state to update the switch button to
     * @author Choo Yun Yi
     */
    public void updateSwitchButton(boolean enabled) {
        frame.getSwitchBtn().setEnabled(enabled);
    }

    /**
     * Returns the {@code Image} that represents the specified {@code Piece} object. 
     * This method calls {@code PieceImageType.getImage} to retrieve the {@code Image} of a {@code PieceImageType} that corresponds to the {@code PieceType} of the chess piece.
     * 
     * @param piece the {@code Piece} object
     * @return the {@code Image} of the piece
     * @see chessgame.ChessView.PieceImageType
     * @see chessgame.ChessView.PieceImageType#getImage(Color)
     * @see chesspiece.Piece.PieceType
     * @author Chay Wen Ning
     */
    public Image getPieceImage(Piece piece) {
        if (piece == null)
            return null;

        Image image;
        if (piece.getPieceType() == PieceType.POINT) {
            image = piece.isFlipped() ? PieceImageType.POINTDOWN.getImage(piece.getColor())
                    : PieceImageType.POINTUP.getImage(piece.getColor());
        } else {
            PieceImageType pieceImageType = PieceImageType.valueOf(piece.getPieceType().toString());
            image = pieceImageType.getImage(piece.getColor());
        }

        return image.getScaledInstance(gridSize, gridSize, java.awt.Image.SCALE_SMOOTH);
    }

    /**
     * Updates the value of the grid size of chess piece. 
     * 
     * @author Chay Wen Ning
     */
    public void updateGridSize() {
        gridSize = frame.getGridPanel().getComponent(0).getWidth();
    }

    /**
     *
     * Displays a message dialog in the referenced {@code ChessGameContainer} to notify the piece switch event between {@code Time} and {@code Plus} pieces.
     * 
     * @author Chay Wen Ning
     */
    public void notifyPieceSwitch() {
        JOptionPane.showMessageDialog(frame.getGridPanel(), "Time and Plus pieces will now switch!");
    }

    /**
     * 
     * Prompts a new game confirmation in the referenced {@code ChessGameContainer}.
     * This method is called after a {@code Player} has won the game, and it declares the winner in the prompt.
     * If user selects "Yes" and confirms a new game, notifies the {@code Observer} object(s) of {@code Event.NEWGAME} and returns {@code true}.
     * Otherwise if user selects "No", exits the application.
     * 
     * @param winnerIndex the index of winner {@code Player}
     * @return {@code true} if user selects "Yes" and confirms a new game
     * @author Chay Wen Ning
     */
    public boolean promptNewGameConfirmation(int winnerIndex) {
        int opt = JOptionPane.showConfirmDialog(frame,
                "Player " + (winnerIndex + 1) + " has won. Choose \"Yes\" to start a new game, \"No\" to quit game",
                "Game Over", JOptionPane.YES_NO_OPTION);
        if (opt == JOptionPane.YES_OPTION) {
            frame.resetContainer();
            notifyObservers(Event.NEWGAME);
            return true;
        } else {
            System.exit(0);
            return false;
        }
    }

    /**
     * Removes existing {@code ComponentListener} of the referenced {@code ChessGameContainer} and attach a new {@code ComponentAdapter} to the container that resizes the piece {@code Icon} in the container whenever the component is resized.
     * This method calls {@code updateGridSize}, and {@code updatePieceIcons} to resize piece {@code Icon}.
     * 
     * @param board the {@code ChessBoard} object as reference
     * @see #updateGridSize()
     * @see #updatePieceIcons(ChessBoard)
     * @author Chay Wen Ning
     */
    public void addPieceIconResizer(ChessBoard board) {
        // remove exising component listener
        ComponentListener[] listeners = frame.getComponentListeners();
        if (listeners.length > 0) {
            for (ComponentListener li : listeners) {
                frame.removeComponentListener(li);
            }
        }
        // add new component listener
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentHidden(ComponentEvent e) {
            }

            @Override
            public void componentMoved(ComponentEvent e) {
            }

            @Override
            public void componentShown(ComponentEvent e) {
            }

            @Override
            public void componentResized(ComponentEvent e) {
                updateGridSize();
                updatePieceIcons(board);
            }
        });
    }

    /**
     * Loads piece {@code Icon} for every {@code Piece} object in the specified {@code ChessBoard} object into the referenced {@code ChessGameContainer}.
     * 
     * @param board the {@code ChessBoard} object 
     * @see #updatePieceIcons(ChessBoard)
     * @author Chay Wen Ning
     */
    public void loadPieceIcons(ChessBoard board) {
        JPanel gridPanel = frame.getGridPanel();

        int row = board.getNoOfRow();
        int col = board.getNoOfColumn();

        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                Piece piece = board.getPieceAt(c, r);
                JLabel grid = new JLabel();

                grid.setOpaque(true);
                grid.setPreferredSize(new Dimension(gridSize, gridSize));
                grid.setHorizontalAlignment(JLabel.CENTER);

                if (piece != null) {
                    Image image = getPieceImage(piece);
                    grid.setIcon(new ImageIcon(image));
                }

                if ((c + r) % 2 == 0) {
                    grid.setBackground(Color.GRAY);
                }

                gridPanel.add(grid);
            }
        }
    }

    /**
     * Update piece {@code Icon} for every {@code Piece} object in the specified {@code ChessBoard} object into the referenced {@code ChessGameContainer}.
     * 
     * @param board the {@code ChessBoard} object
     * @see #loadPieceIcons(ChessBoard)
     * @author Chay Wen Ning
     */
    public void updatePieceIcons(ChessBoard board) {
        JPanel gridPanel = frame.getGridPanel();

        int row = board.getNoOfRow();
        int col = board.getNoOfColumn();

        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                Piece piece = board.getPieceAt(c, r);
                JLabel grid = (JLabel) gridPanel.getComponent(c + r * col);

                if (piece == null) {
                    if (grid.getIcon() != null) {
                        grid.setIcon(null);
                    }
                    continue;
                }

                Image image = getPieceImage(piece);
                grid.setIcon(new ImageIcon(image));
                piece.setX(c);
                piece.setY(r);
            }
        }
    }

    /**
     * Highlights the {@code Piece} object last moved by the player in the referenced {@code ChessGameContainer}.
     * 
     * @param lastMovedPiece the {@code Piece} object last moved by the player 
     * @author Chay Wen Ning
     */
    public void highlightLastMovedPiece(Piece lastMovedPiece) {
        if (lastHighlightedGrid != null) {
            lastHighlightedGrid.setBorder(null);
            lastHighlightedGrid.setBackground(lastHighlightedGridColor);
        }

        if (lastMovedPiece == null)
            return;

        JPanel gridPanel = frame.getGridPanel();
        JLabel grid = (JLabel) gridPanel
                .getComponent(lastMovedPiece.getX() + lastMovedPiece.getY() * frame.NO_OF_COLUMN);

        lastHighlightedGrid = grid;
        lastHighlightedGridColor = grid.getBackground();
        grid.setBackground(Color.ORANGE);

    }

    /**
     * Displays the result of a save game call. 
     * This method is called after a user saves their game progress.
     * 
     * @param result the result of a save game call
     * @see main.ChessGameContainer#saveGame()
     * @see chessgame.ChessController#saveGameData(java.io.File)
     * @see #displayLoadResult(boolean)
     * @author Goh Shi Yi
     */
    public void displaySaveResult(boolean result) {
        if (result) {
            JOptionPane.showMessageDialog(frame.getGridPanel(), "Save Successfully");
        } else {
            JOptionPane.showMessageDialog(frame.getGridPanel(), "Error: Save Unsuccessfully");
        }
    }


    /**
     * Displays the result of a load game call. 
     * This method is called after a user loads their game progress.
     * 
     * @param result the result of a load game call
     * @see main.ChessGameContainer#loadGame()
     * @see chessgame.ChessController#loadGameData(java.io.File)
     * @see #displaySaveResult(boolean)
     * @author Choo Yun Yi
     */
    public void displayLoadResult(boolean result) {
        if (result) {
            JOptionPane.showMessageDialog(frame.getGridPanel(), "Load Successfully");
        } else {
            JOptionPane.showMessageDialog(frame.getGridPanel(), "Error: Load Unsuccessfully");
        }
    }
}
