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
    private static Map<Integer, Counselor> cachedCounselors;

    private CacheObject() {
        getUserList();
        getAccessRightList();
        getAdvancementList();
        getRequirementList();
        getMeritBadgeList();
        getCounselorList();
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
        cachedCounselors = null;

        getUserList();
        getAccessRightList();
        getAdvancementList();
        getRequirementList();
        getMeritBadgeList();
        getCounselorList();
    }

    public static void clear() {
        cachedUsers = null;
        cachedAccessRights = null;
        cachedAdvancements = null;
        cachedRequirements = null;
        cachedMeritBadges = null;
        cachedCounselors = null;
    }

    public static Collection<User> getUserList() {
        if (cachedUsers == null) {
            cachedUsers = new HashMap<>();
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

    public static User getUserByUserName(String username) {
        if (Util.isEmpty(username)) {
            return null;
        }

        getUserList();

        for (User user : cachedUsers.values()) {
            if (user.getUserName().equals(username)) {
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
            cachedAccessRights = new HashMap<>();
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
        List<AccessRight> accessRightList = new ArrayList<>();

        if (userId <= 0) {
            return accessRightList;
        }

        for (AccessRight accessRight : getAccessRightList()) {
            if (accessRight.getUserId() == userId) {
                accessRightList.add(accessRight);
            }
        }

        return accessRightList;
    }

    public static Collection<Advancement> getAdvancementList() {
        if (cachedAdvancements == null) {
            cachedAdvancements = new HashMap<>();
            List<Advancement> advancementList = LogicAdvancement.findAll();
            for (Advancement advancement : advancementList) {
                cachedAdvancements.put(advancement.getId(), advancement);
            }
        }
        return cachedAdvancements.values();
    }

    public static List<Advancement> getAdvancementList(List<String> nameList) {
        List<Advancement> advancementList = new ArrayList<>();
        for (String name : nameList) {
            for (Advancement advancement : getAdvancementList()) {
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
            cachedRequirements = new HashMap<>();
            List<Requirement> requirementList = LogicRequirement.findAll();
            for (Requirement requirement : requirementList) {
                cachedRequirements.put(requirement.getId(), requirement);
            }
        }
        return cachedRequirements.values();
    }

    public static List<Requirement> getRequirementListByParentIdAndTypeId(int parentId, int typeId) {
        List<Requirement> requirementList = new ArrayList<>();
        for (Requirement requirement : getRequirementList()) {
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
            cachedMeritBadges = new HashMap<>();
            List<MeritBadge> meritBadgeList = LogicMeritBadge.findAll();
            for (MeritBadge meritBadge : meritBadgeList) {
                cachedMeritBadges.put(meritBadge.getId(), meritBadge);
            }
        }
        return cachedMeritBadges.values();
    }

    public static List<MeritBadge> getMeritBadgeList(List<String> nameList) {
        List<MeritBadge> meritBadgeList = new ArrayList<>();
        for (String name : nameList) {
            for (MeritBadge meritBadge : getMeritBadgeList()) {
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

    public static Collection<Counselor> getCounselorList() {
        if (cachedCounselors == null) {
            cachedCounselors = new HashMap<>();
            List<Counselor> counselorList = LogicCounselor.findAll();
            for (Counselor counselor : counselorList) {
                cachedCounselors.put(counselor.getId(), counselor);
            }
        }
        return cachedCounselors.values();
    }

    public static List<Counselor> getCounselorListByBadgeId(int badgeId) {
        List<Counselor> counselorList = new ArrayList<>();
        for (Counselor counselor : counselorList) {
            if (counselor.getBadgeId() == badgeId) {
                counselorList.add(counselor);
            }
        }
        return counselorList;
    }

    public static void addToCounselor(Counselor counselor) {
        if (cachedCounselors == null) {
            getCounselorList();
        }

        assert cachedCounselors != null;
        cachedCounselors.put(counselor.getId(), counselor);
    }

    public static void addToCounselors(List<Counselor> counselorList) {
        if (cachedCounselors == null) {
            getCounselorList();
        }

        assert cachedCounselors != null;
        for (Counselor counselor : counselorList) {
            cachedCounselors.put(counselor.getId(), counselor);
        }
    }

    public static void removeFromCounselors(Integer counselorId) {
        if (cachedCounselors == null || cachedCounselors.isEmpty()) {
            return;
        }

        cachedCounselors.remove(counselorId);
    }
}
