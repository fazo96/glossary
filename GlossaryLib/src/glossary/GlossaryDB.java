package glossary;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fazo
 */
public class GlossaryDB extends Glossary{

    Connection db;
    
    public GlossaryDB(Connection c) throws SQLException {
        db = c;
        if(!db.isValid(3)) throw new SQLException("Connection to DB not valid");
        
    }
    
    @Override
    public boolean upsert(String term, String meaning) {
    }

    @Override
    public int find(String term) {
    }

    @Override
    public String[] find(int index) {
    }

    @Override
    public String meaningOf(int index) {
    }

    @Override
    public boolean delete(String term) {
    }

    @Override
    public void clear() {
    }

    @Override
    public int size() {
    }

    @Override
    public String asString() {
    }

    @Override
    public String[][] getCopy() {
    }

    @Override
    public String[] getWordList(String filter) {
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
