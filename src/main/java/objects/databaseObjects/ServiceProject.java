package objects.databaseObjects;

import util.Util;

import java.util.Date;

/**
 * Created by Nathanael on 5/18/2015
 */
public class ServiceProject implements Compare {
    public static final int COL_NAME_LENGTH = 254;
    public static final int COL_LEADERS_LENGTH = 254;

    private int id;
    private String name;
    private String location;
    private Date startDate;
    private Double duration;
    private Integer scoutTypeId;
    private String leaders;
    private String note;

    public ServiceProject() {
        id = -1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        if (Util.isEmpty(name)) {
            this.name = "";
        } else if (name.length() > COL_NAME_LENGTH) {
            this.name = name.substring(0, COL_NAME_LENGTH);
        } else {
            this.name = name;
        }
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

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public Integer getScoutTypeId() {
        return scoutTypeId;
    }

    public void setScoutTypeId(Integer scoutTypeId) {
        this.scoutTypeId = scoutTypeId;
    }

    public String getLeaders() {
        return leaders;
    }

    public void setLeaders(String leaders) {
        if (Util.isEmpty(leaders)) {
            this.leaders = "";
        } else if (leaders.length() > COL_NAME_LENGTH) {
            this.leaders = leaders.substring(0, COL_LEADERS_LENGTH);
        } else {
            this.leaders = leaders;
        }
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ServiceProject that = (ServiceProject) o;

        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return name;
    }
}
