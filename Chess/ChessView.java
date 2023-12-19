// Coding Member: Chay Wen Ning

package Chess;

import Base.Main;
import Board.Board;
import ChessPiece.Piece;
import ChessPiece.Piece.PieceType;

import java.util.HashMap;
import java.awt.*;
import javax.swing.*;

public class ChessView {
    private Main frame;

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

    public void loadPieceIcons(Board board) {
        JPanel gridPanel = frame.getGridPanel();

        int row = board.getNoOfRow();
        int col = board.getNoOfColumn();

        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                Piece piece = board.getPieceAt(c, r);
                JLabel grid = new JLabel();
                
                grid.setOpaque(true);
                grid.setMinimumSize(new Dimension(frame.GRID_SIZE, frame.GRID_SIZE));
                grid.setPreferredSize(new Dimension(frame.GRID_SIZE, frame.GRID_SIZE));
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
                JLabel grid = (JLabel) gridPanel.getComponent(c + r * col);

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

                Image scaledImage = image.getScaledInstance(grid.getWidth(), grid.getWidth(), java.awt.Image.SCALE_SMOOTH);
                grid.setIcon(new ImageIcon(scaledImage));
            }
        }
    }
}