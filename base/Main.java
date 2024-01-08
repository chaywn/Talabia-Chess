// Coding Member: Chay Wen Ning, Melody Koh, Goh Shi Yi

package base;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import chess.Chess;
import chess.ChessController;
import chess.ChessView;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;

public class Main extends JFrame {
    // Constants
    public final int NO_OF_ROW = 6;
    public final int NO_OF_COLUMN = 7;
    public final int GRID_SIZE = 50;

    private final Color toPlayColor = Color.LIGHT_GRAY;
    private final Color canPlayColor = Color.GREEN;
    private final Color cannotPlayColor = Color.RED;

    private ChessController chessController;

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

    Main() {
        // Initialize chess components
        chessController = new ChessController(new Chess(), new ChessView(this));

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

    public JPanel getMaiPanel() {
        return mainPanel;
    }

    public JPanel getGridPanel() {
        return gridPanel;
    }

    public JPanel getGlassPane() {
        return glassPane;
    }

    public JLabel getPlayerTurnLabel() {
        return playerTurnLabel;
    }

    public JLabel getPlayerStatusLabel() {
        return playerStatusLabel;
    }

    public JButton getSwitchBtn() {
        return switchBtn;
    }

    public void setSelectedPieceImage(Image image) {
        selectedPieceImage = image;
    }

    public void reset() {
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
                    gameStarted = true;
                    saveBtn.setEnabled(true);
                    glassPane.repaint();

                    chessController.viewUpdatePlayerTurn();
                    chessController.viewUpdatePlayerStatus();
                } else {
                    int opt = JOptionPane.showConfirmDialog(mainPanel,
                            "Starting a new game will lose your current progress. Are you sure?", "New Game",
                            JOptionPane.YES_NO_OPTION);
                    if (opt == JOptionPane.YES_OPTION) {
                        reset();
                        chessController.newGame();
                    }
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
                if (!gameStarted)
                    return;
                chessController.switchPlayerTurn();
            }
        });

        saveBtn = new JButton("Save Game");
        saveBtn.setFocusable(false);
        saveBtn.setEnabled(false);
        saveBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                int result = fc.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fc.getSelectedFile();
                    chessController.saveGame(selectedFile);
                }
            }
        });
        JButton loadBtn = new JButton("Load Game");
        loadBtn.setFocusable(false);
        loadBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        loadBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // to be added
            }
        });

        JButton exitBtn = new JButton("Exit Game");
        exitBtn.setFocusable(false);
        exitBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int opt = JOptionPane.showConfirmDialog(mainPanel,
                        "Exiting the game will lose your current progress. Are you sure?", "New Game",
                        JOptionPane.YES_NO_OPTION);
                if (opt == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
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

    class MainPanelMouseListener implements MouseListener, MouseMotionListener {
        // Mouse listener methods:
        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (!gameStarted || chessController.currentPlayerHasPlayed()) {
                selectedPieceImage = null;
                return;
            }

            // set global mouse point
            mainPanelMousePoint = e.getPoint();

            // determine the relative point at grid panel
            Point relPoint = new Point((int) (e.getPoint().x - gridPanel.getLocation().getX()),
                    (int) (e.getPoint().y - gridPanel.getLocation().getY()));
            // set grid panel mouse points for source and destination
            gridPanelMousePoints[0] = relPoint;
            gridPanelMousePoints[1] = relPoint;

            if (!chessController.checkPiecePlayability(gridPanelMousePoints[0]))
                return;

            Component selectedComp = gridPanel.getComponentAt(relPoint);
            if (selectedComp != null) {
                gridToPlay = (JLabel) selectedComp;
                gridToPlayColor = gridToPlay.getBackground();
                gridToPlayIcon = gridToPlay.getIcon();

                gridToPlay.setBackground(toPlayColor);
                gridToPlay.setIcon(null);

                selectedPieceImage = chessController.getSelectedPieceImage(gridPanelMousePoints[0]);
            }

            glassPane.repaint();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (!gameStarted || chessController.currentPlayerHasPlayed())
                return;

            // determine the relative point at grid panel
            Point relPoint = new Point((int) (e.getPoint().x - gridPanel.getLocation().getX()),
                    (int) (e.getPoint().y - gridPanel.getLocation().getY()));
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
            chessController.relayPiecePointToPlay(gridPanelMousePoints[0], gridPanelMousePoints[1]);
        }

        // Mouse motion listener methods:
        @Override
        public void mouseMoved(MouseEvent e) {
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if (!gameStarted || chessController.currentPlayerHasPlayed() || selectedPieceImage == null)
                return;

            // restore old background color
            if (selectedGrid != null) {
                selectedGrid.setBackground(selectedGridColor);
            }

            // set global mouse point
            mainPanelMousePoint = e.getPoint();
            // determine the relative point at grid panel
            Point relPoint = new Point((int) (e.getPoint().x - gridPanel.getLocation().getX()),
                    (int) (e.getPoint().y - gridPanel.getLocation().getY()));
            // set grid panel mouse points for source and destination
            gridPanelMousePoints[1] = relPoint;

            chessController.checkPieceMove(gridPanelMousePoints[0], gridPanelMousePoints[1]);

            Component selectedComp = gridPanel.getComponentAt(gridPanelMousePoints[1]);
            if (selectedComp != null) {
                try {
                    selectedGrid = (JLabel) selectedComp;
                    selectedGridColor = selectedGrid.getBackground();
                    Color playabilityColor = chessController.checkPieceMove(gridPanelMousePoints[0],
                            gridPanelMousePoints[1]) ? canPlayColor : cannotPlayColor;
                    selectedGrid.setBackground(playabilityColor);
                } catch (ClassCastException ev) {
                }
            }

            glassPane.repaint();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Main();
            }
        });
    }
}
