package objects.databaseObjects;

/**
 * Created by Malloch on 3/8/2016
 */
public class ScoutAdvancement {

    private int id;
    private int scoutId;
    private int scoutTypeId;
    private int advancementId;

    public ScoutAdvancement() {
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

    public int getAdvancementId() {
        return advancementId;
    }

    public void setAdvancementId(int advancementId) {
        this.advancementId = advancementId;
    }
}
