import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GUI implements ActionListener{
    protected static int numberOfChosenFolders = 0;
    protected static JLabel label;
    private JFrame frame;
    private JPanel panel;
    protected static JButton button;
    
    public GUI(){
        frame = new JFrame();
        
        button = new JButton("DO IT");
        button.setEnabled(false);
        button.addActionListener(this);
        
        label = new JLabel("Status: Nothing renamed");
        
        panel = new JPanel();

        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10 ,30));
        panel.setLayout(new GridLayout(0, 1));
        panel.add(button);
        panel.add(label);
        
        frame.add(panel, BorderLayout.SOUTH);
        
        //choose directory button
        Main.sourceDirectory = new DirectoryChooser(0);
        frame.add(Main.sourceDirectory, BorderLayout.NORTH);
        frame.setSize(Main.sourceDirectory.getPreferredSize());
        //choose directory button end
        
        //choose destination directory
        Main.destinationDirectory = new DirectoryChooser(1);
        frame.add(Main.destinationDirectory, BorderLayout.EAST);
        frame.setSize(Main.destinationDirectory.getPreferredSize());
        //end choose destination directory

        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("GUI");
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {    
        try {
            FileRenamer.findAndRenameFiles();
        } catch (IOException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
