package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.swing.plaf.metal.MetalIconFactory;

public class FileUtil {

    /**
     * Reads a file from the provided path and returns its content
     *
     * @param file the file to read from
     * @return the content of the file as a String
     */
    public static String readFile(File file) {
        BufferedReader bf;
        try {
            // Initialize File Reader object
            bf = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        } catch (FileNotFoundException ex) {
            // File does not exist
            return null;
        }
        String input = "", s = "";
        try {
            // Keep reading until there is nothing to read
            while ((input = bf.readLine()) != null) {
                s += input + "\n";
            }
        } catch (IOException ex) {
            // There was an error while reading or EOF
            return s;
        }
        // Return the string except the last character because it is always \n
        return s.substring(0, s.length() - 1);
    }

    /**
     * Variant of readFile that takes a String instead of a File. Nothing else
     * is different.
     *
     * @param path the filepath
     * @return the content of the file as a String
     */
    public static String readFile(String path) {
        return readFile(new File(path));
    }

    /**
     * Writes the given string to the file replacing its content
     *
     * @param path the string representing the path to the file
     * @param content the string with the content to write
     * @return true if successful, false otherwise
     */
    public static boolean writeFile(String path, String content) {
        File file = new File(path);
        FileWriter fw = null;
        try {
            fw = new FileWriter(file);
        } catch (IOException ex) {
            // File opening failed
            return false;
        }
        try {
            fw.write(content);
        } catch (IOException ex) {
            // Writing to the file failed
            return false;
        }
        try {
            fw.close();
        } catch (IOException ex) {
            // Do nothing if closing file failed
        }
        return true;
    }

    /**
     * Returns the array of Files in the given folder
     *
     * @param path the path to the folder
     * @return an array of File instances
     */
    public static File[] filesIn(String path) {
        File folder = new File(path);
        if (!folder.isDirectory()) {
            return null;
        }
        return folder.listFiles();
    }
    /**
     * Tells if a folder exists and is actually a folder.
     * @param path the path of the folder
     * @return true if it exists.
     */
    public static boolean folderExists(String path){
        return new File(path).isDirectory();
    }
}
