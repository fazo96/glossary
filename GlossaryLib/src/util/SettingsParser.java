package util;

import java.io.File;
import java.util.Properties;

/**
 * This class reads a file with some settings and parses them.
 *
 * @author fazo
 */
public class SettingsParser {

    /**
     * Reads a file and returns the properties inside it.
     *
     * @param f the file to read
     * @param defaults the default properties to use in case some are not found
     * @return a list of properties or null if the file could not be read
     */
    public static Properties parseFile(File f, Properties defaults) {
        String content = FileUtil.readFile(f);
        if (content == null || content.isEmpty()) {
            return null;
        }
        Properties p = new Properties(defaults);
        for (String s : content.split("\n")) {
            s = s.trim();
            if (s.startsWith("#")) {
                // Skip comment
                continue;
            }
            // Prepare key/value pair
            String ss[] = s.split("#", 2)[0].split(":", 2);
            if (ss.length != 2) {
                // Invalid key/value pair
                continue;
            }
            // Set the property we just read
            p.setProperty(ss[0].trim().toLowerCase(), ss[1].trim());
        }
        return p;
    }

    /**
     * Reads a file and returns the properties inside it.
     *
     * @param filepath the path of the file to read
     * @param defaults the default properties to use in case some are not found
     * @return a list of properties or null if the file could not be read
     */
    public static Properties parseFile(String filepath, Properties defaults) {
        return parseFile(new File(filepath), defaults);
    }

    /**
     * Modifies the given settings in the given file without changing the rest
     * of the file. NOT YET IMPLEMENTED
     *
     * @param filepath the file to change
     * @param toSet the properties to set
     * @return true if successfull
     */
    public static boolean change(String filepath, Properties toSet) {
        throw new UnsupportedOperationException("Not yet implemented (doesn't work)");
        /*String fileContent = FileUtil.readFile(filepath);
         for (String prop : toSet.stringPropertyNames()) {
         System.out.println(prop);
         String newS = fileContent.replaceAll("^" + prop, prop + ":" + (String) toSet.getProperty(prop));
         if (newS.equals(fileContent)) {
         return false;
         }
         }
         return FileUtil.writeFile(filepath, fileContent);*/
    }

    /**
     * Writes the given settings to given file.
     *
     * @param filepath the file to write to
     * @param toSet the settings to write in the file
     * @return true if successfull
     */
    public static boolean write(String filepath, Properties toSet) {
        String s = "";
        for (String prop : toSet.stringPropertyNames()) {
            if (toSet.getProperty(prop) == null) {
                continue;
            }
            s += prop + ":" + toSet.getProperty(prop) + "\n";
        }
        return FileUtil.writeFile(filepath, s);
    }

    /**
     * Make default costructor private.
     */
    private SettingsParser() {
    }

}
