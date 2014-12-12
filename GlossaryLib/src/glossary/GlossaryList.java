package glossary;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * This object represents a Glossary implemented with a List as engine.
 */
public class GlossaryList extends Glossary {

    private static Comparator<String[]> comparator;

    private final ArrayList<String[]> list;

    /**
     * Creates a new Glossary that loads from the given file then autosaves
     * every change.
     *
     * @param file the file associated to this glossary
     */
    public GlossaryList(String file) {
        this(); // Call other costructor
        if (file != null && !file.isEmpty()) {
            this.file = file;
            this.autosave = true;
            load();
        }
    }

    /**
     * Creates an empty Glossary
     */
    public GlossaryList() {
        if (comparator == null) {
            // Initialize the comparator to sort the Glossary
            comparator = new Comparator<String[]>() {
                @Override
                public int compare(String[] o1, String[] o2) {
                    return o1[0].compareTo(o2[0]);
                }
            };
        }
        // Initialize the list of glossary entries with a Thread-Safe list.
        list = new ArrayList<String[]>();
        // Set initial vars
        file = null;
        autosave = false;
    }

    @Override
    public boolean upsert(String term, String meaning) {
        int index = -1;
        // Fix the strings
        term = term.trim().toLowerCase();
        meaning = meaning.trim();
        // search if the term already exists
        if ((index = find(term)) >= 0) { // Existing term
            if (meaningOf(index).equals(meaning)) {
                // The term to upsert is exactly the same in the Glossary!
                return false;
            }
            synchronized (list) {
                list.get(index)[1] = meaning;
            }
            System.out.println("[Glossary] Updating meaning for " + term);
        } else { // New term
            String[] s = {term, meaning};
            synchronized (list) {
                list.add(s);
            }
            System.out.println("[Glossary] Adding new term: " + term);
        }
        onUpsert(term, meaning);
        if (autosave) {
            save();
        }
        return true;
    }

    @Override
    public boolean upsert(String toParse) {
        String s[] = toParse.split(":", 2);
        if (s.length != 2) {
            return false;
        }
        return upsert(s);
    }

    @Override
    public boolean upsert(String[] record) {
        if (record.length != 2) {
            return false;
        }
        return upsert(record[0], record[1]);
    }

    @Override
    public boolean upsert(String[][] records) {
        boolean ret = false;
        for (String[] record : records) {
            if (upsert(record)) {
                ret = true;
            }
        }
        return ret;
    }

    @Override
    public int find(String term) {
        // Fix the string
        term = term.trim();
        synchronized (list) {
            for (int i = 0; i < list.size(); i++) { // Each element of the list
                if (list.get(i)[0].equalsIgnoreCase(term)) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    public String[] find(int index) {
        synchronized (list) {
            return list.get(index);
        }
    }

    @Override
    public String meaningOf(int index) {
        if (index < 0) {
            return null;
        }
        synchronized (list) {
            if (index >= list.size()) {
                return null;
            }
            return list.get(index)[1];
        }
    }

    @Override
    public boolean delete(String term) {
        int index = find(term);
        if (index < 0) {
            return false;
        }
        synchronized (list) {
            list.remove(index);
        }
        System.out.println("[Glossary] Deleting " + term);
        onDelete(term);
        if (autosave) {
            save();
        }
        return true;
    }

    @Override
    public void clear() {
        synchronized (list) {
            list.clear();
        }
        onClear();
    }

    @Override
    public int size() {
        synchronized (list) {
            return list.size();
        }
    }

    @Override
    public String asString() {
        String content = "";
        synchronized (list) {
            for (String s[] : list) {
                content += s[0] + ":" + s[1].replace('\n', ' ') + "\n";
            }
        }
        return content;
    }

    @Override
    public String[][] getCopy() {
        String a[][] = null;
        synchronized (list) {
            a = new String[list.size()][2];
            for (int i = 0; i < a.length; i++) {
                a[i] = list.get(i);
            }
        }
        return a;
    }

    @Override
    public String[] getWordList(String filter) {
        Object[] ol;
        synchronized (list) {
            if (filter == null) {
                ol = list.toArray();
            } else {
                // Filter all the words
                ol = list.stream()
                        .filter(
                                (String[] s)
                                -> filter == null || (s[0] != null && s[0].contains(filter))
                        ).toArray();
            }
        }
        // Convert the Array
        String a[] = new String[ol.length];
        for (int i = 0; i < a.length; i++) {
            a[i] = ((String[]) ol[i])[0];
        }
        return a;
    }

    @Override
    public void onUpsert(String term, String meaning) {
        // This method can be overridden if needed
    }

    @Override
    public void onDelete(String term) {
        // This method can be overridden if needed
    }

    @Override
    public void onClear() {
        // This method can be overridden if needed
    }
}
