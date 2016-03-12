package constants;

/**
 * Created by Nathanael on 5/22/2015
 */
public enum ScoutTypeConst {
//    CUB_SCOUT(10, "Cub Scout"),
    BOY_SCOUT(20, "Boy Scout"),
//    VARSITY_SCOUT(30, "Varsity Scout"),
//    VENTURE_SCOUT(40, "Venture Scout"),
    ;

    private int id;
    private String name;

    ScoutTypeConst(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static ScoutTypeConst getConst(int id) {
        for (ScoutTypeConst scoutType : ScoutTypeConst.values()) {
            if (scoutType.getId() == id) {
                return scoutType;
            }
        }

        return null;
    }

    public static ScoutTypeConst getConst(String name) {
        for (ScoutTypeConst scoutType : ScoutTypeConst.values()) {
            if (scoutType.getName().equals(name)) {
                return scoutType;
            }
        }

        return null;
    }
}
