package objects.databaseObjects;

import java.util.Date;

/**
 * Created by Nathanael on 3/8/2016
 */
public class Camp {

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
}
