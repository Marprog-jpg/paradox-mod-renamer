import java.io.File;
import java.io.IOException;

public class Main {
    protected static File dir;
    protected static File modFile;
    protected static File modDir;
    protected static String[] folderNames;;
    protected static String[] modFileNames;
    protected static FileFinder ff;
    protected static DirectoryChooser chosenDirectory;
    
    public static void main (String[] args) throws IOException{     
        new GUI();
    }
}
