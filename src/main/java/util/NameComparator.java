package util;

import java.util.Comparator;

/**
 * Created by Nathanael on 2/28/2016
 */
public class NameComparator implements Comparator<String> {
    @Override
    public int compare(String o1, String o2) {
        return o1.compareTo(o2);
    }
}
