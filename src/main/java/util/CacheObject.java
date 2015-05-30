package util;

import objects.databaseObjects.*;
import objects.objectLogic.*;

import java.util.*;

/**
 * Created by Nathanael on 5/12/2015
 */
public class CacheObject {

    private static Map<Integer, User> cachedUsers;
    private static Map<Integer, AccessRight> cachedAccessRights;
    private static Map<Integer, Advancement> cachedAdvancements;
    private static Map<Integer, Requirement> cachedRequirements;
    private static Map<Integer, MeritBadge> cachedMeritBadges;

    private CacheObject() {
        getUserList();
        getAccessRightList();
        getAdvancementList();
        getRequirementList();
        getMeritBadgeList();
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
        cachedMeritBadges = null;

        getUserList();
        getAccessRightList();
        getAdvancementList();
        getRequirementList();
        getMeritBadgeList();
    }

    public static void clear() {
        cachedUsers = null;
        cachedAccessRights = null;
        cachedAdvancements = null;
        cachedRequirements = null;
        cachedMeritBadges = null;
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

    public static List<Advancement> getAdvancementList(List<String> nameList) {
        getAdvancementList();

        List<Advancement> advancementList = new ArrayList<Advancement>();
        for (String name : nameList) {
            for (Advancement advancement : cachedAdvancements.values()) {
                if (advancement.getName().equals(name)) {
                    advancementList.add(advancement);
                }
            }
        }

        return advancementList;
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
            getAdvancementList();
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

    public static Collection<MeritBadge> getMeritBadgeList() {
        if (cachedMeritBadges == null) {
            cachedMeritBadges = new HashMap<Integer, MeritBadge>();
            List<MeritBadge> meritBadgeList = LogicMeritBadge.findAll();
            for (MeritBadge meritBadge : meritBadgeList) {
                cachedMeritBadges.put(meritBadge.getId(), meritBadge);
            }
        }
        return cachedMeritBadges.values();
    }

    public static List<MeritBadge> getMeritBadgeList(List<String> nameList) {
        getMeritBadgeList();

        List<MeritBadge> meritBadgeList = new ArrayList<MeritBadge>();
        for (String name : nameList) {
            for (MeritBadge meritBadge : cachedMeritBadges.values()) {
                if (meritBadge.getName().equals(name)) {
                    meritBadgeList.add(meritBadge);
                }
            }
        }

        return meritBadgeList;
    }

    public static MeritBadge getMeritBadge(String name) {
        if (Util.isEmpty(name)) {
            return null;
        }

        getMeritBadgeList();

        for (MeritBadge meritBadge : cachedMeritBadges.values()) {
            if (meritBadge.getName().equals(name)) {
                return meritBadge;
            }
        }

        return null;
    }

    public static void addToMeritBadges(MeritBadge meritBadge) {
        if (cachedMeritBadges == null) {
            getMeritBadgeList();
        }

        assert cachedMeritBadges != null;
        cachedMeritBadges.put(meritBadge.getId(), meritBadge);
    }

    public static void removeFromMeritBadges(Integer meritBadgeId) {
        if (cachedMeritBadges == null || cachedMeritBadges.isEmpty()) {
            return;
        }

        cachedMeritBadges.remove(meritBadgeId);
    }
}
