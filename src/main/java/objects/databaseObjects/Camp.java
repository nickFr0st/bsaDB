package objects.databaseObjects;

import java.util.Date;

/**
 * Created by Nathanael on 3/8/2016
 */
public class Camp implements Compare {

    public static final int COL_NAME_LENGTH = 90;
    public static final int COL_LOCATION_LENGTH = 255;
    public static final int COL_LEADERS_LENGTH = 255;

    private int id;
    private String name;
    private int scoutTypeId;
    private String location;
    private Date startDate;
    private String leaders;
    private String note;
    private int numberOfNights;

    {
        id = -1;
        name = "";
        note = "";
    }

    public Camp() {
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

    public int getScoutTypeId() {
        return scoutTypeId;
    }

    public void setScoutTypeId(int scoutTypeId) {
        this.scoutTypeId = scoutTypeId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getLeaders() {
        return leaders;
    }

    public void setLeaders(String leaders) {
        this.leaders = leaders;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getNumberOfNights() {
        return numberOfNights;
    }

    public void setNumberOfNights(int numberOfNights) {
        this.numberOfNights = numberOfNights;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Camp camp = (Camp) o;

        if (scoutTypeId != camp.scoutTypeId) return false;
        return name.equals(camp.name);

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + scoutTypeId;
        return result;
    }

    @Override
    public String toString() {
        return name;
    }
}
