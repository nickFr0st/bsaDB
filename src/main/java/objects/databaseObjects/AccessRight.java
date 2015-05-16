package objects.databaseObjects;

/**
 * Created by Nathanael on 5/16/2015
 */
public class AccessRight {

    private int id;
    private int userId;
    private int rightId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRightId() {
        return rightId;
    }

    public void setRightId(int rightId) {
        this.rightId = rightId;
    }
}
