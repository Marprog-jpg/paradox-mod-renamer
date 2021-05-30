import java.io.File;
import java.io.IOException;

public class Main {
    protected static File dir;
    protected static File modFile;
    protected static File modDir;
    protected static String[] folderNames;;
    public static String[] modFileNames;
    public static FileFinder ff;
    public static DirectoryChooser chosenDirectory;
    
    public static void main (String[] args) throws IOException{     
        new GUI();
    }
}
