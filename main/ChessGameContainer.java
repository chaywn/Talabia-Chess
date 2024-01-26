package main;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import chessgame.ChessGame;
import chessgame.ChessController;
import chessgame.ChessView;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;

/**
 * 
 * The {@code ChessGameContainer} class; Extends {@code JFrame}.
 * The {@code ChessGameContainer} contains the GUI components of the application, and handles user inputs by
 * communicating with the {@code ChessGame} and {@code ChessView} objects through the {@code ChessController}.
 * 
 * @see chessgame.ChessGame
 * @see chessgame.ChessView
 * @see chessgame.ChessController
 * @author Chay Wen Ning
 * @author Melody Koh
 * @author Goh Shi Yi
 * @author Choo Yun Yi
 */
public class ChessGameContainer extends JFrame {
    // Constants
    public final int NO_OF_ROW = 6;
    public final int NO_OF_COLUMN = 7;
    public final int GRID_SIZE = 50;

    private final Color TO_PLAY_COLOR = Color.LIGHT_GRAY;
    private final Color CAN_PLAY_COLOR = Color.GREEN;
    private final Color CANNOT_PLAY_COLOR = Color.RED;

    private ChessController chessController;
    private JFileChooser fc;

    private JPanel mainPanel;
    private JPanel glassPane;
    private JPanel gamePanel;
    private JPanel gridPanel;
    private JPanel sidePanel;

    private JLabel playerTurnLabel;
    private JLabel playerStatusLabel;

    private JButton switchBtn;
    private JButton saveBtn;

    private boolean gameStarted = false;

    private JLabel gridToPlay;
    private Color gridToPlayColor;
    private Icon gridToPlayIcon;

    private JLabel selectedGrid;
    private Color selectedGridColor;

    // Set by view
    private Color playabilityColor;
    private Image selectedPieceImage;

    private Point[] gridPanelMousePoints = new Point[2]; // record source and destination mouse points relative to grid
                                                         // panel
    private Point mainPanelMousePoint; // record global mouse point (relative to main panel)

