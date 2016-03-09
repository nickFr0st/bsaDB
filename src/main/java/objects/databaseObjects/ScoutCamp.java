package objects.databaseObjects;

/**
 * Created by Malloch on 3/8/2016
 */
public class ScoutCamp {

    private int id;
    private int scoutId;
    private int scoutTypeId;
    private int campId;

    public ScoutCamp() {
        id = -1;
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

    public int getCampId() {
        return campId;
    }

    public void setCampId(int campId) {
        this.campId = campId;
    }
}
