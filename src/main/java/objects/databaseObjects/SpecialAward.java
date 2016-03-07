package objects.databaseObjects;

/**
 * Created by Nathanael on 5/16/2015
 */
public class SpecialAward {

    private int id;
    private int scoutId;
    private int scoutTypeId;
    private String name;
    private String imgPath;

    {
        id = -1;
        scoutId = -1;
        name = "";
        imgPath = "";
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

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
}
