/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import util.FileUtil;

/**
 * This object represents the Glossary
 */
public class Glossary {

    private List<String[]> list;

    public Glossary() {
        // Initialize the list of glossary entries with a Thread-Safe list.
        list = Collections.synchronizedList(new ArrayList<String[]>());
    }

    /**
     * Inserts or updates (if it already existed) a term
     *
     * @param term the term to be inserted or update in the glossary
     * @param meaning the string representing the meaning of the term
     * @return true if the term existed before.
     */
    public boolean upsert(String term, String meaning) {
        int index = -1;
        if ((index = find(term)) > 0) {
            synchronized (list) {
                list.get(index)[1] = meaning;
            }
            return true;
        }
        String[] s = new String[2];
        s[0] = term.trim().toLowerCase();
        s[1] = meaning.trim();
        synchronized (list) {
            list.add(s);
        }
        return false;
    }

    /**
     * Variant of Upsert that takes an entire record as parameter (An array of
     * two strings)
     * @param record the record to "upsert" to the list
     * @return false if the length of the array is != 2 and the term wasn't
     * inserted
     */
    public boolean upsert(String[] record){
        if(record.length != 2) return false;
        upsert(record[0],record[1]);
        return true;
    }
    
    /**
     * Returns the index of the term given
     *
     * @param term the term to search
     * @return integer representing the index of the term in the glossary
     */
    public int find(String term) {
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
     * Deletes a term from the glossary if it exists
     * @param term the term to delete
     * @return true if it existed, false otherwise
     */
    public boolean delete(String term) {
        int index = find(term);
        if(index < 0) return false;
        synchronized (list) {
            list.remove(index);
        }
        return true;
    }
    
    /**
     * Reads a file and calls upsert on each term it finds in the file.
     * The format of the terms is "term: meaning" and there can be 1 term
     * per line.
     * @param filepath
     * @return -1 if there was an error, 
     */
    public int load(String filepath){
        String content = FileUtil.readFile(filepath);
        if(content == null) return -1;
        int count = 0;
        for(String s : content.split("\n")){
            if(upsert(s.split(":", 1))) count++;
        }
        return count;
    }
}
