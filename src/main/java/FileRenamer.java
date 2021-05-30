import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileRenamer {
    public static void findAndRenameFiles(){
    
        Main.ff = new FileFinder();
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
                FileModifier.removeLineFromFile(Main.modFile, i);
            } catch (IOException ex) {
                System.out.println("exception in lsitener");
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            FileModifier.addTextToFile(Main.modFile, i);
        }
        
        GUI.label.setText("Status: Good to go!");
    }
}
