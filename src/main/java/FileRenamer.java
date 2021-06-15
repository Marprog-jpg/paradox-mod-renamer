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
    protected static int folderStructure; //0 if there is no weird subdirectory, 1 if there is a weird subdirectory
    protected static File modFolderSubdirectory;

    public static void findAndRenameFiles() throws IOException {

        Main.ff = new FindFiles();

        Main.folderNames = Main.ff.convertFileToString(Main.ff.folders);
        for (int i = 0; i < Main.folderNames.length; i++) {
            Main.modDir = new File(Main.sourceDir.getAbsolutePath() + File.separator + Main.folderNames[i]);
            Main.ff.findFiles();
            Main.modFileNames = Main.ff.convertFileToString(Main.ff.modFiles);

            Main.modFile = new File(Main.modDir.getAbsolutePath() + File.separator + Main.modFileNames[0]);

            renameFiles(i);

            SwingGUI.programStatus.setText("Status: Good to go!");

        }
    }

    public static void renameFiles(int i) throws IOException {
        folderStructure = FolderStructureChecker.checkFolderStructure();
        Path myPath = Paths.get(Main.modFile.getAbsolutePath());
        Path modFolderPath = Paths.get(Main.modFile.getParent() + File.separator + FolderStructureChecker.subdirectoryFolderNames[0]);

        try {
            Files.move(myPath, myPath.resolveSibling(Main.folderNames[i] + ".mod"),REPLACE_EXISTING);
        } catch (IOException ex) {
            Logger.getLogger(SwingGUI.class.getName()).log(Level.SEVERE, null, ex);
        }

        Main.modFile = new File(Main.modDir.getAbsolutePath() + File.separator + Main.folderNames[i] + ".mod");

        try {
            FileModifier.removeLineFromFile(Main.modFile);
        } catch (IOException ex) {
            Logger.getLogger(SwingGUI.class.getName()).log(Level.SEVERE, null, ex);
        }

        FileModifier.addTextToFile();

        if(folderStructure == 0){
            moveFolderStructureNoSubdir();
        }else if(folderStructure == 1){
            moveFolderStructureWithSubdir(i, modFolderPath);
        }

    }

    public static void moveFolderStructureNoSubdir() throws IOException{
        //move file to destination folder
        FileUtils.moveFileToDirectory(Main.modFile, Main.destinationDir, true);
        FileUtils.moveDirectoryToDirectory(Main.modFile.getParentFile(), Main.destinationDir, true);
        //end move file to destination folder
    }

    public static void moveFolderStructureWithSubdir(int i, Path modFolderPath) throws IOException{
        //change folder name
        try {
            Files.move(modFolderPath, modFolderPath.resolveSibling(Main.folderNames[i]),REPLACE_EXISTING);
        } catch (IOException ex) {
            Logger.getLogger(SwingGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        Main.modFile = new File(Main.modDir.getAbsolutePath() + File.separator + Main.folderNames[i] + ".mod");

        modFolderSubdirectory = new File(Main.modDir.getAbsolutePath() + File.separator + Main.folderNames[i]);
        //end change folder name

        //move file to destination folder
        FileUtils.moveFileToDirectory(Main.modFile, Main.destinationDir, true);
        FileUtils.moveDirectoryToDirectory(modFolderSubdirectory, Main.destinationDir, true);
        //end move file to destination folder

        FileUtils.deleteDirectory(new File(modFolderSubdirectory.getParent())); //delete leftover folder

    }
}
