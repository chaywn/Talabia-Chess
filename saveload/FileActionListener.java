package saveload;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class FileActionListener implements ActionListener{
    @Override
    public void actionPerformed(java.awt.event.ActionEvent e) {
        JFileChooser fc = new JFileChooser();
        int result = fc.showOpenDialog(null); 
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fc.getSelectedFile();
            saveGame(selectedFile);
        }
    }

    private void saveGame(File file) {
        try {
            FileWriter writer = new FileWriter(file);
            writer.write("Something");
            writer.close();
            JOptionPane.showMessageDialog(null, "Game saved successfully!");
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: Game saved unsuccessfully.");
        }
    }
}
