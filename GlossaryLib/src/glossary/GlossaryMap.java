/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glossary;

import java.util.TreeMap;

/**
 *
 * @author fazo
 */
public class GlossaryMap extends Glossary {

    private final TreeMap<String, String> map;

    /**
     * Creates an empty Glossary
     */
    public GlossaryMap() {
        file = null;
        autosave = false;
        map = new TreeMap<>();
    }

    /**
     * Creates a new Glossary that loads from the given file then autosaves
     * every change.
     *
     * @param file the file associated to this glossary
     */
    public GlossaryMap(String file) {
        this();
        this.file = file;
        load();
        autosave = true;
    }

    @Override
    public boolean upsert(String term, String meaning) {
        String prevValue;
        meaning = meaning.trim();
        term = term.toLowerCase().trim();
        synchronized (map) {
            prevValue = map.put(term, meaning);
        }
        if (prevValue == null || !prevValue.equals(meaning)) {
            return false;
        }
        return true;
    }

    @Override
    public int find(String term) {
        // I haven't found a way to do this yet
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String[] find(int index) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String meaningOf(int index) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(String term) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int size() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String asString() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String[][] getCopy() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String[] getWordList(String filter) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onUpsert(String term, String meaning) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onDelete(String term) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onClear() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
