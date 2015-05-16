package util;

import objects.databaseObjects.User;
import objects.objectLogic.LogicUser;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Nathanael on 5/12/2015
 */
public class CacheObject {

    private static Map<Integer, User> cachedUsers;

    private CacheObject() {
        getCachedUsers();
    }

    public static void setupCache() {
        getCachedUsers();
    }

    public static Collection<User> getCachedUsers() {
        if (cachedUsers == null) {
            cachedUsers = new HashMap<Integer, User>();
            List<User> userList = LogicUser.findAll();
            for (User user : userList) {
                cachedUsers.put(user.getId(), user);
            }
        }
        return cachedUsers.values();
    }

    public static User getUser(String name) {
        if (Util.isEmpty(name)) {
            return null;
        }

        getCachedUsers();

        for (User user : cachedUsers.values()) {
            if (user.getName().equals(name)) {
                return user;
            }
        }

        return null;
    }

    public static void addToCachedUsers(User user) {
        if (cachedUsers == null) {
            getCachedUsers();
        }

        assert cachedUsers != null;
        cachedUsers.put(user.getId(), user);
    }

    public static void removeFromCachedUsers(Integer userId) {
        if (cachedUsers == null || cachedUsers.isEmpty()) {
            return;
        }

        cachedUsers.remove(userId);
    }
}
