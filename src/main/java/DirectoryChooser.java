import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class DirectoryChooser extends JPanel implements ActionListener {
    JButton go;

    JFileChooser chooser;
    String chooserTitle;
    int targetFolder; //0 if source folder, 1 if destination folder


    public DirectoryChooser(int i) {
        targetFolder = i;
        if(targetFolder == 0){
            go = new JButton("Select Source Directory");
        }else if(targetFolder == 1){
            go = new JButton("Select Destination Directory");
        }

        go.addActionListener(this);
        add(go);
    }

    public void actionPerformed(ActionEvent e) {
        chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle(chooserTitle);
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);

        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            if(targetFolder == 0){
                SwingGUI.sourceField.setText(Main.sourceDirectory.getSelectedFolder());


            }else if(targetFolder == 1){
                SwingGUI.destinationField.setText(Main.destinationDirectory.getSelectedFolder());
            }

            Main.numberOfChosenFolders++;

            if(Main.numberOfChosenFolders == 2){
                Main.numberOfChosenFolders = 0;
                SwingGUI.button.setEnabled(true);
            }
        }
        else {
            System.out.println("No Selection ");
        }
    }
    public String getSelectedFolder(){
        return chooser.getSelectedFile().getAbsolutePath();
    }

    public Dimension getPreferredSize(){
        return new Dimension(200, 200);
    }

}