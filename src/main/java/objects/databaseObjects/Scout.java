package objects.databaseObjects;

/**
 * Created by Nathanael on 3/10/2016
 */
public interface Scout extends Compare {
    String getName();
    void setName(String name);

    int getId();
    void setId(int id);
}
