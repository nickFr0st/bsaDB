package util;

import objects.databaseObjects.AccessRight;
import objects.databaseObjects.Advancement;
import objects.databaseObjects.Requirement;
import objects.databaseObjects.User;
import objects.objectLogic.LogicAccessRight;
import objects.objectLogic.LogicAdvancement;
import objects.objectLogic.LogicRequirement;
import objects.objectLogic.LogicUser;

import java.util.*;

/**
 * Created by Nathanael on 5/12/2015
 */
public class CacheObject {

    private static Map<Integer, User> cachedUsers;
    private static Map<Integer, AccessRight> cachedAccessRights;
    private static Map<Integer, Advancement> cachedAdvancements;
    private static Map<Integer, Requirement> cachedRequirements;

    private CacheObject() {
        getUserList();
        getAccessRightList();
        getAdvancementList();
        getRequirementList();
    }

    public static void setupCache() {
        getUserList();
        getAccessRightList();
    }

    public static void reset() {
        cachedUsers = null;
        cachedAccessRights = null;
        cachedAdvancements = null;
        cachedRequirements = null;

        getUserList();
        getAccessRightList();
        getAdvancementList();
        getRequirementList();
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

    public static void removeFromUsers(Integer userId) {
        if (cachedUsers == null || cachedUsers.isEmpty()) {
            return;
        }

        cachedUsers.remove(userId);
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

    public static void addToAccessRights(AccessRight accessRight) {
        if (cachedAccessRights == null) {
            getAccessRightList();
        }

        assert cachedAccessRights != null;
        cachedAccessRights.put(accessRight.getId(), accessRight);
    }

    public static void removeFromAccessRights(Integer accessRightId) {
        if (cachedAccessRights == null || cachedAccessRights.isEmpty()) {
            return;
        }

        cachedAccessRights.remove(accessRightId);
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

    public static Collection<Advancement> getAdvancementList() {
        if (cachedAdvancements == null) {
            cachedAdvancements = new HashMap<Integer, Advancement>();
            List<Advancement> advancementList = LogicAdvancement.findAll();
            for (Advancement advancement : advancementList) {
                cachedAdvancements.put(advancement.getId(), advancement);
            }
        }
        return cachedAdvancements.values();
    }

    public static Advancement getAdvancement(String name) {
        if (Util.isEmpty(name)) {
            return null;
        }

        getAdvancementList();

        for (Advancement advancement : cachedAdvancements.values()) {
            if (advancement.getName().equals(name)) {
                return advancement;
            }
        }

        return null;
    }

    public static void addToAdvancements(Advancement advancement) {
        if (cachedAdvancements == null) {
            getAccessRightList();
        }

        assert cachedAdvancements != null;
        cachedAdvancements.put(advancement.getId(), advancement);
    }

    public static void removeFromAdvancements(Integer advancementId) {
        if (cachedAdvancements == null || cachedAdvancements.isEmpty()) {
            return;
        }

        cachedAdvancements.remove(advancementId);
    }

    public static Collection<Requirement> getRequirementList() {
        if (cachedRequirements == null) {
            cachedRequirements = new HashMap<Integer, Requirement>();
            List<Requirement> requirementList = LogicRequirement.findAll();
            for (Requirement requirement : requirementList) {
                cachedRequirements.put(requirement.getId(), requirement);
            }
        }
        return cachedRequirements.values();
    }

    public static List<Requirement> getRequirementListByParentIdAndTypeId(int parentId, int typeId) {
        List<Requirement> requirementList = new ArrayList<Requirement>();

        getRequirementList();

        for (Requirement requirement : cachedRequirements.values()) {
            if (requirement.getParentId() == parentId && requirement.getTypeId() == typeId) {
                requirementList.add(requirement);
            }
        }

        return requirementList;
    }

    public static void addToRequirements(Requirement requirement) {
        if (cachedRequirements == null) {
            getRequirementList();
        }

        assert cachedRequirements != null;
        cachedRequirements.put(requirement.getId(), requirement);
    }

    public static void addToRequirements(List<Requirement> requirementList) {
        if (cachedRequirements == null) {
            getRequirementList();
        }

        assert cachedRequirements != null;
        for (Requirement requirement : requirementList) {
            cachedRequirements.put(requirement.getId(), requirement);
        }
    }

    public static void removeFromRequirements(Integer requirementId) {
        if (cachedRequirements == null || cachedRequirements.isEmpty()) {
            return;
        }

        cachedRequirements.remove(requirementId);
    }
}
