package constants;

import util.Util;

/**
 * Created by Nathanael on 5/24/2015
 */
public enum IETypeConst {
    ADVANCEMENT(10, "Advancement"),
    MERIT_BADGE(20, "Merit Badge"),
    CAMPOUT(30, "Campout"),
    SERVICE_PROJECTS(40, "Service Project"),
    ;

    private int id;
    private String name;

    IETypeConst(int id, String name) {
        this.id = id;
        this.name = name;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static String[] getValueNames() {
        String[] names = new String[IETypeConst.values().length];

        int i = 0;
        for (IETypeConst typeConst : IETypeConst.values()) {
            names[i++] = typeConst.getName();
        }

        return names;
    }

    public static IETypeConst getConst(Object name) {
        if (Util.isEmpty((String)name)) {
            return null;
        }

        for (IETypeConst typeConst : IETypeConst.values()) {
            if (typeConst.getName().equals(name)) {
                return typeConst;
            }
        }

        return null;
    }
}
