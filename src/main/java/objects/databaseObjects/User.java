package objects.databaseObjects;

/**
 * Created by Nathanael on 5/12/2015
 */
public class User implements  Comparable<User> {

    public static final int COL_USER_NAME_LENGTH = 30;
    public static final int COL_NAME_LENGTH = 90;
    public static final int COL_POSITION_LENGTH = 90;
    public static final int COL_PHONE_NUMBER_LENGTH = 20;
    public static final int COL_ZIP_LENGTH = 10;

    private int id;
    private String userName;
    private String name;
    private String position;
    private String phoneNumber;
    private String street;
    private String city;
    private String state;
    private String zip;
    private boolean editable;
    private String password;
    private String email;

    {
        id = -1;
        userName = "";
        name = "";
        password = "";
        position = "";
        phoneNumber = "";
        street = "";
        city = "";
        state = "";
        zip = "";
        email = "";
        editable = true;
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return userName.equals(user.userName);

    }

    @Override
    public int hashCode() {
        return userName.hashCode();
    }

    @Override
    public String toString() {
        return userName;
    }

    @Override
    public int compareTo(User user) {
        return name.compareTo(user.name);
    }
}
