
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.File;
import java.util.*;


public class ChooseDirectory extends JPanel implements ActionListener {
   
   public File fileName;
   JButton go;

   JFileChooser chooser;
   String choosertitle;

  public ChooseDirectory() {
    go = new JButton("Select Directory");
    go.addActionListener(this);
    add(go);
   }

  public void actionPerformed(ActionEvent e) {            
    chooser = new JFileChooser(); 
    chooser.setCurrentDirectory(new java.io.File("."));
    chooser.setDialogTitle(choosertitle);
    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    //
    // disable the "All files" option.
    //
    chooser.setAcceptAllFileFilterUsed(false);
    //    
    if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { 
        Main.dir = new File(Main.chosenDirectory.getSelectedFolder());
        GUI.button.setEnabled(true);
        System.out.println(Main.dir.getAbsolutePath());
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