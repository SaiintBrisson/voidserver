package me.saiintbrisson.voidserver.world;

public enum DimensionType {

    NETHER,
    OVERWORLD,
    END;

    public static DimensionType valueOf(int i) {
        return values()[i + 1];
    }

}
