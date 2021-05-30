import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GUI implements ActionListener{
    
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
        Main.chosenDirectory = new DirectoryChooser();
        frame.add(Main.chosenDirectory, BorderLayout.CENTER);
        frame.setSize(Main.chosenDirectory.getPreferredSize());
        //choose directory button end
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("GUI");
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {    
        FileRenamer.findAndRenameFiles();
    }
    
}
