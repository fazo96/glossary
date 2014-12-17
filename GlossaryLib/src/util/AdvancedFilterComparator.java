package util;

import com.mysql.jdbc.StringUtils;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        int total = 0;
        s = s.toLowerCase();
        for (String filter : filters) {
            Pattern p = Pattern.compile(filter);
            Matcher m = p.matcher(s);
            while (m.find()) {
                total++;
            }
        }
        System.out.println("Check: " + s + "\nIt contains: " + total + " filters");
        return total;
    }

    @Override
    public int compare(Object o1, Object o2) {
        String s1[] = (String[]) o1;
        String s2[] = (String[]) o2;
        if (s2.length != 2 || s1.length != 2) {
            throw new UnsupportedOperationException("Can't compare these records");
        }
        return (matches(s2[0]) + matches(s2[1])) - (matches(s1[0]) + matches(s1[1]));
    }

}
