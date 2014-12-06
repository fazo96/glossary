package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileUtil {
    
    /**
     * Reads a file from the provided path and returns its content
     * @param path the path of the file as a string
     * @return the content of the file as a string
     */
    public static String readFile(String path){
        BufferedReader bf;
        try {
            // Initialize File Reader object
            bf = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
        } catch (FileNotFoundException ex) {
            // File does not exist
            return null;
        }
        String input="",s="";
        try {
            // Keep reading until there is nothing to read
            while((input = bf.readLine()) != null){
                s+=input+"\n";
            }
        } catch (IOException ex) {
            // There was an error while reading or EOF
            return s;
        }
        // Return the string except the last character because it is always \n
        return s.substring(0, s.length()-1);
    }
    /**
     * Writes the given string to the file replacing its content
     * @param path the string representing the path to the file
     * @param content the string with the content to write
     * @return true if successful, false otherwise
     */
    public static boolean writeFile(String path, String content){
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
}
