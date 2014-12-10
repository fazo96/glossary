package util;

import glossary.Glossary;
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
        try {
            // Catch exception from the test
            testGlossary();
        } catch (Exception ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Some tests for the Glossary class. If they are not successfull, an
     * exception is thrown
     */
    private static void testGlossary() throws Exception {
        Glossary glossary = new Glossary("file_test.txt");
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
        if (glossary.termCount() != 0) {
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
