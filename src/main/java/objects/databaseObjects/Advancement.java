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

    public Advancement() {
        id = -1;
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
