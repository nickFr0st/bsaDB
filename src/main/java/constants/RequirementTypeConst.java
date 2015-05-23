package constants;

/**
 * Created by Nathanael on 5/22/2015
 */
public enum RequirementTypeConst {
    ADVANCEMENT(10, "Advancement"),
    ;

    private int id;
    private String name;

    RequirementTypeConst(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
