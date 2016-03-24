package objects.databaseObjects;

/**
 * Created by Malloch on 7/10/14
 */
public class Counselor implements Compare {
    public static final int COL_NAME_LENGTH = 90;
    public static final int COL_PHONE_NUMBER_LENGTH = 20;

    private int id;
    private int badgeId;
    private String name;
    private String phoneNumber;

    public Counselor() {
        id = -1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBadgeId() {
        return badgeId;
    }

    public void setBadgeId(int badgeId) {
        this.badgeId = badgeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
