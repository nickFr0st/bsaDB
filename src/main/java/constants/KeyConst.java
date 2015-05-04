package constants;

/**
 * Created by Nathanael on 5/4/2015
 */
public enum KeyConst {
    SAVED_USER("savedUser");

    String name;

    KeyConst(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
