// Coding Member: Chay Wen Ning, Melody Koh, Goh Shi Yi

package chess;

import base.Main;
import board.Board;
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

public class ChessView implements Subject{
    private Main frame;
    private int gridSize;
    private JLabel lastHighlightedGrid;
    private Color lastHighlightedGridColor;

    Set<Observer> observerList = new HashSet<>();

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

    public ChessView(Main frame) {
        this.frame = frame;
        gridSize = frame.GRID_SIZE;
    }

    // Getter
    public int getGridSize() { return gridSize; }

    @Override
    public void addObserver(Observer o) {
        observerList.add(o);
    }

    @Override
    public void notifyObservers(Event event) {
        for (Observer o: observerList) {
            o.handleEvent(event);
        }
    }

    public void updatePlayerTurnLabel(int playerTurn) {
        frame.getPlayerTurnLabel().setText("Player's Turn: " + (playerTurn + 1));
    }

    public void updatePlayerStatusLabel(boolean hasPlayed) {
        frame.getPlayerStatusLabel().setText("Has played: " + hasPlayed);
    }

    public void setSwitchButtonEnabled(boolean enabled) {
        frame.getSwitchBtn().setEnabled(enabled);
    }

    public Image getPieceImage(Piece piece) {
        if (piece == null) 
            return null;

        Image image;
        if (piece.getPieceType() == PieceType.Point) {
            image = piece.isFlipped() ? PieceImageType.PointDown.getImage(piece.getColor()) : PieceImageType.PointUp.getImage(piece.getColor());
        }
        else {
            PieceImageType pieceImageType = PieceImageType.valueOf(piece.getPieceType().toString());
            image = pieceImageType.getImage(piece.getColor());
        }

        return image.getScaledInstance(gridSize, gridSize, java.awt.Image.SCALE_SMOOTH);
    }

    public void updateGridSize() {
        gridSize = frame.getGridPanel().getComponent(0).getWidth();
    }

    public void notifyPieceSwitch() {
        JOptionPane.showMessageDialog(frame.getGridPanel(),"Time and Plus pieces will now switch!");
    }

    public boolean promptNewGame(int winnerIndex) {
        int opt = JOptionPane.showConfirmDialog(frame, "Player " + (winnerIndex + 1) + " has won. Choose \"Yes\" to start a new game, \"No\" to quit game", "Game Over", JOptionPane.YES_NO_OPTION);
        if (opt == JOptionPane.YES_OPTION) {
            frame.reset();
            notifyObservers(Event.NewGame);
            return true;
        }
        else {  
            System.exit(0);
            return false;
        }
    }

    public void addPieceIconResizer(Board board) {
        // remove exising component listener
        ComponentListener[] listeners = frame.getComponentListeners();
        if (listeners.length > 0) {
            for (ComponentListener li: listeners) {
                frame.removeComponentListener(li);
            }
        }
        // add new component listener
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentHidden(ComponentEvent e) {}
            @Override
            public void componentMoved(ComponentEvent e) {}
            @Override
            public void componentShown(ComponentEvent e) {}
    
            @Override
            public void componentResized(ComponentEvent e) {
                updateGridSize();
                updatePieceIcons(board);
            }
        });
    }

    public void loadPieceIcons(Board board) {
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
            
                if ((c + r) % 2 ==0) {
                    grid.setBackground(Color.GRAY);
                }

                gridPanel.add(grid);
            }
        }
    }

    public void updatePieceIcons(Board board) {
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

    public void highlightLastMovedPiece(Board board, Piece lastMovedPiece) {
        if (lastHighlightedGrid != null) {
            lastHighlightedGrid.setBorder(null);
            lastHighlightedGrid.setBackground(lastHighlightedGridColor);
        }

        if (lastMovedPiece == null) 
            return;

        JPanel gridPanel = frame.getGridPanel();
        JLabel grid = (JLabel) gridPanel.getComponent(lastMovedPiece.getX() + lastMovedPiece.getY() * frame.NO_OF_COLUMN);
        // grid.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.ORANGE));
        
        lastHighlightedGrid = grid;
        lastHighlightedGridColor = grid.getBackground();
        grid.setBackground(Color.ORANGE);

        
    }

    public void notifySave(boolean successful) {
        if (successful) {
          JOptionPane.showMessageDialog(frame.getGridPanel(),"Save Successfully");
        }
        else{
            JOptionPane.showMessageDialog(frame.getGridPanel(),"Error: Save Unsuccessfully");
        }
    }
}

