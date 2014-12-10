package glossary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import util.FileUtil;

/**
 * This object represents a Glossary
 */
public class Glossary {

    private static Comparator<String[]> comparator;

    private final ArrayList<String[]> list;
    private boolean autosave;
    private String file;

    /**
     * Creates a new Glossary loading from given file and autosaving to it
     *
     * @param file the file to sync with the glossary
     */
    public Glossary(String file) {
        this(); // Call other costructor
        this.file = file;
        this.autosave = true;
        load(file);
    }

    public Glossary() {
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

    /**
     * Inserts or updates (if it already existed) a term.
     *
     * @param term the term to be inserted or update in the glossary
     * @param meaning the string representing the meaning of the term
     * @return true if the term existed before.
     */
    public boolean upsert(String term, String meaning) {
        int index = -1;
        // Fix the strings
        term = term.trim().toLowerCase();
        meaning = meaning.trim();
        // search if the term already exists
        if ((index = find(term)) >= 0) {
            synchronized (list) {
                list.get(index)[1] = meaning;
            }
            System.out.println("[Glossary] Updating meaning for " + term);
            if (autosave) {
                save();
            }
            return true;
        }
        String[] s = {term, meaning};
        synchronized (list) {
            list.add(s);
        }
        System.out.println("[Glossary] Adding new term: " + term);
        if (autosave) {
            save();
        }
        return false;
    }

    /**
     * Variant of Upsert that takes a String and parses the term and the
     * meaning.
     *
     * @param toParse
     * @return true if it was a parseable string.
     */
    public boolean upsert(String toParse) {
        String s[] = toParse.split(":", 2);
        if (s.length != 2) {
            return false;
        }
        return upsert(s);
    }

    /**
     * Variant of Upsert that takes an entire record as parameter (An array of
     * two strings)
     *
     * @param record the record to "upsert" to the list
     * @return false if the length of the array is != 2 and the term wasn't
     * inserted
     */
    public boolean upsert(String[] record) {
        if (record.length != 2) {
            return false;
        }
        upsert(record[0], record[1]);
        return true;
    }

    /**
     * Variant of upsert that takes an Array of records as a parameters and
     * calls Upsert on every one of them.
     *
     * @param records the array of records to "upsert" to the list
     */
    public void upsert(String[][] records) {
        for (String[] record : records) {
            upsert(record);
        }
    }

    /**
     * Returns the index of the term given
     *
     * @param term the term to search
     * @return integer representing the index of the term in the glossary
     */
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

    /**
     * Returns the meaning of the given term
     *
     * @param term a string
     * @return null if the term is not in the glossary, otherwise the meaning of
     * the term
     */
    public String meaningOf(String term) {
        int i = find(term);
        if (i < 0) {
            return null;
        }
        synchronized (list) {
            return list.get(i)[1];
        }
    }

    /**
     * Returns the meaning of the word at the given index
     *
     * @param index the index of the term
     * @return the meaning of the term or null if it doesn't exist
     */
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

    /**
     * Deletes a term from the glossary if it exists
     *
     * @param term the term to delete
     * @return true if it existed, false otherwise
     */
    public boolean delete(String term) {
        int index = find(term);
        if (index < 0) {
            return false;
        }
        synchronized (list) {
            list.remove(index);
        }
        System.out.println("[Glossary] Deleting " + term);
        if (autosave) {
            save();
        }
        return true;
    }

    /**
     * The number of terms in the glossary
     *
     * @return an integer
     */
    public int termCount() {
        synchronized (list) {
            return list.size();
        }
    }

    /**
     * Removes everything from the glossary. Be careful!
     */
    public void clear() {
        synchronized (list) {
            list.clear();
        }
    }

    /**
     * Returns the size of the Glossary
     *
     * @return the size of the glossary.
     */
    public int size() {
        synchronized (list) {
            return list.size();
        }
    }

    /**
     * Reads a file and loads the terms in it into the glossary.
     *
     * @param filepath
     * @return -1 if there was an error,
     */
    private int load(String filepath) {
        String content = FileUtil.readFile(filepath);
        if (content == null) {
            return -1;
        }
        return fromString(content);
    }

    /**
     * Reads this Glossary's file and loads the terms in it into the Glossary.
     *
     * @return
     */
    public int load() {
        if (file == null) {
            return -1;
        }
        return load(file);
    }

    /**
     * Returns the entire Glossary as a single String.
     *
     * @return a String.
     */
    public String asString() {
        String content = "";
        synchronized (list) {
            for (String s[] : list) {
                content += s[0] + ":" + s[1].replace('\n', ' ') + "\n";
            }
        }
        return content;
    }

    /**
     * Takes a String with multiple terms and parses them.
     *
     * @param content the source string
     * @return the number of terms successfully loaded.
     */
    public int fromString(String content) {
        int count = 0;
        for (String s : content.split("\n")) {
            if (upsert(s)) {
                count++;
            }
        }
        System.out.println("[Glossary] Loaded " + count + " terms");
        return count;
    }

    /**
     * Saves the glossary to file. The saved file is human readable and can be
     * loaded back into the glossary.
     *
     * @param filepath the path of the file to save
     * @return true if successfull
     */
    private boolean save(String filepath) {
        String content = this.asString();
        System.out.println("[Glossary] Saving Glossary to " + filepath);
        return FileUtil.writeFile(filepath, content);
    }

    /**
     * Saves the Glossary to this Glossary's file.
     *
     * @return
     */
    public boolean save() {
        if (file == null) {
            return false;
        }
        return save(file);
    }

    /**
     * Get a copy of the glossary
     *
     * @return a copy of the glossary in the format of a matrix of strings.
     */
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

    /**
     * Get an Array of the words in this Glossary filtered by the given String
     *
     * @param filter the filter to find the words
     * @return the words found
     */
    public String[] getWordList(String filter) {
        Object[] ol;
        synchronized (list) {
            // Filter all the words
            ol = list.stream()
                    .filter(
                            (String[] s) -> s[0].contains(filter) || filter == null
                    ).toArray();
        }
        // Convert array
        String a[] = new String[ol.length];
        for (int i = 0; i < a.length; i++) {
            a[i] = (String) ol[i];
        }
        return a;
    }

    /**
     * Like getWordList(filter) but sorted alphabetically.
     *
     * @param filter the filter to find the words
     * @return a sorted array of the words found
     */
    public String[] getSortedWordList(String filter) {
        String s[] = getWordList(filter);
        Arrays.sort(s);
        return s;
    }

    /**
     * Get an Array of the words in this Glossary.
     *
     * @return an array of strings.
     */
    public String[] getWordList() {
        return getWordList(null);
    }

    /**
     * Like getWordList() but sorted alphabetically.
     *
     * @return an array of the words found
     */
    public String[] getSortedWordList() {
        return getSortedWordList(null);
    }

    /**
     * Wether automatically saving to file is enabled.
     *
     * @return true if enabled
     */
    public boolean isAutosaveOn() {
        return autosave;
    }

    /**
     * Set the Glossary to automatically save to its file.
     *
     * @param autosave if the Glossary must save automatically
     */
    public void setAutosave(boolean autosave) {
        this.autosave = autosave;
    }

    /**
     * Returns this glossary's file
     *
     * @return this glossary's file
     */
    public String getFile() {
        return file;
    }

    /**
     * Sets this glossary's file
     *
     * @param file path to a file
     */
    public void setFile(String file) {
        this.file = file;
    }

    /**
     * Sorts the Glossary by term name.
     */
    public void sort() {
        synchronized (list) {
            list.sort(comparator);
        }
    }
}
