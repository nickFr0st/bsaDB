package objects.databaseObjects;

import java.util.Date;

/**
 * Created by Nathanael on 3/5/2016
 */
public class BoyScout implements Scout {

    public static final int COL_NAME_LENGTH = 90;
    public static final int COL_POSITION_LENGTH = 90;
    public static final int COL_PHONE_NUMBER_LENGTH = 20;
    public static final int COL_GUARDIAN_NAME_LENGTH = 90;
    public static final int COL_GUARDIAN_PHONE_NUMBER_LENGTH = 20;

    private int id;
    private String name;
    private String position;
    private Date birthDate;
    private int advancementId;
    private Date advancementDate;
    private String phoneNumber;
    private String guardianName;
    private String guardianPhoneNumber;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String note;

    {
        id = -1;
        name = "";
        position = "";
        advancementId = -1;
        phoneNumber = "";
        guardianName = "";
        guardianPhoneNumber = "";
        street = "";
        city = "";
        state = "";
        zip = "";
        note = "";
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

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public int getAdvancementId() {
        return advancementId;
    }

    public void setAdvancementId(int advancementId) {
        this.advancementId = advancementId;
    }

    public Date getAdvancementDate() {
        return advancementDate;
    }

    public void setAdvancementDate(Date advancementDate) {
        this.advancementDate = advancementDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getGuardianName() {
        return guardianName;
    }

    public void setGuardianName(String guardianName) {
        this.guardianName = guardianName;
    }

    public String getGuardianPhoneNumber() {
        return guardianPhoneNumber;
    }

    public void setGuardianPhoneNumber(String guardianPhoneNumber) {
        this.guardianPhoneNumber = guardianPhoneNumber;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
