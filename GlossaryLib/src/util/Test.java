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
            System.out.println("TESTING GLOSSARYLIST");
            testGlossary(new GlossaryList("glossary.txt"));
            System.out.println("TESTING GLOSSARYDB");
            Class.forName("com.mysql.jdbc.Driver");
            Properties cp = new Properties();
            cp.put("user", "root");
            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/glossary", cp);
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
        if (glossary instanceof GlossaryList) {
            glossary.setAutosave(false);
        }
        glossary.upsert("test", "nope");
        if (!glossary.meaningOf("test").equals("nope")) {
            throw new Exception("Upsert doesn't insert");
        }
        glossary.upsert("test", "meaning of test");
        if (!glossary.meaningOf("test").equals("meaning of test")) {
            throw new Exception("Upsert doesn't update");
        }
        if (!(glossary instanceof GlossaryDB)) {
            glossary.save();
        }
        glossary.upsert("tast", "maning of tast");
        if (glossary.size() != 2) {
            throw new Exception("Upsert doesn't insert");
        }
        if (!(glossary instanceof GlossaryDB) && glossary.getWordList("ta").length != 1) {
            throw new Exception("Filter error");
        }
        glossary.clear();
        if (glossary.size() != 0) {
            throw new Exception("Upsert doesn't insert");
        }
        glossary.delete("test");
        if (glossary.size() != 0) {
            throw new Exception("Delete doesn't work");
        }
        if (glossary instanceof GlossaryDB) {
            // Tests for glossarydb end here
            return;
        }
        glossary.load();
        if (!glossary.meaningOf("test").equals("meaning of test")) {
            throw new Exception("Saving/Loading error");
        }
        glossary.upsert("tast", "maning af tast");
        if (glossary.getWordList().length != 2) {
            throw new Exception("Word list error");
        }
    }

    public void fillGlossary(Glossary g, int itemCount) {
        for (int i = 0; i < itemCount; i++) {
            g.upsert("autoTerm" + i, "auto meaning " + i);
        }
    }

    /**
     * Make default costructor private.˙
     */
    private Test() {
    }
}
