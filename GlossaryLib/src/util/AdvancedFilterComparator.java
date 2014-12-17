package util;

import java.util.Comparator;

/**
 * Compares two Glossary records
 *
 * @author fazo
 */
public class AdvancedFilterComparator implements Comparator {

    private String[] filters;

    public AdvancedFilterComparator(String filter) {
        this.filters = filter.split(" ");
    }

    /**
     * Checks with how many filters the given string matches
     *
     * @param s the string to check
     * @return an int
     */
    public int matches(String s) {
        if (filters == null || filters.length == 0) {
            return 0;
        }
        int i = 0;
        s = s.toLowerCase();
        for (String filter : filters) {
            if (s.contains(filter)) {
                i++;
            }
        }
        return i;
    }

    @Override
    public int compare(Object o1, Object o2) {
        String s1[] = (String[]) o1;
        String s2[] = (String[]) o2;
        if (s2.length != 2 || s1.length != 2) {
            throw new UnsupportedOperationException("Can't compare these records");
        }
        return (matches(s1[0]) + matches(s1[1])) - (matches(s2[0]) + matches(s2[1]));
    }

}
