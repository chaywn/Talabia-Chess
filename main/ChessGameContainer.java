/**
*
* @author Chay Wen Ning
* @author Melody Koh
* @author Goh Shi Yi
* @author Choo Yun Yi
* @author Mohamed kamal

*/
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
 * The ChessGameContainer class extends JFrame
 * 
 */
public class ChessGameContainer extends JFrame {
    // Constants
    public final int NO_OF_ROW = 6;
    public final int NO_OF_COLUMN = 7;
    public final int GRID_SIZE = 50;

    private final Color toPlayColor = Color.LIGHT_GRAY;
    private final Color canPlayColor = Color.GREEN;
    private final Color cannotPlayColor = Color.RED;

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
     * The ChessGameContainer Constructor
     * 
     * @return default
     */
    ChessGameContainer() {
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
     * Get the main panel
     * 
     * @return the main panel
     */
    public JPanel getMainPanel() {
        return mainPanel;
    }

    /**
     * 
     * Get the grid panel
     * 
     * @return the grid panel
     */
    public JPanel getGridPanel() {
        return gridPanel;
    }

    /**
     * 
     * Get the glass panel
     * 
     * @return the glass panel
     */
    public JPanel getGlassPane() {
        return glassPane;
    }

    /**
     * 
     * Get the player's turn label
     * 
     * @return the player's turn label
     */
    public JLabel getPlayerTurnLabel() {
        return playerTurnLabel;
    }

    /**
     * 
     * Get the label of player status
     * 
     * @return the label of player status
     */
    public JLabel getPlayerStatusLabel() {
        return playerStatusLabel;
    }

    /**
     * 
     * Get the switch player's turn button
     * 
     * @return the switch player's turn button
     */
    public JButton getSwitchBtn() {
        return switchBtn;
    }

    /**
     * 
     * Set the image of the selected piece
     * 
     * @param image specified image in the form of Image
     */
    public void setSelectedPieceImage(Image image) {
        selectedPieceImage = image;
    }

    /**
     * 
     * Reset the container
     * 
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
     * Create glass pane
     * 
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
     * Create game panel
     * 
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
     * Create side panel and include all the funcional buttons to facilitate the
     * game play
     * 
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
     * Switch player's turn
     * 
     */
    public void switchTurn() {
        chessController.switchTurnAndUpdateContainer();
    }

    /**
     * 
     * Start the game
     * 
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
     * Show pop-up screen to confirm if the player wants to start a new game
     * 
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
     * Show pop-up screen to confirm if the player wants to quit the game
     * 
     */
    public void promptExitGameConfirmation() {
        int opt = JOptionPane.showConfirmDialog(mainPanel,
                "Exiting the game will lose your current progress. Are you sure?", "New Game",
                JOptionPane.YES_NO_OPTION);
        if (opt == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    /**
     * 
     * Show pop-up screen to choose or create file
     * 
     * @return pop-up screen to choose or create file
     */
    public int createOrLocateFile() {
        fc = new JFileChooser();
        return fc.showOpenDialog(null);
    }

    /**
     * 
     * Save the game
     * 
     */
    public void saveGame() {
        File selectedFile = fc.getSelectedFile();
        chessController.saveGameData(selectedFile);
    }

    /**
     * 
     * Load a game
     * 
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
     * MainPanelMouseListener class impements MouseListener and MouseMotionListener.
     * 
     */
    class MainPanelMouseListener implements MouseListener, MouseMotionListener {
        /**
         *
         * Verify the availability to select a chess piece
         * 
         * @param point the pointed position Point
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

            if (!chessController.ConvertCoordinateAndCheckPiecePlayability(gridPanelMousePoints[0])) {
                return;
            }

            Component selectedComp = gridPanel.getComponentAt(relPoint);
            if (selectedComp != null) {
                gridToPlay = (JLabel) selectedComp;
                gridToPlayColor = gridToPlay.getBackground();
                gridToPlayIcon = gridToPlay.getIcon();

                gridToPlay.setBackground(toPlayColor);
                gridToPlay.setIcon(null);

                selectedPieceImage = chessController.getSelectedPieceImage();
            }
        }

        /**
         *
         * Verify the availability to play a chess piece
         * 
         * @param point the pointed position Point
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
            chessController.ConvertCoordinateAndAttemptPlay(gridPanelMousePoints[0], gridPanelMousePoints[1]);
        }

        /**
         *
         * Show whether a piece can be placed at the specified position
         * 
         * @param point the pointed position Point
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
                            gridPanelMousePoints[1]) ? canPlayColor : cannotPlayColor;
                    selectedGrid.setBackground(playabilityColor);
                } catch (ClassCastException ex) {
                }
            }
        }

        // Mouse listener methods:
        @Override
        /**
         * 
         * When mouse is clicked
         * 
         */
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        /**
         * 
         * When mouse entered
         * 
         */
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        /**
         * 
         * When mouse exited
         * 
         */
        public void mouseExited(MouseEvent e) {
        }

        @Override
        /**
         * 
         * When mouse is pressed
         * 
         */
        public void mousePressed(MouseEvent e) {
            selectPieceIfValid(e.getPoint());
            glassPane.repaint();
        }

        @Override
        /**
         * 
         * When mouse is released
         * 
         */
        public void mouseReleased(MouseEvent e) {
            playPieceIfValid(e.getPoint());
        }

        // Mouse motion listener methods:
        @Override
        /**
         * 
         * When mouse is moved
         * 
         */
        public void mouseMoved(MouseEvent e) {
        }

        @Override
        /**
         * 
         * When mouse is dragged
         * 
         */
        public void mouseDragged(MouseEvent e) {
            showIsPositionPlacable(e.getPoint());
            glassPane.repaint();
        }
    }

        /**
         * 
         * Main method to run the game.
         * 
         */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ChessGameContainer();
            }
        });
    }
}
