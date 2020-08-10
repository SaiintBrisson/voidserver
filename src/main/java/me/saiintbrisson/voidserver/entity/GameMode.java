package me.saiintbrisson.voidserver.entity;

public enum GameMode {

    SURVIVAL,
    CREATIVE,
    ADVENTURE,
    SPECTATOR;

    public static GameMode valueOf(int i) {
        return values()[i];
    }

}
