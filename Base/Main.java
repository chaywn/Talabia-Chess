// Coding Member: Chay Wen Ning, Melody Koh

package Base;

import Chess.Chess;
import Chess.ChessController;
import Chess.ChessView;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class Main extends JFrame{
    // Constants
    public final int NO_OF_ROW = 6;
    public final int NO_OF_COLUMN = 7;
    public static int GRID_SIZE = 50;

    // Chess MVC components
    private Chess chess;
    private ChessView chessView;
    ChessController chessController;
    
    public JPanel mainPanel;
    public JPanel gamePanel;
    public static JPanel gridPanel;
    public JPanel sidePanel;
    public static int WIDTH = 800;
    public static int HEIGHT = 600;
    public static int initialX;
    public static int initialY;

    public JPanel getGridPanel() {
        return gridPanel;
    }

    public JLabel getPlayerTurnLabel() {
        return (JLabel) sidePanel.getComponent(0);
    }

    Main() {
        // Initialize chess components
        chess = new Chess();
        chessView = new ChessView(this);
        chessController = new ChessController(chess, chessView);
    


        // Initialize panels
        mainPanel = new JPanel(new BorderLayout());
        gamePanel = new JPanel(new GridBagLayout());
        sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.setBorder(new EmptyBorder(20, 20, 20, 20));


        // Game Panel
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
        chessController.viewLoadPieceIcons();

        gamePanel.setBackground(Color.DARK_GRAY);
        gamePanel.add(gridPanel);
        


        // Side Panel
        JLabel playerTurnLabel = new JLabel("Player's Turn: 1");
        playerTurnLabel.setHorizontalAlignment(JLabel.LEFT);
        playerTurnLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton switchBtn = new JButton("Switch Turn (for testing purposes)");
        switchBtn.setFocusable(false);
        switchBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        switchBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chessController.modelSwitchTurn();
            }
        }); 
        JButton exitBtn = new JButton("Exit Game");
        exitBtn.setFocusable(false);
        exitBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        sidePanel.add(playerTurnLabel);
        sidePanel.add(Box.createRigidArea(new Dimension(0, 20)));
        sidePanel.add(switchBtn);
        sidePanel.add(Box.createRigidArea(new Dimension(0, 20)));
        sidePanel.add(exitBtn);

        mainPanel.add(gamePanel, BorderLayout.CENTER);
        mainPanel.add(sidePanel, BorderLayout.EAST);

        gridPanel.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                // if (selectedPiece != null) {
                    // this part is not working, trying to figure it out
                    // selectedPiece.setX(e.getX() - (ChessView.getGridWidth()/2));
                    // selectedPiece.setY(e.getY() - (ChessView.getGridHeight()/2));
                //     repaint();
                // }
            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }
        });

        chessView.movePieces(chessController);
        
        add(mainPanel);
        addComponentListener(new ResizeComponentListener());

        pack(); 
        setVisible(true);
        setMinimumSize(new Dimension(NO_OF_COLUMN * GRID_SIZE + sidePanel.getWidth() + 138, NO_OF_ROW * GRID_SIZE + 120));
        setDefaultCloseOperation(EXIT_ON_CLOSE);      
    }

    private class ResizeComponentListener extends ComponentAdapter {
            public void componentHidden(ComponentEvent e) {}
            public void componentMoved(ComponentEvent e) {}
            public void componentShown(ComponentEvent e) {}

            @Override
            public void componentResized(ComponentEvent e) {
                chessController.viewUpdatePieceIcons();
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