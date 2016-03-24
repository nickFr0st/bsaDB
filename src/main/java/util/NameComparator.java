package util;

import objects.databaseObjects.Compare;

import java.util.Comparator;

/**
 * Created by Nathanael on 2/28/2016
 */
public class NameComparator implements Comparator<Compare> {
    @Override
    public int compare(Compare o1, Compare o2) {
        return o1.getName().compareTo(o2.getName());
    }
}
