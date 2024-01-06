// Coding Member: Chay Wen Ning, Melody Koh, Goh Shi Yi

package Base;

import Chess.Chess;
import Chess.ChessController;
import Chess.ChessView;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

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
    private Image selectedPieceImage;


    private Point[] mouseDragPosition = new Point[2];  // record source and destination points

    Main() {
        // Initialize chess components
        chessController = new ChessController(new Chess(), new ChessView(this));

        // Initialize panels
        mainPanel = new JPanel(new BorderLayout());
        gamePanel = new JPanel(new GridBagLayout());
        sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Game Panel
        createGamePanel();

        // Side Panel
        createSidePanel();


        chessController.viewLoadPieceIcons();
        chessController.viewAddPieceIconResizer();


        // Glass Pane
        // * for gray screen before game starts and for chess piece dragging
        glassPane = new JPanel(){
            public void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                if (!gameStarted) {
                    g.setColor(new Color(0,0,0,140));
                    g.fillRect(0,0, gamePanel.getWidth(), gamePanel.getHeight());
                    return;
                }

                if (selectedPieceImage != null) {
                    Point p = MouseInfo.getPointerInfo().getLocation();
                    g.fillOval((int) p.getX(), (int) p.getY(), 50, 50);
                    g.fillRect(0, 0, 50, 50);
                    g.fillRect((int) mouseDragPosition[1].getX(), (int) mouseDragPosition[1].getY(), selectedPieceImage.getWidth(Main.this), selectedPieceImage.getHeight(Main.this));
                    g.drawImage(selectedPieceImage, (int) mouseDragPosition[1].getX() , (int) mouseDragPosition[1].getY() , Main.this);
                }
            }
        };		
        glassPane.setOpaque(false);

        mainPanel.add(gamePanel, BorderLayout.CENTER);
        mainPanel.add(sidePanel, BorderLayout.EAST);

        add(mainPanel);
        setGlassPane(glassPane);
        glassPane.setVisible(true);

        pack();
        setVisible(true);
        setMinimumSize(
                new Dimension(NO_OF_COLUMN * GRID_SIZE + sidePanel.getWidth() + 138, NO_OF_ROW * GRID_SIZE + 120));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public JPanel getMaiPanel() { return mainPanel; }
    public JPanel getGridPanel() { return gridPanel; }
    public JPanel getGlassPane() { return glassPane; }
    public JLabel getPlayerTurnLabel() { return playerTurnLabel; }
    public JLabel getPlayerStatusLabel() { return playerStatusLabel; }
    public JButton getSwitchBtn() { return switchBtn; }


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

    public void createGamePanel() {
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
        gridPanel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}

            @Override
            public void mousePressed(MouseEvent e) {
                if (!gameStarted || chessController.currentPlayerHasPlayed()) {
                    selectedPieceImage = null;
                    return;
                }
                    
                // set position (source)
                mouseDragPosition[0] = e.getPoint();

                // set position (destination)
                mouseDragPosition[1] = e.getPoint();

                if (!chessController.checkPiecePlayability(mouseDragPosition[0])) 
                    return;

                gridToPlay = (JLabel) gridPanel.getComponentAt(e.getPoint());
                gridToPlayColor = gridToPlay.getBackground();
                gridToPlayIcon = gridToPlay.getIcon();

                gridToPlay.setBackground(toPlayColor);
                gridToPlay.setIcon(null);

                selectedPieceImage = chessController.getSelectedPieceImage(mouseDragPosition[0]);  
                glassPane.repaint();
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                if (!gameStarted || chessController.currentPlayerHasPlayed()) 
                    return;

                // set destination
                mouseDragPosition[1] = e.getPoint();
                
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
                chessController.relayPiecePositionToPlay(mouseDragPosition);
            }
        });
        gridPanel.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (!gameStarted || chessController.currentPlayerHasPlayed() || selectedPieceImage == null) 
                    return;

                // restore old background color
                if (selectedGrid != null) {
                    selectedGrid.setBackground(selectedGridColor);
                }

                // Set destination as current mouse location
                mouseDragPosition[1] = e.getPoint();

                // set new selected grid and its background color
                Point point = e.getPoint();
                Point pointAdjust = new Point((int) (point.getX() + 2), (int) point.getY());
                Component selectedComp = gridPanel.getComponentAt(pointAdjust);

                if (selectedComp != null) {
                    try {
                        selectedGrid = (JLabel) selectedComp;
                        selectedGridColor = selectedGrid.getBackground();

                        Color playabilityColor = chessController.checkPiecePlayability(mouseDragPosition[0], mouseDragPosition[1]) ? canPlayColor : cannotPlayColor;
                        selectedGrid.setBackground(playabilityColor);
                    }
                    catch (ClassCastException ev) {}
                }
            
                glassPane.repaint();
            }

            @Override
            public void mouseMoved(MouseEvent e) {}
        });

        gamePanel.add(gridPanel);
    }

    public void createSidePanel() {
        JButton startBtn = new JButton("New Game");
        startBtn.setFocusable(false);
        startBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!gameStarted) {
                    gameStarted=true;
                    saveBtn.setEnabled(true);
                    glassPane.repaint();

                    chessController.viewUpdatePlayerTurn();
                    chessController.viewUpdatePlayerStatus();
                }
                else {
                    int opt = JOptionPane.showConfirmDialog(mainPanel, "Starting a new game will lose your current progress. Are you sure?", "New Game", JOptionPane.YES_NO_OPTION);
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
                if (!gameStarted) 
                    return;
                //to be added
            }
        });
        JButton loadBtn = new JButton("Load Game");
        loadBtn.setFocusable(false);
        loadBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        loadBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //to be added
            }
        });

        JButton exitBtn = new JButton("Exit Game");
        exitBtn.setFocusable(false);
        exitBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int opt = JOptionPane.showConfirmDialog(mainPanel, "Exiting the game will lose your current progress. Are you sure?", "New Game", JOptionPane.YES_NO_OPTION);
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Main();
            }
        });
    }
}