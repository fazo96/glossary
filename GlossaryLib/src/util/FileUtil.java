package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;

public class FileUtil {

    /**
     * Reads a file from the provided path and returns its content
     *
     * @param file the file to read from
     * @return the content of the file as a String
     */
    public static String readFile(File file) {
        if (file == null) {
            return "";
        }
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
        if (path == null || path.isEmpty()) {
            return null;
        }
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
        return writeFile(file, content);
    }

    /**
     * Writes the given string to the file replacing its content
     *
     * @param file the file to write
     * @param content the content to write in the file
     * @return true is successful, false otherwise
     */
    public static boolean writeFile(File file, String content) {
        if (file == null) {
            return false;
        }
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
     *
     * @param path the path of the folder
     * @return true if it exists.
     */
    public static boolean folderExists(String path) {
        return new File(path).isDirectory();
    }

    /**
     * Returns the relative path for the given filepath to the working
     * directory.
     *
     * @param filepath the path to the file
     * @return a String with the relative path
     */
    public static String relativePathFor(String filepath) {
        return Paths.get(".").relativize(Paths.get(filepath)).toString();
    }

    /**
     * Returns the relative path for the given File to the working directory.
     *
     * @param f the file
     * @return a String with the relative path
     */
    public static String relativePathFor(File f) {
        return Paths.get(".").relativize(f.toPath()).toString();
    }

    /**
     * Make default costructor private
     */
    private FileUtil() {
    }
}
