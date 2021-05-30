import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;

public class FileRenamer {
    protected static String modFileWithoutExtension;
    protected static int folderStructure; //0 if there is no weird subdirectory, 1 if there is a weird subdirectory
    protected static File modFolderSubdirectory;
    
    public static void findAndRenameFiles() throws IOException{
    
        Main.ff = new FindFiles();

        Main.folderNames = Main.ff.convertFileToString(Main.ff.folders);
        for(int i = 0; i < Main.folderNames.length;i++){
            Main.modDir = new File(Main.sourceDir.getAbsolutePath() + File.separator + Main.folderNames[i]);
            Main.ff.findFiles();
            Main.modFileNames = Main.ff.convertFileToString(Main.ff.modFiles);

            Main.modFile = new File(Main.modDir.getAbsolutePath() + File.separator + Main.modFileNames[0]);
            
            folderStructure = FolderStructureChecker.checkFolderStructure();
            
            if(folderStructure == 0){
                folderStructureNoSubdir(i);
            }else if(folderStructure == 1){
                folderStructureWithSubdir(i);
            }            
        }
        
        GUI.label.setText("Status: Good to go!");
        GUI.button.setEnabled(false);
    }
    
    public static void folderStructureNoSubdir(int i) throws IOException{
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
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }

        FileModifier.addTextToFile(Main.modFile, i);
        
        //move file to destination folder
        
        FileUtils.moveFileToDirectory(Main.modFile, Main.destinationDir, true);
        FileUtils.moveDirectoryToDirectory(Main.modFile.getParentFile(), Main.destinationDir, true);

        //end move file to destination folder
    }
    
    
    public static void folderStructureWithSubdir(int i) throws IOException{
        Path modFilePath = Paths.get(Main.modFile.getAbsolutePath());
        Path modFolderPath = Paths.get(Main.modFile.getParent() + File.separator + FolderStructureChecker.subdirectoryFolderNames[0]);

        
        //change file name
        try {
            Files.move(modFilePath, modFilePath.resolveSibling(Main.folderNames[i] + ".mod"),REPLACE_EXISTING);
        } catch (IOException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        Main.modFile = new File(Main.modDir.getAbsolutePath() + File.separator + Main.folderNames[i] + ".mod");
        //end change file name

        
        //modify file content
        try {
            FileModifier.removeLineFromFile(Main.modFile, i);
        } catch (IOException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        FileModifier.addTextToFile(Main.modFile, i);      
        //end modify file content
        
        //change folder name
        
        try {
            Files.move(modFolderPath, modFolderPath.resolveSibling(Main.folderNames[i]),REPLACE_EXISTING);
        } catch (IOException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        Main.modFile = new File(Main.modDir.getAbsolutePath() + File.separator + Main.folderNames[i] + ".mod");
        
        modFolderSubdirectory = new File(Main.modDir.getAbsolutePath() + File.separator + Main.folderNames[i]);
        
        //end change folder name
        
        //move file to destination folder
        
        FileUtils.moveFileToDirectory(Main.modFile, Main.destinationDir, true);
        FileUtils.moveDirectoryToDirectory(modFolderSubdirectory, Main.destinationDir, true);

        //end move file to destination folder

                  
    }
}
