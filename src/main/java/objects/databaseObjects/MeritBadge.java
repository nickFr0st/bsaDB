package objects.databaseObjects;

/**
 * Created by Nathanael on 5/18/2015
 */
public class MeritBadge implements Compare {
    public static final int COL_NAME_LENGTH = 254;

    private int id;
    private String name;
    private String imgPath;
    private boolean requiredForEagle;
    private boolean readOnly;

    public MeritBadge() {
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

    public boolean isRequiredForEagle() {
        return requiredForEagle;
    }

    public void setRequiredForEagle(boolean requiredForEagle) {
        this.requiredForEagle = requiredForEagle;
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

        MeritBadge that = (MeritBadge) o;

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
