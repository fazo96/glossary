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
     * Make default costructor private.
     */
    private SettingsParser() {
    }
;
}
