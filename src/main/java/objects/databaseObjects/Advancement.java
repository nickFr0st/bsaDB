package objects.databaseObjects;

/**
 * Created by Nathanael on 5/18/2015
 */
public class Advancement implements Compare {
    public static final int COL_NAME_LENGTH = 254;
    public static final int COL_IMG_PATH_LENGTH = 254;

    private int id;
    private String name;
    private String imgPath;
    private Integer timeRequirement;
    private Integer nextAdvancementId;
    private Double serviceHours;
    private boolean readOnly;

    public Advancement() {
        id = -1;
        readOnly = false;
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

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public Integer getTimeRequirement() {
        return timeRequirement;
    }

    public void setTimeRequirement(Integer timeRequirement) {
        this.timeRequirement = timeRequirement;
    }

    public Integer getNextAdvancementId() {
        return nextAdvancementId;
    }

    public void setNextAdvancementId(Integer nextAdvancementId) {
        this.nextAdvancementId = nextAdvancementId;
    }

    public Double getServiceHours() {
        return serviceHours;
    }

    public void setServiceHours(Double serviceHours) {
        this.serviceHours = serviceHours;
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Advancement that = (Advancement) o;

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
