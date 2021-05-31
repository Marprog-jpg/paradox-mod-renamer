import java.io.File;
import java.io.FilenameFilter;

public class FindFiles{
    
    public File[] modFiles;
    public File[] directoryInDirectory;
    
    File[] folders = Main.sourceDir.listFiles(new FilenameFilter() {
        public boolean accept(File dir, String name) {
            return new File(dir, name).isDirectory();
        }
    });
    
    public void findFiles(){
        modFiles = Main.modDir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".mod");
            }
        });
    }
 
    public void findDirectoryInDirectory(File dir){
        //System.out.println(dir.getAbsolutePath());
        directoryInDirectory = dir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return new File(dir, name).isDirectory();
            }
        });
    }
  

    public String[] convertFileToString(File[] files){
        String[] names = new String[files.length];
        for (int i = 0; i < files.length; i++) {
            names[i] = files[i].getName();
        }
        return names;
    }

}