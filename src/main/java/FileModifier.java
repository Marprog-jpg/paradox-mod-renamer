import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileModifier {
    public static void removeLineFromFile(File inputFile, int i) throws FileNotFoundException, IOException{
        File tempFile = new File(inputFile.getParent() + File.separator + "myTempFile.mod");

        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        String lineToRemove = "path=";
        String currentLine;

        while((currentLine = reader.readLine()) != null) {
            // trim newline when comparing with lineToRemove
            String trimmedLine = currentLine.trim();
            if(trimmedLine.contains(lineToRemove)) continue;
            writer.write(currentLine + System.getProperty("line.separator"));
        }
        writer.close(); 
        reader.close(); 

        inputFile.delete();
        boolean successful = tempFile.renameTo(inputFile);
        
        
}
    
    public static void addTextToFile(File inputFile, int i){
        String fileNameWithOutExt = Main.modFile.getName().replaceFirst("[.][^.]+$", "");
        Path myPath = Paths.get(Main.modFile.getAbsolutePath());
        try {
            Files.write(Paths.get(Main.modFile.getAbsolutePath()), ("path=\"mod/" + fileNameWithOutExt + "\"").getBytes(), StandardOpenOption.APPEND);
        }catch (IOException e) {
            //handle exception here
        }
    }
}
