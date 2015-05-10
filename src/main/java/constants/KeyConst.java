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
    SAVED_USER("savedUser");

    String name;

    KeyConst(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
