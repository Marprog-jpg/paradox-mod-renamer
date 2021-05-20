import java.io.File;
import java.io.IOException;

public class Main {
    public static File dir;
    public static File modFile;
    public static File modDir;
    public static String[] folderNames;;
    public static String[] modFileNames;
    public static fileFinder ff;
    
    public static void main (String[] args) throws IOException{
        dir = new File("D:\\Stuff\\User Folders\\Documents\\NetBeansProjects\\renameFilesParadoxGames\\testingFiles");
        new GUI();
    }
}
