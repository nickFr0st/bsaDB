package util;

import objects.databaseObjects.User;

import java.util.Comparator;

/**
 * Created by Nathanael on 2/28/2016
 */
public class UserNameComparator implements Comparator<User> {
    @Override
    public int compare(User o1, User o2) {
        return o1.getUserName().compareTo(o2.getUserName());
    }
}
