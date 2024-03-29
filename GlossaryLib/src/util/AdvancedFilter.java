package util;

import java.util.function.Predicate;

/**
 * This is an advanced filter for searching through arrays of strings.
 * It allows advanced filtering using a String regex.
 * 
 * @author fazo
 */
public class AdvancedFilter implements Predicate<String[]> {

    private String[] filters;

    public AdvancedFilter(String filter) {
        this.filters = filter.trim().toLowerCase().split(" ");
    }

    @Override
    public boolean test(String[] toTest) {
        if(filters == null || filters.length == 0) return false;
        int i = 0;
        for (String str : toTest) {
            for (String filter : filters) {
                if (str.toLowerCase().contains(filter)) {
                    i++;
                }
            }
        }
        return i >= filters.length;
    }
}
