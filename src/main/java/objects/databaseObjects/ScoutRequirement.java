package objects.databaseObjects;

/**
 * Created by Malloch on 3/8/2016
 */
public class ScoutRequirement {

    private int id;
    private int scoutAdvancementId;
    private int requirementId;

    public ScoutRequirement() {
        id = -1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScoutAdvancementId() {
        return scoutAdvancementId;
    }

    public void setScoutAdvancementId(int scoutAdvancementId) {
        this.scoutAdvancementId = scoutAdvancementId;
    }

    public int getRequirementId() {
        return requirementId;
    }

    public void setRequirementId(int requirementId) {
        this.requirementId = requirementId;
    }
}
