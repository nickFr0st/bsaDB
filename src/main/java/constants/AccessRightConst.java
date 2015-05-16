package constants;

/**
 * Created by Nathanael on 5/16/2015
 */
public enum AccessRightConst {
    DATABASE_SETTINGS(10, "Database Settings"),
    USERS(20, "Users");

    private int id;
    private String name;

    AccessRightConst(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
