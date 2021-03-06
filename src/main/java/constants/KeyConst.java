package constants;

/**
 * Created by Nathanael on 5/4/2015
 */
public enum KeyConst {
    // Database
    DB_URL("dbUrl"),
    DB_NAME("dbName"),
    DB_USER_NAME("dbUserName"),
    DB_PASSWORD("dbPassword"),
    SAVED_USER("savedUser"),
    CURRENT_USER("currentUser"),

    // User
    ID("id"),
    USERNAME("username"),
    NAME("name"),
    TIME_REQUIREMENT("timeRequirement"),
    POSITION("position"),
    PHONE_NUMBER("phoneNumber"),
    STREET("street"),
    STATE("state"),
    CITY("city"),
    ZIP("zip"),
    EDITABLE("editable"),
    PASSWORD("password"),
    EMAIL("email"),

    // Boy Scout
    BIRTH_DATE("birthDate"),
    ADVANCEMENT_ID("advancementId"),
    ADVANCEMENT_DATE("advancementDate"),
    GUARDIAN_NAME("guardianName"),
    GUARDIAN_PHONE_NUMBER("guardianPhoneNumber"),
    NOTE("note"),
    REQUIREMENT_ID("requirementId"),
    REQUIREMENT_DATE_COMPLETED("dateCompleted"),
    MERIT_BADGE_ID("meritBadgeId"),

    // Camp
    CAMP_ID("campId"),
    SCOUT_ID("scoutId"),
    SCOUT_TYPE_ID("scoutTypeId"),
    LOCATION("location"),
    START_DATE("startDate"),
    LEADER("leaders"),
    NIGHT_COUNT("numberOfNights"),

    // Service Project
    DURATION("duration"),

    // Scout Service Project
    SERVICE_PROJECT_ID("serviceProjectId"),

    // Access Right
    RIGHT_ID("rightId"),
    USER_ID("userId"),

    // Advancement
    IMG_PATH("imgPath"),
    NEXT_ADVANCEMENT_ID("nextAdvancementId"),
    SERVICE_HOURS("serviceHours"),
    READ_ONLY("readOnly"),

    // Requirement
    DESCRIPTION("description"),
    PARENT_ID("parentId"),
    TYPE_ID("typeId"),

    // Merit Badge
    REQUIRED_FOR_EAGLE("requiredForEagle"),
    REV_DATE("revisionDate"),

    // Counselor
    BADGE_ID("badgeId"),

    // Special Awards
    DATE_RECEIVED("dateReceived"),
    ;

    String name;

    KeyConst(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
