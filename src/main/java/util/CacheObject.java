package util;

import objects.databaseObjects.AccessRight;
import objects.databaseObjects.User;
import objects.objectLogic.LogicAccessRight;
import objects.objectLogic.LogicUser;

import java.util.*;

/**
 * Created by Nathanael on 5/12/2015
 */
public class CacheObject {

    private static Map<Integer, User> cachedUsers;
    private static Map<Integer, AccessRight> cachedAccessRights;

    private CacheObject() {
        getUserList();
        getAccessRightList();
    }

    public static void setupCache() {
        getUserList();
        getAccessRightList();
    }

    public static void reset() {
        cachedUsers = null;
        cachedAccessRights = null;

        getUserList();
        getAccessRightList();
    }

    public static Collection<User> getUserList() {
        if (cachedUsers == null) {
            cachedUsers = new HashMap<Integer, User>();
            List<User> userList = LogicUser.findAll();
            for (User user : userList) {
                cachedUsers.put(user.getId(), user);
            }
        }
        return cachedUsers.values();
    }

    public static Collection<AccessRight> getAccessRightList() {
        if (cachedAccessRights == null) {
            cachedAccessRights = new HashMap<Integer, AccessRight>();
            List<AccessRight> accessRightList = LogicAccessRight.findAll();
            for (AccessRight accessRight : accessRightList) {
                cachedAccessRights.put(accessRight.getId(), accessRight);
            }
        }
        return cachedAccessRights.values();
    }

    public static User getUser(String name) {
        if (Util.isEmpty(name)) {
            return null;
        }

        getUserList();

        for (User user : cachedUsers.values()) {
            if (user.getName().equals(name)) {
                return user;
            }
        }

        return null;
    }

    public static void addToUsers(User user) {
        if (cachedUsers == null) {
            getUserList();
        }

        assert cachedUsers != null;
        cachedUsers.put(user.getId(), user);
    }

    public static void addToAccessRights(AccessRight accessRight) {
        if (cachedAccessRights == null) {
            getAccessRightList();
        }

        assert cachedAccessRights != null;
        cachedAccessRights.put(accessRight.getId(), accessRight);
    }

    public static void removeFromUsers(Integer userId) {
        if (cachedUsers == null || cachedUsers.isEmpty()) {
            return;
        }

        cachedUsers.remove(userId);
    }

    public static List<AccessRight> getAccessRights(int userId) {
        List<AccessRight> accessRightList = new ArrayList<AccessRight>();

        if (userId <= 0) {
            return accessRightList;
        }

        getAccessRightList();

        for (AccessRight accessRight : cachedAccessRights.values()) {
            if (accessRight.getUserId() == userId) {
                accessRightList.add(accessRight);
            }
        }

        return accessRightList;
    }
}
