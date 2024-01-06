import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Test extends JFrame {
    private BufferedImage image;
    private Point mousePoint;

    public Test() {
        try {
            // Load your image from a file
            image = ImageIO.read(new File("Icons/BluePieces/Point(up).png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Set up JFrame
        setTitle("Image Drawer");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add mouse listener
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mousePoint = e.getPoint();
                repaint();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                mousePoint = e.getPoint();
                repaint();
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (image != null && mousePoint != null) {
            // Draw the image at the mouse position
            g.drawImage(image, mousePoint.x, mousePoint.y, this);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Test imageDrawer = new Test();
            imageDrawer.setVisible(true);
        });
    }
}
