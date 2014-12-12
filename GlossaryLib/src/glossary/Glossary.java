package glossary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import util.FileUtil;

/**
 * This object represents a Glossary
 */
public abstract class Glossary {
    
    private boolean autosave;
    private String file;

    /**
     * Inserts or updates (if it already existed) a term.
     *
     * @param term the term to be inserted or update in the glossary
     * @param meaning the string representing the meaning of the term
     * @return true if there was a change in the glossary.
     */
    public abstract boolean upsert(String term, String meaning);

    /**
     * Variant of Upsert that takes a String and parses the term and the
     * meaning.
     *
     * @param toParse
     * @return true if there was a change in the glossary.
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
     * @return true if there was a change in the glossary.
     */
    public boolean upsert(String[] record) {
        if (record.length != 2) {
            return false;
        }
        return upsert(record[0], record[1]);
    }

    /**
     * Variant of upsert that takes an Array of records as a parameters and
     * calls Upsert on every one of them.
     *
     * @param records the array of records to "upsert" to the list
     * @return true if there was a change in the glossary.
     */
    public boolean upsert(String[][] records) {
        boolean ret = false;
        for (String[] record : records) {
            if (upsert(record)) {
                ret = true;
            }
        }
        return ret;
    }

    /**
     * Returns the index of the term given
     *
     * @param term the term to search
     * @return integer representing the index of the term in the glossary
     */
    public abstract int find(String term);

    /**
     * Returns the record at the given index
     *
     * @param index the index of the record to get
     * @return the resulting record or null if it doesn't exist
     */
    public abstract String[] find(int index);

    /**
     * Returns the meaning of the given term
     *
     * @param term a string
     * @return null if the term is not in the glossary, otherwise the meaning of
     * the term
     */
    public String meaningOf(String term) {
        int i = find(term);
        return meaningOf(i);
    }

    /**
     * Returns the meaning of the word at the given index
     *
     * @param index the index of the term
     * @return the meaning of the term or null if it doesn't exist
     */
    public abstract String meaningOf(int index);

    /**
     * Deletes a term from the glossary if it exists
     *
     * @param term the term to delete
     * @return true if it existed, false otherwise
     */
    public abstract boolean delete(String term);

    /**
     * Removes everything from the glossary. Be careful!
     */
    public abstract void clear();

    /**
     * Returns the size of the Glossary
     *
     * @return the size of the glossary.
     */
    public abstract int size();

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
     * Exports the entire Glossary to a single String.
     *
     * @return a String.
     */
    public abstract String asString();

    /**
     * Imports glossary terms from the given string.
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
    public abstract String[][] getCopy();

    /**
     * Get an Array of the words in this Glossary filtered by the given String
     *
     * @param filter the filter to find the words
     * @return the words found
     */
    public abstract String[] getWordList(String filter);

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
     * This method is called when a term is upserted to the Glossary.
     *
     * @param term the term that was upserted
     * @param meaning the meaning of the upserted term
     */
    public abstract void onUpsert(String term, String meaning);

    /**
     * This method is called when a term is deleted from the Glossary.
     *
     * @param term the term that was deleted
     */
    public abstract void onDelete(String term);

    /**
     * This method is called when the glossary is cleared.
     */
    public abstract void onClear();
}