    /**
     * 
     * Constructs a new {@code ChessGameContainer} with the GUI and game components.
     * This method creates a {@code ChessGame}, a {@code ChessView}, and a {@code ChessController} object.
     * 
     * @author Chay Wen Ning
     * @author Goh Shi Yi
     */
    public ChessGameContainer() {
        // Initialize chess components
        chessController = new ChessController(new ChessGame(), new ChessView(this));

        // Initialize Main Panel with mouse listeners
        mainPanel = new JPanel(new BorderLayout());
        MainPanelMouseListener ml = new MainPanelMouseListener();
        mainPanel.addMouseListener((MouseListener) ml);
        mainPanel.addMouseMotionListener((MouseMotionListener) ml);

        // Create Game Panel
        createGamePanel();

        // Create Side Panel
        createSidePanel();

        // Create Glass Pane
        createGlassPane();

        chessController.viewLoadPieceIcons();
        chessController.viewAddPieceIconResizer();

        // Add panels
        mainPanel.add(gamePanel, BorderLayout.CENTER);
        mainPanel.add(sidePanel, BorderLayout.EAST);
        add(mainPanel);

        // Set glass pane
        setGlassPane(glassPane);
        glassPane.setVisible(true);

        pack();
        setMinimumSize(
                new Dimension(NO_OF_COLUMN * GRID_SIZE + sidePanel.getWidth() + 138, NO_OF_ROW * GRID_SIZE + 120));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    /**
     * 
     * Returns the main panel in the {@code ChessGameContainer}.
     * 
     * @return the main panel in the {@code ChessGameContainer}
     * @author Chay Wen Ning
     */
    public JPanel getMainPanel() {
        return mainPanel;
    }

    /**
     * 
     * Returns the grid panel in the {@code ChessGameContainer}.
     * 
     * @return the grid panel in the {@code ChessGameContainer}
     * @author Chay Wen Ning
     */
    public JPanel getGridPanel() {
        return gridPanel;
    }

    /**
     * 
     * Returns the glass panel in the {@code ChessGameContainer}.
     * 
     * @return the glass panel in the {@code ChessGameContainer}
     * @author Chay Wen Ning
     */
    public JPanel getGlassPane() {
        return glassPane;
    }

    /**
     * 
     * Returns the player turn label in the {@code ChessGameContainer}.
     * 
     * @return the player turn label in the {@code ChessGameContainer}
     * @author Chay Wen Ning
     */
    public JLabel getPlayerTurnLabel() {
        return playerTurnLabel;
    }

    /**
     * 
     * Returns the player status label in the {@code ChessGameContainer}.
     * 
     * @return the label of player status
     * @author Chay Wen Ning
     */
    public JLabel getPlayerStatusLabel() {
        return playerStatusLabel;
    }

    /**
     * 
     * Returns the switch turn button in the {@code ChessGameContainer}.
     * 
     * @return the switch turn button in the {@code ChessGameContainer}
     * @author Chay Wen Ning
     */
    public JButton getSwitchBtn() {
        return switchBtn;
    }

    /**
     * 
     * Resets the components in the {@code ChessGameContainer}.
     * @author Chay Wen Ning
     * @author Melody Koh Si Jie
     */
    public void resetContainer() {
        if (gridToPlay != null) {
            gridToPlay.setBackground(gridToPlayColor);
            gridToPlay.setIcon(gridToPlayIcon);
        }
        gridToPlay = null;
        gridToPlayColor = null;
        gridToPlayIcon = null;

        if (selectedGrid != null) {
            selectedGrid.setBackground(selectedGridColor);
        }
        selectedGrid = null;
        selectedGridColor = null;
        selectedPieceImage = null;
    }

    /**
     * 
     * Creates a glass pane in the {@code ChessGameContainer}. 
     * The glass pane draws the selected chess piece image on the panel when the user clicks and drags a chess piece.
     * 
     * @author Chay Wen Ning
     * @author Goh Shi Yi
     */
    public void createGlassPane() {
        glassPane = new JPanel() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);

                // draw gray screen at Game Panel before game starts
                if (!gameStarted) {
                    g.setColor(new Color(0, 0, 0, 140));
                    g.fillRect(0, 0, gamePanel.getWidth(), gamePanel.getHeight());
                    return;
                }

                // draw piece image drag
                if (selectedPieceImage != null && mainPanelMousePoint != null) {
                    g.drawImage(selectedPieceImage,
                            mainPanelMousePoint.x - (int) (selectedPieceImage.getWidth(this) / 2),
                            mainPanelMousePoint.y - (int) (selectedPieceImage.getHeight(this) / 2), this);
                }
            }
        };
        glassPane.setOpaque(false);
    }

    /**
     * 
     * Creates a game panel in the {@code ChessGameContainer}.
     * The game panel displays the chess board in the form of grids.
     * @author Melody Koh Si Jie
     * @author Goh Shi Yi
     */
    public void createGamePanel() {
        gamePanel = new JPanel(new GridBagLayout());
        gamePanel.setBackground(Color.DARK_GRAY);

        gridPanel = new JPanel(new GridLayout(NO_OF_ROW, NO_OF_COLUMN)) {
            @Override // Overriding preferred size to always oblige to aspect ratio
            public Dimension getPreferredSize() {
                Dimension d = this.getParent().getSize();
                int newSize = d.width > d.height ? d.height : d.width;
                newSize = newSize == 0 ? 100 : newSize;

                return new Dimension((int) (newSize * 1.2), newSize);
            }
        };
        gridPanel.setBackground(Color.DARK_GRAY);

        gamePanel.add(gridPanel);
    }

    /**
     * 
     * Creates a side panel in the {@code ChessGameContainer}. 
     * The side panel contains all the funcional buttons to facilitate the game play.
     * 
     * @author Chay Wen Ning
     * @author Melody Koh Si Jie
     * @author Goh Shi Yi
     * @author Choo Yun Yi
     */
    public void createSidePanel() {
        sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JButton startBtn = new JButton("New Game");
        startBtn.setFocusable(false);
        startBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!gameStarted) {
                    startGame();
                } else {
                    promptNewGameConfirmation();
                }
            }
        });

        playerTurnLabel = new JLabel("Player's Turn: ");
        playerTurnLabel.setHorizontalAlignment(JLabel.LEFT);
        playerTurnLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        playerStatusLabel = new JLabel("Has played: ");
        playerStatusLabel.setHorizontalAlignment(JLabel.LEFT);
        playerStatusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        switchBtn = new JButton("Switch Turn");
        switchBtn.setFocusable(false);
        switchBtn.setEnabled(false);
        switchBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        switchBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchTurn();
            }
        });

        saveBtn = new JButton("Save Game");
        saveBtn.setFocusable(false);
        saveBtn.setEnabled(false);
        saveBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = createOrLocateFile();
                if (result == JFileChooser.APPROVE_OPTION) {
                    saveGame();
                }
            }
        });
        JButton loadBtn = new JButton("Load Game");
        loadBtn.setFocusable(false);
        loadBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        loadBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = createOrLocateFile();
                if (result == JFileChooser.APPROVE_OPTION) {
                    loadGame();
                }
            }
        });

        JButton exitBtn = new JButton("Exit Game");
        exitBtn.setFocusable(false);
        exitBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                promptExitGameConfirmation();
            }
        });

        sidePanel.add(startBtn);
        sidePanel.add(Box.createRigidArea(new Dimension(0, 30)));
        sidePanel.add(playerTurnLabel);
        sidePanel.add(playerStatusLabel);
        sidePanel.add(Box.createRigidArea(new Dimension(0, 20)));
        sidePanel.add(switchBtn);
        sidePanel.add(Box.createRigidArea(new Dimension(0, 20)));
        sidePanel.add(saveBtn);
        sidePanel.add(Box.createRigidArea(new Dimension(0, 20)));
        sidePanel.add(loadBtn);
        sidePanel.add(Box.createRigidArea(new Dimension(0, 20)));
        sidePanel.add(exitBtn);
    }

    /**
     * 
     * Calls the {@code ChessController} object to switch the player turn.
     * This method calls the {@code switchTurnAndUpdateContainer} method in the controller.
     * 
     * @see chessgame.ChessController#switchTurnAndUpdateContainer()
     * @author Chay Wen Ning
     */
    public void switchTurn() {
        chessController.switchTurnAndUpdateContainer();
    }

    /**
     * 
     * Starts a chess game.
     * 
     * @author Melody Koh Si Jie
     * @author Chay Wen Ning
     */
    public void startGame() {
        gameStarted = true;
        saveBtn.setEnabled(true);
        glassPane.repaint();

        chessController.displayCurrentPlayerTurn();
        chessController.displayCurrentPlayerStatus();
    }

    /**
     * 
     * Prompts a new game confirmation to the user.
     * If the user selects "Yes", start a new game. 
     * This method calls the {@code newGame} method in the {@code ChessController} object. 
     * 
     * @author Chay Wen Ning
     */
    public void promptNewGameConfirmation() {
        int opt = JOptionPane.showConfirmDialog(mainPanel,
                "Starting a new game will lose your current progress. Are you sure?", "New Game",
                JOptionPane.YES_NO_OPTION);
        if (opt == JOptionPane.YES_OPTION) {
            resetContainer();
            chessController.newGame();
        }
    }

    /**
     * 
     * Prompts a quit game confirmation to the user.
     * If the user selects "Yes", exit the application. 
     * 
     * @author Chay Wen Ning
     */
    public void promptExitGameConfirmation() {
        int opt = JOptionPane.showConfirmDialog(mainPanel,
                "Exiting the game will lose your current progress. Are you sure?", "New Game",
                JOptionPane.YES_NO_OPTION);
        if (opt == JOptionPane.YES_OPTION) {
            exitGame();
        }
    }

    /**
     * 
     * Exits the game. 
     * 
     * @author Choo Yun Yi
     */
    public void exitGame() {
        System.exit(0);
    }

    /**
     * 
     * Creates a {@code JFileChooser} and shows an "Open File" file chooser dialog to create or locate a file.
     * 
     * @return the return state of the file chooser on popdown:
     * <ul>
     * <li>JFileChooser.CANCEL_OPTION
     * <li>JFileChooser.APPROVE_OPTION
     * <li>JFileChooser.ERROR_OPTION if an error occurs or the
     *                  dialog is dismissed
     * </ul>
     * 
     * @author Goh Shi Yi
     * @author Choo Yun Yi
     */
    public int createOrLocateFile() {
        fc = new JFileChooser();
        return fc.showOpenDialog(null);
    }

    /**
     * 
     * Calls the {@code saveGameData} method in {@code ChessController} to save the current game progress to a {@link java.io.File File}.
     * 
     * @see chessgame.ChessController#saveGameData(File)
     * @see #loadGame()
     * @author Goh Shi Yi
     */
    public void saveGame() {
        File selectedFile = fc.getSelectedFile();
        chessController.saveGameData(selectedFile);
    }

    /**
     * 
     * Calls the {@code loadGameData} method in {@code ChessController} to restore a game progress from a saved {@link java.io.File File}.
     * 
     * @see chessgame.ChessController#loadGameData(File)
     * @see #saveGame()
     * @author Choo Yun Yi
     */
    public void loadGame() {
        File selectedFile = fc.getSelectedFile();
        if (chessController.loadGameData(selectedFile)) {
            gameStarted = true;
            saveBtn.setEnabled(true);
            glassPane.repaint();
        }
    }

    /**
     * 
     * The {@code MainPanelMouseListener} class; Implements {@link java.awt.event.MouseListener MouseListener} and {@link java.awt.event.MouseMotionListener MouseMotionListener}.
     * This class acts as a listener to the main panel in {@code ChessGameContainer}, and responds to user's mouse inputs on the chess board.
     * 
     * @author Chay Wen Ning
     * @author Melody Koh Si Jie
     */
    class MainPanelMouseListener implements MouseListener, MouseMotionListener {
        /**
         *
         * Selects a chess piece on the chess board if the piece is valid.
         * This method calls the {@code convertCoordinateAndCheckPiecePlayability} method in the {@code ChessController} to validate a chess piece.
         * 
         * @param point the {@code Point} of the mouse
         * @see chessgame.ChessController#convertCoordinateAndCheckPiecePlayability(Point)
         * @author Chay Wen Ning
         */
        public void selectPieceIfValid(Point point) {
            if (!gameStarted || chessController.currentPlayerHasPlayed()) {
                selectedPieceImage = null;
                return;
            }

            // set global mouse point
            mainPanelMousePoint = point;

            // determine the relative point at grid panel
            Point relPoint = new Point((int) (point.x - gridPanel.getLocation().getX()),
                    (int) (point.y - gridPanel.getLocation().getY()));
            // set grid panel mouse points for source and destination
            gridPanelMousePoints[0] = relPoint;
            gridPanelMousePoints[1] = relPoint;

            if (!chessController.convertCoordinateAndCheckPiecePlayability(gridPanelMousePoints[0])) {
                return;
            }

            Component selectedComp = gridPanel.getComponentAt(relPoint);
            if (selectedComp != null) {
                gridToPlay = (JLabel) selectedComp;
                gridToPlayColor = gridToPlay.getBackground();
                gridToPlayIcon = gridToPlay.getIcon();

                gridToPlay.setBackground(TO_PLAY_COLOR);
                gridToPlay.setIcon(null);

                selectedPieceImage = chessController.getSelectedPieceImage();
            }
        }

        /**
         *
         * Verifies the availability to play a chess piece and proceeds to play the piece if the move is valid.
         * This method calls the {@code convertCoordinateAndAttemptPlay(Point, Point)} method in the {@code ChessController} to validate and play a chess piece move.
         * 
         * @see chessgame.ChessController#convertCoordinateAndAttemptPlay(Point, Point)
         * @param point the {@code Point} of the mouse
         * @author Chay Wen Ning
         */
        public void playPieceIfValid(Point point) {
            if (!gameStarted || chessController.currentPlayerHasPlayed())
                return;

            // determine the relative point at grid panel
            Point relPoint = new Point((int) (point.x - gridPanel.getLocation().getX()),
                    (int) (point.y - gridPanel.getLocation().getY()));
            // set grid panel mouse points for destination
            gridPanelMousePoints[1] = relPoint;

            if (selectedGrid != null) {
                selectedGrid.setBackground(selectedGridColor);
            }

            selectedPieceImage = null;
            selectedGrid = null;

            if (gridToPlay != null) {
                gridToPlay.setIcon(gridToPlayIcon);
                gridToPlay.setBackground(gridToPlayColor);
                gridToPlay = null;
            }

            glassPane.repaint();
            chessController.convertCoordinateAndAttemptPlay(gridPanelMousePoints[0], gridPanelMousePoints[1]);
        }

        /**
         *
         * Shows whether a chess piece can be placed at the specified position.
         * This method calls the {@code convertCoordinateAndCheckPieceMove(Point, Point)} method in the {@code ChessController} to validate a chess piece move.
         * If a piece move is playable, color the selected grid {@code Color.GREEN}.
         * Else if the piece move is not playable, color the selected grid {@code Color.RED}
         * 
         * @param point the {@code Point} of the mouse
         * @see chessgame.ChessController#convertCoordinateAndCheckPieceMove(Point, Point)
         * @author Chay Wen Ning
         */
        public void showIsPositionPlacable(Point point) {
            if (!gameStarted || chessController.currentPlayerHasPlayed() || selectedPieceImage == null)
                return;

            // restore old background color
            if (selectedGrid != null) {
                selectedGrid.setBackground(selectedGridColor);
            }

            // set global mouse point
            mainPanelMousePoint = point;
            // determine the relative point at grid panel
            Point relPoint = new Point((int) (point.x - gridPanel.getLocation().getX()),
                    (int) (point.y - gridPanel.getLocation().getY()));
            // set grid panel mouse points for destination
            gridPanelMousePoints[1] = relPoint;

            Component selectedComp = gridPanel.getComponentAt(gridPanelMousePoints[1]);
            if (selectedComp != null) {
                try {
                    selectedGrid = (JLabel) selectedComp;
                    selectedGridColor = selectedGrid.getBackground();
                    playabilityColor = chessController.convertCoordinateAndCheckPieceMove(gridPanelMousePoints[0],
                            gridPanelMousePoints[1]) ? CAN_PLAY_COLOR : CANNOT_PLAY_COLOR;
                    selectedGrid.setBackground(playabilityColor);
                } catch (ClassCastException ex) {
                }
            }
        }

        // Mouse listener methods:
        
        /**
         * 
         * Handles a mouse clicked event.
         * 
         */
        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        /**
         * 
         * Handles a mouse entered event.
         * 
         */
        public void mouseEntered(MouseEvent e) {
        }

        /**
         * 
         * Handles a mouse exited event.
         * 
         */
        @Override
        public void mouseExited(MouseEvent e) {
        }

        /**
         * 
         * Handles a mouse pressed event.
         * 
         */
        @Override
        public void mousePressed(MouseEvent e) {
            selectPieceIfValid(e.getPoint());
            glassPane.repaint();
        }

        
        /**
         * 
         * Handles a mouse released event.
         * 
         */
        @Override
        public void mouseReleased(MouseEvent e) {
            playPieceIfValid(e.getPoint());
        }



        // Mouse motion listener methods:

        /**
         * 
         * Handles a mouse moved event.
         * 
         */
        @Override
        public void mouseMoved(MouseEvent e) {
        }

        /**
         * 
         * Handles a mouse dragged event.
         * 
         */
        @Override
        public void mouseDragged(MouseEvent e) {
            showIsPositionPlacable(e.getPoint());
            glassPane.repaint();
        }
    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ChessGameContainer();
            }
        });
    }
}
