package objects.databaseObjects;

/**
 * Created by Malloch on 3/8/2016
 */
public class ScoutMeritBadge {

    private int id;
    private int scoutId;
    private int scoutTypeId;
    private int meritBadgeId;

    public ScoutMeritBadge() {
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

    public int getMeritBadgeId() {
        return meritBadgeId;
    }

    public void setMeritBadgeId(int meritBadgeId) {
        this.meritBadgeId = meritBadgeId;
    }
}
