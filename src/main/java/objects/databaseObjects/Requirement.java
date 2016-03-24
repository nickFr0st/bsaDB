package objects.databaseObjects;

/**
 * Created by Nathanael on 5/19/2015
 */
public class Requirement implements Compare {

    public static final int COL_NAME_LENGTH = 44;

    private int id;
    private String name;
    private String description;
    private int typeId;
    private int parentId;

    {
        typeId = -1;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int type) {
        this.typeId = type;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }
}
