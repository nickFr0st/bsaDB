package objects.databaseObjects;

/**
 * Created by Nathanael on 5/12/2015
 */
public class User {

    public static final int COL_NAME_LENGTH = 90;
    public static final int COL_POSITION_LENGTH = 90;
    public static final int COL_PHONE_NUMBER_LENGTH = 20;
    public static final int COL_ZIP_LENGTH = 10;

    private int id;
    private String name;
    private String position;
    private String phoneNumber;
    private String street;
    private String city;
    private String zip;
    private boolean editable;
    private String password;

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
