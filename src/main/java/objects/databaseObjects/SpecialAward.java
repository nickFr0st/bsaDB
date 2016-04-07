package objects.databaseObjects;

import java.util.Date;

/**
 * Created by Nathanael on 5/16/2015
 */
public class SpecialAward {

    private int id;
    private int scoutId;
    private int scoutTypeId;
    private String name;
    private String description;
    private Date dateReceived;

    {
        id = -1;
        scoutId = -1;
        name = "";
        description = "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScoutId() {
        return scoutId;
    }

    public void setScoutId(int scoutId) {
        this.scoutId = scoutId;
    }

    public int getScoutTypeId() {
        return scoutTypeId;
    }

    public void setScoutTypeId(int scoutTypeId) {
        this.scoutTypeId = scoutTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateReceived() {
        return dateReceived;
    }

    public void setDateReceived(Date dateReceived) {
        this.dateReceived = dateReceived;
    }
}
