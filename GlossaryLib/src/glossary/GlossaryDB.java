package glossary;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fazo
 */
public class GlossaryDB extends Glossary {

    Connection db;

    /**
     * Creates a Glossary connected to given Database
     *
     * @param c
     * @throws SQLException
     */
    public GlossaryDB(Connection c) throws SQLException {
        db = c;
        if (!db.isValid(3)) {
            throw new SQLException("Connection to DB not valid");
        }
        c.createStatement().execute("create table if not exists glossary.glossary "
                + "(term CHAR(50), meaning CHAR(255));");
    }

    /**
     * Creates a Glossary connected to given Database loading from given file
     * and autosaving to it
     *
     * @param file the file to sync with the glossary
     */
    public GlossaryDB(Connection c, String file) throws SQLException {
        this(c); // Call other costructor
        if (file != null && !file.isEmpty()) {
            this.file = file;
            this.autosave = true;
            load();
        }
    }

    @Override
    public boolean upsert(String term, String meaning) {
        term = term.trim().toLowerCase().replace("\"", "\\\"");
        meaning = meaning.trim().replace("\"", "\\\"");
        int i;
        try {
            i = db.createStatement().executeUpdate("UPDATE glossary set meaning = \"" + meaning + "\" where term = \"" + term + "\";");
        } catch (SQLException ex) {
            // Failed
            Logger.getLogger(GlossaryDB.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        if (i > 0) {
            return true; // Updated original meaning
        }
        try {
            i = db.createStatement().executeUpdate("INSERT INTO GLOSSARY values (\"" + term + "\",\"" + meaning + "\");");
        } catch (SQLException ex) {
            // Failed
            Logger.getLogger(GlossaryDB.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        if (i > 0) {
            onUpsert(term, meaning);
        }
        return i > 0;
    }

    @Override
    protected int find(String term) {
        throw new UnsupportedOperationException("This Operation is not supported using a DB Glossary");
    }

    @Override
    protected String[] find(int index) {
        throw new UnsupportedOperationException("This Operation is not supported using a DB Glossary");
    }

    @Override
    protected String meaningOf(int index) {
        throw new UnsupportedOperationException("This Operation is not supported using a DB Glossary");
    }

    @Override
    public boolean delete(String term) {
        term = term.trim().toLowerCase();
        int i;
        try {
            i = db.createStatement().executeUpdate("DELETE FROM GLOSSARY where term=\"" + term + "\";");
        } catch (SQLException ex) {
            // Failed
            Logger.getLogger(GlossaryDB.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        if (i == 1) {
            onDelete(term);
        }
        return i == 1;
    }

    @Override
    public void clear() {
        try {
            db.createStatement().executeUpdate("DELETE FROM GLOSSARY;");
            onClear();
        } catch (SQLException ex) {
            // Failed
            Logger.getLogger(GlossaryDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public int size() {
        try {
            ResultSet result = db.createStatement().executeQuery("SELECT COUNT(term) FROM GLOSSARY");
            if (!result.first()) {
                return -1;
            }
            return result.getInt(1);
        } catch (SQLException ex) {
            // Failed
            Logger.getLogger(GlossaryDB.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }

    @Override
    public String asString() {
        String r = "";
        ResultSet result;
        try {
            result = db.createStatement().executeQuery("SELECT * FROM GLOSSARY;");
        } catch (SQLException ex) {
            // Failed
            Logger.getLogger(GlossaryDB.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        try {
            while (result.next()) {
                try {
                    r += result.getString("term") + ":" + result.getString("meaning") + "\n";
                } catch (SQLException ex) {
                    // Ignore this row
                }
            }
        } catch (SQLException ex) {
            // Loop has stopped
        }
        return r;
    }

    @Override
    public String[][] getCopy() {
        String r[][] = new String[size()][2];
        ResultSet result;
        try {
            result = db.createStatement().executeQuery("SELECT * FROM GLOSSARY;");
        } catch (SQLException ex) {
            // Failed
            Logger.getLogger(GlossaryDB.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        try {
            for (int i = 0; result.next(); i++) {
                try {
                    r[i][0] = result.getString("term");
                    r[i][1] = result.getString("meaning");
                } catch (SQLException ex) {
                    // Ignore this row
                }
            }
        } catch (SQLException ex) {
            // Loop has stopped
        }
        return r;
    }

    @Override
    public String[] getWordList(String filter) {
        return getWordList(filter, false);
    }

    @Override
    public String[] getSortedWordList(String filter) {
        return getWordList(filter, true);
    }

    /**
     * FILTER BROKEN
     *
     * @param filter
     * @param sorted
     * @return
     */
    private String[] getWordList(String filter, boolean sorted) {
        if (filter != null || !filter.isEmpty()) {
            throw new UnsupportedOperationException("Filter not yet supported in GlossaryDB");
        }
        String r[] = new String[size()];
        ResultSet result;
        try {
            result = db.createStatement().executeQuery(
                    "SELECT term FROM GLOSSARY where " + (sorted ? " order by term" : "") + ";");
        } catch (SQLException ex) {
            // Failed
            Logger.getLogger(GlossaryDB.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        try {
            for (int i = 0; result.next(); i++) {
                try {
                    r[i] = result.getString(1);
                } catch (SQLException ex) {
                    // Ignore this row
                }
            }
        } catch (SQLException ex) {
            // Loop has stopped
        }
        return r;
    }

    @Override
    public String meaningOf(String term) {
        term = term.trim().toLowerCase();
        ResultSet result;
        try {
            result = db.createStatement().executeQuery("SELECT * FROM GLOSSARY WHERE term=\"" + term + "\";");
        } catch (SQLException ex) {
            // Failed
            Logger.getLogger(GlossaryDB.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        try {
            if (result.first()) {
                return result.getString("meaning");
            }
        } catch (SQLException ex) {
            Logger.getLogger(GlossaryDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void onUpsert(String term, String meaning) {
        // Can be overridden
    }

    @Override
    public void onDelete(String term) {
        // Can be overridden
    }

    @Override
    public void onClear() {
        // Can be overridden
    }

}
