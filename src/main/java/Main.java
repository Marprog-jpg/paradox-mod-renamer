import java.io.File;
import java.io.IOException;

public class Main {
    protected static File sourceDir;
    protected static File destinationDir;
    protected static File modFile;
    protected static File modDir;
    protected static String[] folderNames;
    public static String[] modFileNames;
    public static FindFiles ff;
    public static DirectoryChooser sourceDirectory;
    public static DirectoryChooser destinationDirectory;
    
    
    public static void main (String[] args) throws IOException{     
        new GUI();
    }
}
