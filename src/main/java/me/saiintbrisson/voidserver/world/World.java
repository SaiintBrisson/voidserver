package me.saiintbrisson.voidserver.world;

import lombok.Getter;
import me.saiintbrisson.voidserver.world.location.Location;

import java.util.Random;

@Getter
public class World {

    private final String name;
    private Location spawnPoint;

    private DimensionType dimensionType;
    private WorldType worldType;
    private WorldDifficultyType difficultyType;

    private final long seed;

    private WorldBorder worldBorder;

    private long age, time;

    {
        name = "test";
        spawnPoint = new Location();

        dimensionType = DimensionType.END;
        worldType = WorldType.DEFAULT;
        difficultyType = WorldDifficultyType.PEACEFUL;

        seed = new Random().nextLong();

        worldBorder = new WorldBorder(
          0, 0,
          100, 50,
          2, 0,
          0, 0
        );

        age = 10;
        time = 6000;
    }

}
