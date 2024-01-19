/**
*
* @author Chay Wen Ning
* @author Melody Koh
* @author Goh Shi Yi
* @author Choo Yun Yi
*/

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
The {@code ChessView} class ; Implements {@code Subject} class.
 */
public class ChessView implements Subject {
    private ChessGameContainer frame;
    private int gridSize;
    private JLabel lastHighlightedGrid;
    private Color lastHighlightedGridColor;

    Set<Observer> observerList = new HashSet<>();

    // Represent different types of images associated with chess pieces
    private enum PieceImageType {
        Hourglass("Hourglass.png"),
        Plus("Plus.png"),
        PointDown("Point(down).png"),
        PointUp("Point(up).png"),
        Sun("Sun.png"),
        Time("Time.png");

        private final HashMap<Color, Image> colorPathMap = new HashMap<>();

        private PieceImageType(String imageName) {
            String yellowPiecePath = "../Icons/YellowPieces/" + imageName;
            String bluePiecePath = "../Icons/BluePieces/" + imageName;

            colorPathMap.put(Color.YELLOW, new ImageIcon(this.getClass().getResource(yellowPiecePath)).getImage());
            colorPathMap.put(Color.BLUE, new ImageIcon(this.getClass().getResource(bluePiecePath)).getImage());
        }

        public final Image getImage(Color color) {
            return colorPathMap.get(color);
        }
    }

    /**
     *
     * Constructs a new {@code ChessView}.
     *
     * @param frame the {@code ChessGameContainer}
     */
    public ChessView(ChessGameContainer frame) {
        this.frame = frame;
        gridSize = frame.GRID_SIZE;
    }

    /**
     * Get the grid size
     * 
     * @return the grid size
     */
    public int getGridSize() {
        return gridSize;
    }

    @Override
    /**
     * Add observer
     * @param o the {@code Observer}
     */
    public void addObserver(Observer o) {
        observerList.add(o);
    }

    @Override
    /**
     *
     * Notify Observer to response to the event triggered
     *
     * @param event the {@code Event}.
     */
    public void notifyObservers(Event event) {
        for (Observer o : observerList) {
            o.handleEvent(event);
        }
    }

    /**
     * Show the player's turn
     * 
     * @param playerTurn the player's turn  
     */
    public void displayPlayerTurn(int playerTurn) {
        frame.getPlayerTurnLabel().setText("Player's Turn: " + (playerTurn + 1));
    }

    /**
     * Show the player's status
     * 
     * @param hasPlayed indicates whether the player has played in the form of boolean
     */
    public void displayPlayerStatus(boolean hasPlayed) {
        frame.getPlayerStatusLabel().setText("Has played: " + hasPlayed);
    }

    /**
     * Update and enable the switch button
     * 
     * @param enabled whether the switch is enabled in the form of boolean
     */
    public void updateSwitchButton(boolean enabled) {
        frame.getSwitchBtn().setEnabled(enabled);
    }

    /**
     * Get the piece image
     * 
     * @param piece the {@code Piece} 
     * @return the piece image
     */
    public Image getPieceImage(Piece piece) {
        if (piece == null)
            return null;

        Image image;
        if (piece.getPieceType() == PieceType.Point) {
            image = piece.isFlipped() ? PieceImageType.PointDown.getImage(piece.getColor())
                    : PieceImageType.PointUp.getImage(piece.getColor());
        } else {
            PieceImageType pieceImageType = PieceImageType.valueOf(piece.getPieceType().toString());
            image = pieceImageType.getImage(piece.getColor());
        }

        return image.getScaledInstance(gridSize, gridSize, java.awt.Image.SCALE_SMOOTH);
    }

    /**
     * Update the grid size
     */
    public void updateGridSize() {
        gridSize = frame.getGridPanel().getComponent(0).getWidth();
    }

    /**
     * Notify the player that Time and Plus pieces will be switched after two turns.
     */
    public void notifyPieceSwitch() {
        JOptionPane.showMessageDialog(frame.getGridPanel(), "Time and Plus pieces will now switch!");
    }

    /**
     * Prompt a new game confrimation
     * 
     * @param winnerIndex the index of winner  
     * @return {@code true} if player confirms a new game
     */
    public boolean promptNewGameConfirmation(int winnerIndex) {
        int opt = JOptionPane.showConfirmDialog(frame,
                "Player " + (winnerIndex + 1) + " has won. Choose \"Yes\" to start a new game, \"No\" to quit game",
                "Game Over", JOptionPane.YES_NO_OPTION);
        if (opt == JOptionPane.YES_OPTION) {
            frame.resetContainer();
            notifyObservers(Event.NewGame);
            return true;
        } else {
            System.exit(0);
            return false;
        }
    }

    /**
     * Add the piece icon resizer
     * 
     * @param board the {@code ChessBoard} 
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
     * Load the piece icons to the board
     * 
     * @param board the {@code ChessBoard} 
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
     * Udate the piece icons
     * 
     * @param board the {@code ChessBoard}
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
     * Highlight the chess piece last moved by the player
     * 
     * @param board          the {@code ChessBoard} 
     * @param lastMovedPiece the {@code Piece}  last moved by the player 
     */
    public void highlightLastMovedPiece(ChessBoard board, Piece lastMovedPiece) {
        if (lastHighlightedGrid != null) {
            lastHighlightedGrid.setBorder(null);
            lastHighlightedGrid.setBackground(lastHighlightedGridColor);
        }

        if (lastMovedPiece == null)
            return;

        JPanel gridPanel = frame.getGridPanel();
        JLabel grid = (JLabel) gridPanel
                .getComponent(lastMovedPiece.getX() + lastMovedPiece.getY() * frame.NO_OF_COLUMN);
        // grid.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.ORANGE));

        lastHighlightedGrid = grid;
        lastHighlightedGridColor = grid.getBackground();
        grid.setBackground(Color.ORANGE);

    }

    /**
     * Display the result of a save
     * 
     * @param result whether the file is saved in the form of boolean
     */
    public void displaySaveResult(boolean result) {
        if (result) {
            JOptionPane.showMessageDialog(frame.getGridPanel(), "Save Successfully");
        } else {
            JOptionPane.showMessageDialog(frame.getGridPanel(), "Error: Save Unsuccessfully");
        }
    }

    /**
     * Display the result of a load
     * 
     * @param result whether the file is loaded in the form of boolean
     */
    public void displayLoadResult(boolean result) {
        if (result) {
            JOptionPane.showMessageDialog(frame.getGridPanel(), "Load Successfully");
        } else {
            JOptionPane.showMessageDialog(frame.getGridPanel(), "Error: Load Unsuccessfully");
        }
    }
}
