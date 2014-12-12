package util;

import glossary.Glossary;
import glossary.GlossaryDB;
import glossary.GlossaryList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class contains functions to test the various parts of the Library.
 *
 * @author fazo
 */
public class Test {

    /**
     * Runs all the tests.
     *
     * @param args args are not used.
     */
    public static void main(String args[]) {
        Connection c;
        try {
            // Catch exception from the test
            testGlossary(new GlossaryList("glossary.txt"));
            Class.forName("com.mysql.jdbc.Driver");
            Properties cp = new Properties();
            cp.put("user", "root");
            c = DriverManager.getConnection("jdbc:mysql://10.0.0.11:3306/glossary",cp);
            testGlossary(new GlossaryDB(c));
        } catch (Exception ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Some tests for the Glossary class. If they are not successfull, an
     * exception is thrown
     */
    private static void testGlossary(Glossary glossary) throws Exception {
        glossary.setAutosave(false);
        glossary.upsert("test", "nope");
        if (!glossary.meaningOf("test").equals("nope")) {
            throw new Exception("Upsert doesn't insert");
        }
        glossary.upsert("test", "meaning of test");
        if (!glossary.meaningOf("test").equals("meaning of test")) {
            throw new Exception("Upsert doesn't update");
        }
        glossary.save();
        glossary.delete("test");
        if (glossary.size() != 0) {
            throw new Exception("Delete doesn't work");
        }
        glossary.load();
        if (!glossary.meaningOf("test").equals("meaning of test")) {
            throw new Exception("Saving/Loading error");
        }
        glossary.upsert("tast","maning af tast");
        if(glossary.getWordList().length != 2)
            throw new Exception("Word list error");
        if(glossary.getWordList("ta").length != 1)
            throw new Exception("Filter error");
    }

    /**
     * Make default costructor private.˙
     */
    private Test() {
    }
}
