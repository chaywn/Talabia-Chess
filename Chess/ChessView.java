// Coding Member: Chay Wen Ning, Melody Koh

package Chess;

import Base.Main;
import Board.Board;
import ChessPiece.Piece;
import ChessPiece.Piece.PieceType;
import Player.Player;

import java.util.HashMap;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ChessView {
    private Main frame;
    private static Piece selectedPiece;
    private static JLabel grid = new JLabel();

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
    }

    public void updatePlayerTurnLabel(int playerTurn) {
        frame.getPlayerTurnLabel().setText("Player's Turn: " + (playerTurn + 1));;
    }

    public void movePieces (ChessController chessController) {
        Main.gridPanel.addMouseListener(new MouseListener() {
            Board board = chessController.getModelBoard();

            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                selectedPiece = board.getPieceAt(e.getX()/getGridWidth(), e.getY()/getGridHeight());
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                Player p = chessController.getModelPlayer(chessController.getModelPlayerTurn());
                selectedPiece.movePiece(board, p, e.getX()/getGridWidth(), e.getY()/getGridHeight());
                if ((p.getPlayCount(0) == 2) && (p.getPlayCount(1) == 2)) {
                    chessController.modelSwitchTimePlusPiece();
                    p.setPlayCount(0, 0);
                    p.setPlayCount(1, 0);
                } 
                chessController.viewUpdatePieceIcons();
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

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
                grid = new JLabel();
                
                grid.setOpaque(true);
                setGridWidth(grid.getWidth());
                setGridHeight(grid.getHeight());
                grid.setHorizontalAlignment(JLabel.CENTER);

                if (piece != null) {
                    Image image;

                    if (piece.getPieceType() == PieceType.Point) {
                        image = piece.isFlipped() ? PieceImageType.PointDown.getImage(piece.getColor()) : PieceImageType.PointUp.getImage(piece.getColor());
                    }
                    else {
                        PieceImageType pieceImageType = PieceImageType.valueOf(piece.getPieceType().toString());
                        image = pieceImageType.getImage(piece.getColor());
                    }

                    Image scaledImage = image.getScaledInstance(frame.GRID_SIZE, frame.GRID_SIZE, java.awt.Image.SCALE_SMOOTH);
                    grid.setIcon(new ImageIcon(scaledImage));
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
                grid = (JLabel) gridPanel.getComponent(c + r * col);

                if (piece == null) {
                    if (grid.getIcon() != null) {
                        grid.setIcon(null);
                    }
                    continue;
                } 

                Image image;

                if (piece.getPieceType() == PieceType.Point) {
                    image = piece.isFlipped() ? PieceImageType.PointDown.getImage(piece.getColor()) : PieceImageType.PointUp.getImage(piece.getColor());
                }
                else {
                    PieceImageType pieceImageType = PieceImageType.valueOf(piece.getPieceType().toString());
                    image = pieceImageType.getImage(piece.getColor());
                }

                Image scaledImage = image.getScaledInstance(getGridWidth(), getGridHeight(), java.awt.Image.SCALE_SMOOTH);
                grid.setIcon(new ImageIcon(scaledImage));
                piece.setX(c);
                piece.setY(r);
            }
        }
    }

    public static int getGridWidth() {
        return grid.getWidth();
    }

    public static int getGridHeight() {
        return grid.getHeight();
    }

    public static void setGridWidth(int width) {
        grid.setPreferredSize(new Dimension(width, grid.getHeight()));
    }

    public static void setGridHeight(int height) {
        grid.setPreferredSize(new Dimension(grid.getWidth(), height));
    }
}