import java.io.File;


public class FolderStructureChecker {
    protected static File folderOfSpecificMod;
    protected static FindFiles findFile; //change this into something better
    protected static String[] subdirectoryFolderNames;

    public static int checkFolderStructure(){
        findFile = new FindFiles();

        String modFileNameWithoutExtension = Main.modFile.getName().replaceFirst("[.][^.]+$", "");

        String stringFolderOfSpecificMod = Main.modFile.getParent();
        folderOfSpecificMod = new File(stringFolderOfSpecificMod);

        findFile.findDirectoryInDirectory(folderOfSpecificMod);
        subdirectoryFolderNames = findFile.convertFileToString(findFile.directoryInDirectory);

        if(modFileNameWithoutExtension.equals(subdirectoryFolderNames[0])){
            return 1;
        }else{
            return 0;
        }


    }}
