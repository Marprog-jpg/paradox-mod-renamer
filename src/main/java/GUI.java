import java.nio.file.Files;

import java.io.*;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GUI implements ActionListener{
    
    private JLabel label;
    private JFrame frame;
    private JPanel panel;
    public static JButton button;
    
    public GUI(){
        frame = new JFrame();
        
        button = new JButton("DO IT");
        button.setEnabled(false);
        button.addActionListener(this);
        //button.addActionListneer(this);
        
        label = new JLabel("Status: Nothing renamed");
        
        
        panel = new JPanel();

        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10 ,30));
        panel.setLayout(new GridLayout(0, 1));
        panel.add(button);
        panel.add(label);
        
        frame.add(panel, BorderLayout.SOUTH);
        //choose directory button
        Main.chosenDirectory = new ChooseDirectory();
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
        
        
        
        Main.ff = new fileFinder();
        Main.folderNames = Main.ff.convertFileToString(Main.ff.folders);
        
        for(int i = 0; i < Main.folderNames.length;i++){
            Main.modDir = new File(Main.dir.getAbsolutePath() + File.separator + Main.folderNames[i]);
            Main.ff.findFiles();
            Main.modFileNames = Main.ff.convertFileToString(Main.ff.modFiles);

            Main.modFile = new File(Main.modDir.getAbsolutePath() + File.separator + Main.modFileNames[0]);

            Path myPath = Paths.get(Main.modFile.getAbsolutePath());

            try {
                Files.move(myPath, myPath.resolveSibling(Main.folderNames[i] + ".mod"),REPLACE_EXISTING);
            } catch (IOException ex) {
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            Main.modFile = new File(Main.modDir.getAbsolutePath() + File.separator + Main.folderNames[i] + ".mod");

            try {
                removeLineFromFile(Main.modFile, i);
            } catch (IOException ex) {
                System.out.println("exception in lsitener");
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            addTextToFile(Main.modFile, i);
        }
        
        label.setText("Status: Good to go!");
        
       
    }
    public void removeLineFromFile(File inputFile, int i) throws FileNotFoundException, IOException{

        File tempFile = new File(inputFile.getParent() + File.separator + "myTempFile.mod");

        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        String lineToRemove = "path=";
        String currentLine;

        while((currentLine = reader.readLine()) != null) {
            // trim newline when comparing with lineToRemove
            String trimmedLine = currentLine.trim();
            if(trimmedLine.contains(lineToRemove)) continue;
            writer.write(currentLine + System.getProperty("line.separator"));
        }
        writer.close(); 
        reader.close(); 
        
        inputFile.delete();
        boolean successful = tempFile.renameTo(inputFile);
        //System.out.println(successful);
    }
    
    public void addTextToFile(File inputFile, int i){
        String fileNameWithOutExt = Main.modFile.getName().replaceFirst("[.][^.]+$", "");
        Path myPath = Paths.get(Main.modFile.getAbsolutePath());
        try {
            Files.write(Paths.get(Main.modFile.getAbsolutePath()), ("path=\"mod/" + fileNameWithOutExt + "\"").getBytes(), StandardOpenOption.APPEND);
        }catch (IOException e) {
            //exception handling left as an exercise for the reader
}
    }
    

}
