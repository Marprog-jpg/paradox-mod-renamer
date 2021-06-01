import com.formdev.flatlaf.FlatDarkLaf;
import java.io.File;
import java.io.IOException;
import javax.swing.UIManager;

public class Main {
    protected static File sourceDir;
    protected static File destinationDir;
    protected static File modFile;
    protected static File modDir;
    protected static String[] folderNames;;
    public static String[] modFileNames;
    public static FindFiles ff;
    public static DirectoryChooser sourceDirectory;
    public static DirectoryChooser destinationDirectory;
    protected static int numberOfChosenFolders = 0;

    public static void main (String[] args) throws IOException{
        try {
            UIManager.setLookAndFeel( new FlatDarkLaf() );
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize LaF" );
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                SwingGUI frame = new SwingGUI();
                frame.setTitle("Paradox Mod Renamer");
                frame.setVisible(true);
            }
        });
    }
}
