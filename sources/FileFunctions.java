import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class FileFunctions {
    public  Path fileOut;
     List<String> lines;
    public  List<Integer> readFromFile(String fileName) throws IOException {
        List<Integer> returnBox = new ArrayList<>();
        File file = new File(fileName + ".txt");
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String text = null;
            while ((text = reader.readLine()) != null) {
                String[] splitString = text.split(",", 2);
                for (String x : splitString) returnBox.add(Integer.parseInt(x));
            }
            return returnBox;
        } catch (FileNotFoundException e) {
            System.out.println("\nNot found\n");
            return null;
        }
    }
    public void initFile(String name){
        fileOut = Paths.get(name + ".txt");
        lines = new ArrayList<String>();
    }
    public void writeInFile(String toWrite) {
    lines.add(toWrite);
    }
    public void commitFile() throws IOException {
        Files.write(fileOut,lines,StandardCharsets.UTF_8);
        lines.clear();
    }
}
