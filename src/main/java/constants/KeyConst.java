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
    NAME("name"),
    POSITION("position"),
    PHONE_NUMBER("phoneNumber"),
    STREET("street"),
    CITY("city"),
    ZIP("zip"),
    EDITABLE("editable"),
    PASSWORD("password"),
    EMAIL("email"),

    // Access Right
    RIGHT_ID("rightId"),
    USER_ID("userId"),

    // Advancement
    IMG_PATH("imgPath"),

    // Requirement
    DESCRIPTION("description"),
    PARENT_ID("parentId"),
    TYPE_ID("typeId");

    String name;

    KeyConst(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
